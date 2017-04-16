package com.sandeep.rstorm.examples.multilang;

import com.sandeep.rstorm.examples.stormstarter.SentenceSplitBolt;
import com.sandeep.rstorm.examples.stormstarter.SentenceSpout;
import com.sandeep.rstorm.examples.stormstarter.WordCountBolt;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by k2user on 1/4/17.
 */
public class Main {

  public static void main(String[] args) {
    Config config = new Config();
    config.setDebug(true);
    config.setMaxTaskParallelism(1);

    Map<String, Object> map = new HashMap();
    RandomSentenceSpout spout = new RandomSentenceSpout();
    ExecutorBolts executorBolts = new ExecutorBolts();

    TopologyBuilder topologyBuilder = new TopologyBuilder();

    topologyBuilder.setSpout("spout", spout);
    topologyBuilder.setBolt("executor", executorBolts).shuffleGrouping("spout");

    //StormSubmitter.submitTopology("spout-bolt", map, topologyBuilder.createTopology());
    LocalCluster cluster = new LocalCluster();
    cluster.submitTopology("multi-lang-spout", config, topologyBuilder.createTopology());

/*    try {
      StormSubmitter.submitTopology("multi-lang-spout", config, topologyBuilder.createTopology());
    } catch (Exception exception) {
      exception.printStackTrace();
    }*/
  }
}
