## Function to find occurance of word #arg1 in text #arg2
occurance.word <- function(word, text) {
  return(lengths(regmatches(word, gregexpr(word, text, ignore.case=TRUE))))
}

## Function to calculate proportion of #arg1 no. of occurances / Total no. of words
proportion.word <- function(word, text) {
  return(100*lengths(regmatches(word, gregexpr(word, text, ignore.case=TRUE)))/lengths(regmatches(text, gregexpr("\\w+", text))))
}

proportion.word.bulk <- function(list, text) {
  vector <- c()
  for (word in list) {
    vector <- c(vector, proportion.word(word, text))
  }
  vector <- c(vector, 0);  #Not SPAM
  return(vector);
}



text <- "Hello, this is test word for testing text,email, but text is now longer"
#word <- "test"

#occurance.word(word, text)
#proportion.word(word, text)


list_data <- c("make","address","all","3d","our","over","remove","internet", "order", "mail", "receive", "will",
                    "people", "report", "addresses", "free", "business","email", "you", "credit", "your"
               ,"font", "000","money","hp","hpl","george","650","lab", "labs", "telnet", "857", "data",
               "415", "85", "technology", "1999", "parts", "pm", "direct", "cs", "meeting", "original",
               "project", "re", "edu", "table", "conference",";", '\\(', '\\[','!','\\$','\\#',"\\W{8}","\\W{10}", "\\W{15}")

#print(list_data)
#proportion.word.bulk(list_data, text)

newDataTest <- dataTest[1,1:58]
newRecordVector <- proportion.word.bulk(list_data, text)
newDataTest[1,1:58] <- newRecordVector

##Initialize to No SPAM
pred <- 0

pred <- predict(x, newDataTest[1,1:57])

print(pred)



