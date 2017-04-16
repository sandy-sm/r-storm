package com.sandeep.rstorm.examples.multilang;


import org.apache.storm.generated.ShellComponent;
import org.apache.storm.task.ShellBolt;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by k2user on 4/4/17.
 */
public class ExecutorBolts extends ShellBolt implements IRichBolt {

  private static final long serialVersionUID = 4305587293613439941L;
  
  public ExecutorBolts() {
    super("Rscript","script.R");
  }

  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    outputFieldsDeclarer.declare(new Fields("spam"));
  }

  public Map<String, Object> getComponentConfiguration() {
    return null;
  }
}
