library(Storm, quietly = TRUE)

storm = Storm$new();

storm$lambda  = function(s) {
    t = s$tuple;
    s$ack(t);

    proportion.word <- function(word, text) {
        return(100*lengths(regmatches(word, gregexpr(word, text, ignore.case=TRUE, fixed = TRUE)))/lengths(regmatches(text, gregexpr("\\w+", text))))
    }

    proportion.word.bulk <- function(list, text) {
        vector <- c()
        for (word in list) {
            vector <- c(vector, proportion.word(word, text))
        }
        vector <- c(vector, 0);  #Not SPAM
        return(vector);
    }

    isSpam <- function(inputText) {
        #make vector of suspect words
        list_data <- c("make","address","all","3d","our","over","remove","internet", "order", "mail", "receive", "will",
        "people", "report", "addresses", "free", "business","email", "you", "credit", "your"
        ,"font", "000","money","hp","hpl","george","650","lab", "labs", "telnet", "857", "data",
        "415", "85", "technology", "1999", "parts", "pm", "direct", "cs", "meeting", "original",
        "project", "re", "edu", "table", "conference",";", '\\(', '\\[','!','\\$','\\#',"\\W{8}","\\W{10}", "\\W{15}")



        newDataTest <- dataTest[1,1:58]
        newRecordVector <- proportion.word.bulk(list_data, text)
        newDataTest[1,1:58] <- newRecordVector

        print(newDataTest)

        ##Initialize to No SPAM
        pred <- 0

        pred <- predict(x, newDataTest[1,1:57])

        return(pred)
    }

    result <- isSpam(t$input[1])

    print(result)

    t$output = vector(mode="character",length=1);
    t$output[1] = paste(c("spam",result),collapse="");

    s$emit(t);


}


storm$run();