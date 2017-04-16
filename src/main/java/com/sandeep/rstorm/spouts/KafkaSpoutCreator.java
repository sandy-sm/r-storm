package com.sandeep.rstorm.spouts;

import com.sandeep.rstorm.config.ReadConfig;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by k2user on 16/4/17.
 */
public class KafkaSpoutCreator {
  static Logger logger = LoggerFactory.getLogger(KafkaSpoutCreator.class);

  public static KafkaSpout createKafkaSpout() {
    String brokerStr = ReadConfig.properties.getProperty(ReadConfig.KAFKA_BROKER);
    String kafkaTopic = ReadConfig.properties.getProperty(ReadConfig.KAFKA_TOPIC);
    String groupId = ReadConfig.properties.getProperty(ReadConfig.KAFKA_CONSUMER_GROUP);

    logger.info("Creating KafkaSpout with \'{}\' brokers, \'{}\' topic and \'{}\' consumer", brokerStr, kafkaTopic, groupId);

    KafkaSpout kafkaSpout = new KafkaSpout<>(KafkaSpoutConfig.builder(brokerStr, kafkaTopic)
      .setGroupId(groupId)
      .setFirstPollOffsetStrategy(KafkaSpoutConfig.FirstPollOffsetStrategy.LATEST).build());

    return kafkaSpout;
  }

}
