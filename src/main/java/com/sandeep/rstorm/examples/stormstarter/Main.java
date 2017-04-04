package com.sandeep.rstorm.examples.stormstarter;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by k2user on 1/4/17.
 */
public class Main {

    public static void main(String[] args) {
        Config config = new Config();
        config.setDebug(true);
        config.setMaxTaskParallelism(2);

        Map<String, Object> map = new HashMap();
        SentenceSpout spout = new SentenceSpout();

        SentenceSplitBolt sentenceSplitBolt = new SentenceSplitBolt();
        WordCountBolt wordCountBolt = new WordCountBolt();

        TopologyBuilder topologyBuilder = new TopologyBuilder();

        topologyBuilder.setSpout("spout", spout);
        topologyBuilder.setBolt("split", sentenceSplitBolt).shuffleGrouping("spout");
        topologyBuilder.setBolt("count", wordCountBolt).fieldsGrouping("split", new Fields("word"));

        //StormSubmitter.submitTopology("spout-bolt", map, topologyBuilder.createTopology());
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("word-count", config, topologyBuilder.createTopology());
    }
}