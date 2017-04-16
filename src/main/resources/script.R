#!/usr/bin/Rscript
library(Storm)

storm = Storm$new();

storm$lambda  = function(s) {
    t = s$tuple;

    input <- t$input;
    print("tuple input is");
    print(input);

    load(file = "/home/k2user/my-dev/r-storm/src/main/resources/img.RData");
    result <- isSpam(input);
    isSpam <- as.character(result);

    t$output = vector(mode="character",length=2);
    t$output[0] = as.character(input);
    t$output[1] = as.character(result);

    s$emit(t);
    s$ack(t);
}

storm$run();