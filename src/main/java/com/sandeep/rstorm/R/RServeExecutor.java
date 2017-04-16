package com.sandeep.rstorm.R;

import com.sandeep.rstorm.RStormConstants;
import com.sandeep.rstorm.config.ReadConfig;
import com.sandeep.rstorm.errorhandler.RStormException;
import org.apache.storm.utils.Utils;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by k2user on 16/4/17.
 */
public class RServeExecutor implements RExecutor {

  private static final Logger logger = LoggerFactory.getLogger(RServeExecutor.class);

  RConnection connection;

  public RServeExecutor() {
    String host = ReadConfig.properties.getProperty(ReadConfig.R_ENGINE_HOST);
    String port = ReadConfig.properties.getProperty(ReadConfig.R_ENGINE_PORT);

    try {
      logger.info("Creating RServer Connection with host {} and port {}", host, port);
      connection = new RConnection(host, Integer.parseInt(port));
    } catch (RserveException e) {
      logger.error("RServer failed! ", e);
    }
  }

  @Override
  public void initializeScript(String path) {
    File file = new File(RStormConstants.R_SCRIPT_IMAGE_LOCATION);

    if (!file.exists()) {
      logger.info("Starting REngine to initialize RData...");

      long startTime = System.currentTimeMillis();
      String startupScriptPath = file.getAbsolutePath();

      file = new File(RStormConstants.R_STARTUP_SCRIPT_LOCATION);
      logger.info("Getting absolute path of initializer script {}", file.getAbsolutePath());

      try {
        connection.eval("source(file=\""+ file.getAbsolutePath()+"\");");
        logger.info("Execution took {} secs", (System.currentTimeMillis() - startTime)/1000);
        connection.eval("save.image(file=\""+file.getAbsolutePath()+"\");");

        Utils.sleep(3000);
      } catch (RserveException e) {
        throw new RStormException("Could not execute startup script!");
      }

      logger.info("Rscript Image saved!");
    }

    try {
      logger.info("Loading R Image file {}", file.getAbsolutePath());
      String loadFileCommand = "load(file = \""+file.getAbsolutePath()+"\")";
      logger.debug("R Loadfile command {}", loadFileCommand);
      connection.eval(loadFileCommand);
    } catch (RserveException e) {
      logger.error("Error loading RScript Image file ", e);
      throw new RStormException("Could not load RImage File!");
    }
  }

  @Override
  public String executeScriptMethod(String methodName, String inputData) {
    String result = null;
    try {
      connection.eval("result <- "+ String.format(methodName, inputData));
      String _isSpam = connection.eval("as.character(result)").asString();
      result = _isSpam;
    } catch (Exception exception) {
      logger.error("Error ", exception);
      throw new RStormException("Error occured while executing RScript method: "+ exception.getMessage());
    }
    return result;
  }
}
