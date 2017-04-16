package com.sandeep.rstorm.examples.R;

import com.google.common.io.Files;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.rosuda.JRI.Rengine;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by k2user on 15/4/17.
 */
public class RExecutorBolt implements IRichBolt {
  private static Logger logger = LoggerFactory.getLogger(RExecutorBolt.class);
  RConnection engine = null;
  String scriptFilePath = null;


  RExecutorBolt() {}

  RExecutorBolt(RConnection _engine) {
    engine= _engine;
  }

  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    logger.info("Starting REngine...");
    if (engine == null) {
      try {
        File file = new File("src/main/resources/img.RData");
        scriptFilePath = file.getAbsolutePath();
        engine = new RConnection();
        logger.info("Loading SPAMClassifier R Script File from : {}", scriptFilePath);
        engine.eval("load(file = \""+scriptFilePath+"\")");
      } catch (RserveException e) {
        e.printStackTrace();
      }
    }
  }

  public void execute(Tuple input) {
    logger.info("Kafka Input logger {}", input.getString(0));
    String maildata = input.getStringByField("value");

    logger.info("Identifying email contents...");
    logger.info("Contents = {}", maildata);

    long startTime = System.currentTimeMillis();

    // Start Rengine.
    logger.info("Detecting SPAM...");
    try {
      engine.eval("result <- isSpam(\"" + maildata + "\");");
      String _isSpam = engine.eval("as.character(result)").asString();

      if (Boolean.valueOf(_isSpam)) {
        logger.info("Given email is SPAM");
      } else {
        logger.info("Given email is Not SPAM");
      }
    } catch (Exception exception) {
      logger.error("Error ", exception);
    }

    logger.info("Executed! In {} secs", (System.currentTimeMillis() - startTime)/1000);
  }

  public void cleanup() {

  }

  public void declareOutputFields(OutputFieldsDeclarer declarer) {

  }

  public Map<String, Object> getComponentConfiguration() {
    return null;
  }
}
