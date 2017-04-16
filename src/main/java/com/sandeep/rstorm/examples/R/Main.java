package com.sandeep.rstorm.examples.R;

import com.sandeep.rstorm.examples.R.RandomSentenceSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.rosuda.JRI.Rengine;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by k2user on 15/4/17.
 */
public class Main {

  private static final Logger logger = LoggerFactory.getLogger(RExecutor.class);

  public static void main(String[] arggs) throws RserveException {
    File file = new File("src/main/resources/img.RData");

    if (!file.exists()) {
      logger.info("Starting REngine to initialize RData...");
      //Rengine engine = new Rengine(new String[]{"--save"}, false, null);
      RConnection connection = new RConnection("127.0.0.1");

      long startTime = System.currentTimeMillis();

      file = new File("src/main/resources/SpamDetector.R");
      logger.info("Getting absolute path of initializer script {}", file.getAbsolutePath());
      //engine.eval("source(file=\""+ file.getAbsolutePath()+"\");");
      connection.eval("source(file=\""+ file.getAbsolutePath()+"\");");

      logger.info("Execution took {} secs", (System.currentTimeMillis() - startTime)/1000);
      String path = file.getAbsolutePath().replaceAll("/\\.*\\.R", "");

      logger.info("File parent path: {}", path);

      //engine.eval("save.image(file=\"" + file.getParent() + "/img.RData\");");
      connection.eval("save.image(file=\"/home/k2user/my-dev/r-storm/src/main/resources/img.RData\");");

      logger.info("Rscript Image saved!");
      //connection.serverShutdown();
    }

    Config config = new Config();
    config.setDebug(true);
    config.setMaxTaskParallelism(1);

    Map<String, Object> map = new HashMap();
    RandomSentenceSpout spout = new RandomSentenceSpout();
    RExecutorBolt executorBolts = new RExecutorBolt();

    TopologyBuilder topologyBuilder = new TopologyBuilder();

    topologyBuilder.setSpout("random-spout", spout);
    topologyBuilder.setBolt("r-executor", executorBolts).shuffleGrouping("random-spout");

    //StormSubmitter.submitTopology("spout-bolt", map, topologyBuilder.createTopology());
    LocalCluster cluster = new LocalCluster();
    cluster.submitTopology("random-spout-r-bolt", config, topologyBuilder.createTopology());
  }
}
