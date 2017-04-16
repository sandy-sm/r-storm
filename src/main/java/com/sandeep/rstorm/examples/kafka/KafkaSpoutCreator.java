package com.sandeep.rstorm.examples.kafka;

import org.apache.storm.Config;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.spout.SchemeAsMultiScheme;

import java.util.UUID;

/**
 * Created by k2user on 11/4/17.
 */
public class KafkaSpoutCreator {

  public static KafkaSpout createKafkaSpout() {
    KafkaSpout kafkaSpout = new KafkaSpout<>(KafkaSpoutConfig.builder("127.0.0.1:9092", "user_emails")
      .setGroupId("emailConsumer")
      .setFirstPollOffsetStrategy(KafkaSpoutConfig.FirstPollOffsetStrategy.EARLIEST).build());

    return kafkaSpout;
  }
}
