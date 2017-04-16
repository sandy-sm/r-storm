package com.sandeep.rstorm.examples.kafka;



import com.google.common.io.Files;
import com.google.gson.Gson;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.rosuda.JRI.Rengine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.util.parsing.json.JSON;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by k2user on 1/4/17.
 */
public class ExecutorBolt implements IRichBolt {

    private static Logger logger = LoggerFactory.getLogger(ExecutorBolt.class);

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    public void execute(Tuple tuple) {
        logger.info("Kafka Input logger {}", tuple.getStringByField("value"));
        String maildata = tuple.getStringByField("value");


        logger.info("Identifying email contents...");
        logger.info("Contents = {}", maildata);

        long startTime = System.currentTimeMillis();

        // Start Rengine.
        Rengine engine = new Rengine(new String[]{"--no-save"}, false, null);
        File file = new File("src/main/resources/listData.RData");
        String scriptFilePath = file.getPath();

        logger.info("Loading SPAMClassifier R Script File from : {}", scriptFilePath);
        engine.eval("load(file = \""+scriptFilePath+"\")");

        logger.info("Detecting SPAM...");
        engine.eval("result <- isSpam(\""+maildata+"\");");

        String _isSpam = engine.eval("as.character(result)").asString();

        if (Boolean.valueOf(_isSpam)) {
            logger.info("Given email is SPAM");
        } else {
            logger.info("Given email is Not SPAM");
        }

        logger.info("Executed! In {} secs", (System.currentTimeMillis() - startTime)/1000);
    }

    public void cleanup() {

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
