package com.sandeep.rstorm.R;

import com.sandeep.rstorm.errorhandler.RStormException;
import org.apache.storm.shade.org.apache.zookeeper.KeeperException;
import org.rosuda.JRI.Rengine;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by k2user on 16/4/17.
 */
public class RConnectionFactory {

  private static Logger logger = LoggerFactory.getLogger(RConnectionFactory.class);

  private RConnectionFactory(){}

  public static RConnection getRConnectionEngine(String host, int port) {
    try {
      logger.info("Creating RServer Connection with host {} and port {}", host, port);
      return new RConnection(host, port);
    } catch (RserveException e) {
      e.printStackTrace();
    }

    throw new RStormException("Not a valid R Connector!");
  }

  public static Rengine getRJavaConnectionEngine() {
    logger.info("Creating RJava Connection");
    return new Rengine(new String[]{"--no-save"}, false, null);
  }


}
