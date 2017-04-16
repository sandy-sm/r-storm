package com.sandeep.rstorm.R;

/**
 * Created by k2user on 16/4/17.
 */
public interface RExecutor {

  public void initializeScript(String path);

  public String executeScriptMethod(String methodName, String inputData);

}
