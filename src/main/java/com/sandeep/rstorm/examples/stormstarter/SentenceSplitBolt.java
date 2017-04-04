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
