package com.sandeep.rstorm.bolts;

import com.sandeep.rstorm.R.RExecutor;
import com.sandeep.rstorm.RStormConstants;
import com.sandeep.rstorm.config.ReadConfig;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by k2user on 16/4/17.
 */
public class RExecutorBolt implements IRichBolt {

  RExecutor rExecutor;
  OutputCollector outputCollector;

  private static Logger logger = LoggerFactory.getLogger(RExecutorBolt.class);

  @Override
  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    Map<String, Object> topologyEnv = (Map<String, Object>) stormConf.get(RStormConstants.STORM_TOPOLOGY_ENV);

    logger.info("Topology Env. {}", topologyEnv);
    this.rExecutor = new RExecutorBoltCreator().
      getRExecutorBolt(topologyEnv.get(RStormConstants.STORM_TOPOLOGY_ENV_BOLT_EXECUTOR_KEY).toString());
    outputCollector= collector;
  }

  @Override
  public void execute(Tuple input) {
    String _rMethod = ReadConfig.properties.getProperty(ReadConfig.R_SCRIPT_METHOD);
    String scriptInput = input.getStringByField("value").replaceAll("\"", "");
    String result = rExecutor.executeScriptMethod(_rMethod, scriptInput);

    logger.info("RExecutor Output : {}", result);
    logger.info("Emitting results to SinkBolt");

    outputCollector.emit(new Values(scriptInput, result));
  }

  @Override
  public void cleanup() {

  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields(RStormConstants.STORM_SINK_BOLT_FIELD, ReadConfig.properties.getProperty(ReadConfig.STORM_SINK_BOLT_CONFIG)));
  }

  @Override
  public Map<String, Object> getComponentConfiguration() {
    return null;
  }
}
