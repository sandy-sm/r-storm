package com.sandeep.rstorm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by k2user on 16/4/17.
 */
public class ReadConfig {

  public static Properties properties = null;

  //Application properties keys
  public static final String R_SCRIPT_PATH = "r_script_path";
  public static final String R_MULTILANG_SCRIPT_PATH = "r_multilang_script";
  public static final String R_SCRIPT_METHOD = "r_script_method";
  public static final String R_ENGINE_HOST = "r_engine_host";
  public static final String R_ENGINE_PORT = "r_engine_port";

  //Kafka Config keys
  public static final String KAFKA_TOPIC = "kafkaTopic";
  public static final String KAFKA_BROKER = "kafkaBroker";
  public static final String KAFKA_CONSUMER_GROUP = "kafkaGroupId";

  //Storm Config
  public static final String STORM_SINK_BOLT_CONFIG = "storm_sink_bolt_field";

  private static final Logger logger = LoggerFactory.getLogger(ReadConfig.class);

  static {
    logger.info("Initializing properties..");
    InputStream inputStream  = null;
    properties = new Properties();
    try {
      inputStream = new FileInputStream("src/main/resources/application.properties");
      properties.load(inputStream);
    } catch (IOException ioException) {
      logger.error("Error loading config properties due to ", ioException);
    }
  }
}
