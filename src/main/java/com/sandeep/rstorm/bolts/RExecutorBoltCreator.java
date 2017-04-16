package com.sandeep.rstorm.bolts;

import com.sandeep.rstorm.R.RExecutor;
import com.sandeep.rstorm.R.RNativeExecutor;
import com.sandeep.rstorm.R.RServeExecutor;
import com.sandeep.rstorm.RStormConstants;
import com.sandeep.rstorm.config.ReadConfig;
import com.sandeep.rstorm.errorhandler.RStormException;
import org.apache.storm.topology.IRichBolt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by k2user on 16/4/17.
 */
public class RExecutorBoltCreator {

  Logger logger = LoggerFactory.getLogger(RExecutorBoltCreator.class);

  public RExecutor getRExecutorBolt(String boltExecutor) {
    String initializerScript = ReadConfig.properties.getProperty(ReadConfig.R_SCRIPT_PATH);
    logger.info("R Initializer script {}", initializerScript);

    switch(RExecutorEnum.valueOf(boltExecutor)) {
      case RJava:
        logger.info("Initializing RJava Engine for Bolt execution");
        RExecutor rExecutor = new RNativeExecutor();

        logger.info("Creating image.RData of Initializer Script {}", initializerScript);
        rExecutor.initializeScript(initializerScript);

        return rExecutor;

      case RServe:
        logger.info("Initializing RServer Engine for Bolt execution");
        RExecutor rServeExecutor = new RServeExecutor();

        logger.info("Creating image.RData of Initializer Script {}", initializerScript);
        rServeExecutor.initializeScript(initializerScript);
        return rServeExecutor;

      case MultiLang:
        logger.info("Initialzing Multi-Lang ShellBolt..");
        break;

      default:
        logger.error("Invalid Argument {}", boltExecutor);
        throw new RStormException("Invalid Argument "+ boltExecutor +"List of Valid Executor (RJava, RServe, MultiLang)");
    }

    return null;
  }
}
