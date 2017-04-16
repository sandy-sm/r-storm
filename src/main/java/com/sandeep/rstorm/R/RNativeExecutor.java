package com.sandeep.rstorm.R;

import com.sandeep.rstorm.RStormConstants;
import com.sandeep.rstorm.errorhandler.RStormException;
import org.apache.storm.utils.Utils;
import org.rosuda.JRI.Rengine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by k2user on 16/4/17.
 */
public class RNativeExecutor implements RExecutor {

  Rengine rengine;

  private static final Logger logger = LoggerFactory.getLogger(RNativeExecutor.class);

  public RNativeExecutor() {
    rengine = new Rengine(new String[]{"--no-save"}, false, null);
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

      rengine.eval("source(file=\"" + file.getAbsolutePath() + "\");");
      logger.info("Execution took {} secs", (System.currentTimeMillis() - startTime) / 1000);
      rengine.eval("save.image(file=\"" + file.getAbsolutePath() + "\");");
      logger.info("Rscript Image saved!");
      Utils.sleep(3000);
    }

    logger.info("Loading R Image file {}", file.getAbsolutePath());
    String loadFileCommand = "load(file = \""+file.getAbsolutePath()+"\")";
    logger.debug("R Loadfile command {}", loadFileCommand);

    rengine.eval(loadFileCommand);
  }

  @Override
  public String executeScriptMethod(String methodName, String inputData) {
    String result = null;
    try {
      rengine.eval("result <- "+ String.format(methodName, inputData));
      String _isSpam = rengine.eval("as.character(result)").asString();
      result = _isSpam;
    } catch (Exception exception) {
      logger.error("Error ", exception);
      throw new RStormException("Error occured while executing RScript method: "+ exception.getMessage());
    }

    return result;
  }
}
