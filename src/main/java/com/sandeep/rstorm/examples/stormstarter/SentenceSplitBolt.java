package com.sandeep.rstorm.examples.stormstarter;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by k2user on 1/4/17.
 */
public class SentenceSplitBolt implements IRichBolt{

    OutputCollector outputCollector;

    private static final Logger logger = LoggerFactory.getLogger(SentenceSplitBolt.class);

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        outputCollector = collector;
    }

    public void execute(Tuple input) {
        String sentence = input.getString(0);
        String[] words = sentence.split("\\s");

        for (String word: words) {
            logger.debug("Emitting word {}", word);
            outputCollector.emit(new Values(word));
        }
    }

    public void cleanup() {

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
