package com.sandeep.rstorm.bolts;

import com.sandeep.rstorm.RStormConstants;
import com.sandeep.rstorm.config.ReadConfig;
import org.apache.storm.task.ShellBolt;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;

import java.util.Map;

/**
 * Created by k2user on 16/4/17.
 */
public class RMultiLangExecutorBolt extends ShellBolt implements IRichBolt {

  private static final long serialVersionUID = 4305587293613439941L;

  public RMultiLangExecutorBolt(String script) {
    super(RStormConstants.R_SCRIPT_MULTI_LANG_COMMAND, script);
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
