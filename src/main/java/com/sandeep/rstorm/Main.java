package com.sandeep.rstorm;

import com.sandeep.rstorm.R.RExecutor;
import com.sandeep.rstorm.R.RNativeExecutor;
import com.sandeep.rstorm.R.RServeExecutor;
import com.sandeep.rstorm.bolts.*;
import com.sandeep.rstorm.config.ReadConfig;
import com.sandeep.rstorm.errorhandler.RStormException;
import com.sandeep.rstorm.spouts.KafkaSpoutCreator;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
      Config config = new Config();
      config.setDebug(false);
      config.setMaxTaskParallelism(1);

      String boltExecutor = args[0];

      Map<String, Object> env = new HashMap<>();
      env.put("boltExecutor", boltExecutor);
      config.setEnvironment(env);

      IRichBolt rExecutorBolt;

      if (RExecutorEnum.valueOf(boltExecutor).ordinal() == RExecutorEnum.MultiLang.ordinal()) {
        logger.info("Initializing RMultiLangBolt...");
        rExecutorBolt = new RMultiLangExecutorBolt(ReadConfig.properties
          .getProperty(ReadConfig.R_MULTILANG_SCRIPT_PATH));
      } else {
        logger.info("Initializing R Bolt...");
        rExecutorBolt = new RExecutorBolt();
      }

      SinkBolt sinkBolt = new SinkBolt();

      logger.info("Building r-storm-topology...");

      TopologyBuilder topologyBuilder = new TopologyBuilder();
      topologyBuilder.setSpout("r-storm-kafka-spout", KafkaSpoutCreator.createKafkaSpout(), 1);

      topologyBuilder.setBolt("r-storm-executor-bolt", rExecutorBolt)
        .globalGrouping("r-storm-kafka-spout");

      topologyBuilder.setBolt("r-storm-sink-bolt", sinkBolt)
        .fieldsGrouping("r-storm-executor-bolt",
          new Fields(RStormConstants.STORM_SINK_BOLT_FIELD,
            ReadConfig.properties.getProperty(ReadConfig.STORM_SINK_BOLT_CONFIG)));

      try {
        logger.info("Submitting storm topology...");
        //StormSubmitter.submitTopology("r-storm-topology", config, topologyBuilder.createTopology());
        new LocalCluster().submitTopology("r-storm-topology", config, topologyBuilder.createTopology());
      } catch (Exception exception) {
        logger.error("Error.. ", exception);
      }
    }
}
