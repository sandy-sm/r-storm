package com.sandeep.rstorm.examples.multilang;


import org.apache.storm.task.ShellBolt;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;

import java.util.Map;

/**
 * Created by k2user on 4/4/17.
 */
public class ExecutorBolts extends ShellBolt implements IRichBolt {

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
