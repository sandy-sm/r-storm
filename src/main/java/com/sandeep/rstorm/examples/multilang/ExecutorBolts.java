package com.sandeep.rstorm.examples.multilang;


import backtype.storm.task.ShellBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;

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
