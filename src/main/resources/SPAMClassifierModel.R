##Read data attributes
dataset <- read.csv("/home/k2user/Dissertation/R-practice/data.csv", header
= FALSE, sep=";")
##Read column headers
names <- read.csv("/home/k2user/Dissertation/R-practice/names.csv", header =
FALSE, sep = ";")
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

