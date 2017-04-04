package com.sandeep.rstorm.examples.kafka;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import java.util.UUID;

/**
 * Created by k2user on 1/4/17.
 */
public class Main {

    public static void main(String[] args) {
        Config config = new Config();
        config.setDebug(true);
        config.setMaxTaskParallelism(2);

        BrokerHosts brokerHosts = new ZkHosts("localhost:3191");
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, "user_emails",
                "/user_emails", UUID.randomUUID().toString());

        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

        ExecutorBolt executorBolt = new ExecutorBolt();

        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("kafka-spout", kafkaSpout);
        topologyBuilder.setBolt("kafka-bolt",executorBolt);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("kafka-topology", config, topologyBuilder.createTopology());
    }
}
