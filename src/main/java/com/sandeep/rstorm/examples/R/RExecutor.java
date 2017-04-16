package com.sandeep.rstorm.examples.R;

import com.google.common.io.Files;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by k2user on 1/4/17.
 */
public class RExecutor {

    private static final Logger logme = LoggerFactory.getLogger(RExecutor.class);

    public static void main(String[] args) throws IOException, Exception {
        String maildata = Files.toString(new File("src/main/resources/sampleMailData"), Charset.defaultCharset());

        logme.info("Identifying email contents...");
        logme.info("Contents = {}", maildata);

        long startTime = System.currentTimeMillis();

        // Start Rengine.
        RConnection engine = new RConnection();
        File file = new File("src/main/resources/listData.RData");
        String scriptFilePath = file.getPath();

        logme.info("Loading SPAMClassifier R Script File from : {}", scriptFilePath);
        engine.eval("load(file = \""+scriptFilePath+"\")");

        logme.info("Detecting SPAM...");
        engine.eval("result <- isSpam(\""+maildata+"\");");

        String _isSpam = engine.eval("as.character(result)").asString();

        if (Boolean.valueOf(_isSpam)) {
            logme.info("Given email is SPAM");
        } else {
            logme.info("Given email is Not SPAM");
        }

        logme.info("Executed! In {} secs", (System.currentTimeMillis() - startTime)/1000);
        System.exit(0);
    }
}
