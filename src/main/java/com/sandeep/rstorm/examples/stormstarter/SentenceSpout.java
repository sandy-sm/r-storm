package com.sandeep.rstorm.examples.stormstarter;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Random;

/**
 * Created by k2user on 1/4/17.
 */
public class SentenceSpout implements IRichSpout {

    SpoutOutputCollector collector;
    Random random;

    private static  final Logger logger = LoggerFactory.getLogger(SentenceSpout.class);


    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("sentence"));
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        collector = spoutOutputCollector;
        random = new Random();
    }

    public void close() {

    }

    public void activate() {

    }

    public void deactivate() {

    }

    public void nextTuple() {
        Utils.sleep(100);
        String[] sentences = new String[]{"the cow jumped over the moon", "an apple a day keeps the doctor away",
                "four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature"};
        String sentence = sentences[random.nextInt(sentences.length)];

        logger.debug("Emitting sentence tuple : {}", sentence);
        collector.emit(new Values(sentence));
    }

    public void ack(Object o) {

    }

    public void fail(Object o) {

    }
}
