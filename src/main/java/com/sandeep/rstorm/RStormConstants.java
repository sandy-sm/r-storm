package com.sandeep.rstorm;

/**
 * Created by k2user on 16/4/17.
 */
public interface RStormConstants {

  public static final String R_DEFAULT_PORT = "6311";
  public static final String R_STARTUP_SCRIPT_LOCATION = "src/main/resources/SpamDetector.R";
  public static final String R_SCRIPT_IMAGE_LOCATION = "src/main/resources/image.RData";

  public static final String R_SCRIPT_MULTI_LANG_COMMAND = "Rscript";
  public static final String R_SCRIPT_MULTI_LANG_SCRIPT = "script.R";

  public static final String STORM_TOPOLOGY_ENV = "topology.environment";
  public static final String STORM_TOPOLOGY_ENV_BOLT_EXECUTOR_KEY = "boltExecutor";

  public static final String STORM_SINK_BOLT_FIELD = "scriptInput";

}
