##Read data attributes
dataset <- read.csv("/home/k2user/Dissertation/R-practice/data.csv", header = FALSE, sep=";")
##Read column headers
names <- read.csv("/home/k2user/Dissertation/R-practice/names.csv", header = FALSE, sep = ";")
##Apply column headers to dataset
names(dataset) <- sapply((1:nrow(names)), function(i) toString(names[i,1]))
##Transforming y column to factor values makes the SVM to use a classification output.
dataset$y <- as.factor(dataset$y)
##sample dataset for model building
sample <- dataset[sample(nrow(dataset), 1000),]
##Installing required packages
#install.packages('caret')
require(caret)
#install.packages('kernlab')
require(kernlab)
#install.packages('doMC')
require(doMC)
##split our dataset in two parts
trainIndex <- createDataPartition(sample$y, p = .8, list = FALSE, times = 1)
##One to train the SVM model
dataTrain <- sample[ trainIndex,]
##One to actually test data.
dataTest <- sample[-trainIndex,]
##no. of CPU cores to use for model evaluation.
registerDoMC(cores=1)
### finding optimal value of a tuning parameter
sigDist <- sigest(y ~ ., data = dataTrain, frac = 1)
### creating a grid of two tuning parameters, .sigma comes from the earlier line. we are trying to find best value of .C
svmTuneGrid <- data.frame(.sigma = sigDist[1], .C = 2^(-2:7))
### Train the model
x <- train(y ~ .,
data = dataTrain,
method = "svmRadial",
preProc = c("center", "scale"),
tuneGrid = svmTuneGrid,
trControl = trainControl(method = "repeatedcv", repeats = 5,
classProbs = FALSE))

##Actual prediction based on above model for 200 sample dataset.


## Function to find occurance of word #arg1 in text #arg2
occurance.word <- function(word, text) {
  return(lengths(regmatches(word, gregexpr(word, text, ignore.case=TRUE))))
}

## Function to calculate proportion of #arg1 no. of occurances / Total no. of words
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
  
  
 input <- inputText;

  newDataTest <- dataTest[1,1:57]
  newDataTest[1,1:57] <- proportion.word.bulk(list_data, inputText)
  
  print(newDataTest)
  
  ##Initialize to No SPAM
  pred <- 0
  
  pred <- predict(x, newDataTest[1,1:57])
  
  return(pred)
}

