package com.sandeep.rstorm.examples.R;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.rosuda.JRI.Rengine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;
import java.util.Random;

/**
 * Created by k2user on 14/4/17.
 */
public class RandomSentenceSpout implements IRichSpout {

  SpoutOutputCollector outputCollector;
  Random random;

  private static Logger logger = LoggerFactory.getLogger(RandomSentenceSpout.class);

  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("word"));
  }

  public Map<String, Object> getComponentConfiguration() {
    return null;
  }

  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    outputCollector = collector;
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
    String[] sentences = new String[]{sentence("the cow jumped over the moon"), sentence("an apple a day keeps the doctor away"),
      sentence("four score and seven years ago"), sentence("snow white and the seven dwarfs"), sentence("i am at two with nature")};
    final String sentence = sentences[random.nextInt(sentences.length)];

    logger.debug("Emitting tuple: {}", sentence);

    outputCollector.emit(new Values(sentence));
  }

  protected String sentence(String input) {
    return input.trim();
  }

  public void ack(Object msgId) {

  }

  public void fail(Object msgId) {

  }
}
