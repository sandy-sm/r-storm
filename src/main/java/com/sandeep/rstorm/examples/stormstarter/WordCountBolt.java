package com.sandeep.rstorm.examples.stormstarter;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by k2user on 1/4/17.
 */
public class WordCountBolt implements IRichBolt {
    Map<String,Integer> counts = new HashMap<String, Integer>();
    OutputCollector collector;

    private static Logger logger = LoggerFactory.getLogger(WordCountBolt.class);

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector= outputCollector;
    }

    public void execute(Tuple tuple) {
        String word = tuple.getStringByField("word");
        Integer count = counts.get(word);

        if (count == null)
            count = 0;

        count++;
        counts.put(word, count);

        logger.debug("word {}, count {}", word, count);

        collector.emit(new Values(word, count));
    }

    public void cleanup() {

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word", "count"));
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
