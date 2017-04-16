package com.sandeep.rstorm.examples.kafka;

import com.google.common.base.Strings;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by k2user on 1/4/17.
 */
public class Main {

    public static void main(String[] args) {
        Config config = new Config();
        config.setDebug(false);
        config.setMaxTaskParallelism(1);

        ExecutorBolt executorBolt = new ExecutorBolt();

        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("kafka-spout", KafkaSpoutCreator.createKafkaSpout(), 1);
        topologyBuilder.setBolt("kafka-bolt", executorBolt).globalGrouping("kafka-spout");

        LocalCluster localCluster =new LocalCluster();
        localCluster.submitTopology("kafka-topology", config, topologyBuilder.createTopology());

/*        try {
            StormSubmitter.submitTopology("kafka-topology", config, topologyBuilder.createTopology());
        } catch (Exception exception) {
            exception.printStackTrace();
        }*/
        //localCluster.shutdown();
    }
}
