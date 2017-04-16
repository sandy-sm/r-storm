package com.sandeep.rstorm.bolts;

import com.sandeep.rstorm.RStormConstants;
import com.sandeep.rstorm.config.ReadConfig;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by k2user on 16/4/17.
 */
public class SinkBolt implements IRichBolt {

  String fieldKey;
  private static final Logger logger = LoggerFactory.getLogger(SinkBolt.class);

  @Override
  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    fieldKey = ReadConfig.properties.getProperty(ReadConfig.STORM_SINK_BOLT_CONFIG);
  }

  @Override
  public void execute(Tuple input) {
    // Do business logic on RScript post execution tuple result
    String scriptInput = input.getStringByField(RStormConstants.STORM_SINK_BOLT_FIELD);

    String result= input.getStringByField(fieldKey);
    logger.info("SinkBolt Input {}", result);

    if (result.equalsIgnoreCase("1")) {
      logger.info("Email is SPAM, For Email Contents \n:{}", scriptInput);
    } else {
      logger.info("Email is Not SPAM, For Email Contents \n:{}", scriptInput);
    }
  }

  @Override
  public void cleanup() {

  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {

  }

  @Override
  public Map<String, Object> getComponentConfiguration() {
    return null;
  }
}
