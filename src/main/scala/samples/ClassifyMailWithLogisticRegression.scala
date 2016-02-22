package samples

import org.apache.spark._
import org.apache.spark.mllib.regression._
import org.apache.spark.mllib.linalg._
import org.apache.spark.mllib.classification._
import org.apache.spark.mllib.evaluation._

object ClassifyMailWithLogisticRegression {

  def sparkJob() = {
    // Specify the path to your data file
    val conf = new SparkConf()
      .setAppName("Spark classify mail as spam or non-spam with logistic regression")
      //Add more config if needed
      .setMaster("local")

    val sc = new SparkContext(conf)

    // load the data
    val data = sc.textFile("data/spambase.csv")

    // parse data
    val parsedData = data.map { line =>
      val parts = line.split(',').map(_.toDouble)

      // prepare data into RDD[LabeledPoint]
      // LabeledPoint is a couple (label, features)
      LabeledPoint(parts(0), Vectors.dense(parts.tail))
    }

    // Split data into 2 sets : training (60%) and test (40%).
    val splits = parsedData.randomSplit(Array(0.6, 0.4), seed = 11L)
    // training split
    val training = splits(0)
    // test split
    val test = splits(1)

    // training model on training set with 100 iterations
    val model = LogisticRegressionWithSGD.train(training, 100)

    val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))

    val metrics = new BinaryClassificationMetrics(predictionAndLabel)

    val accuracy = 1.0 * predictionAndLabel.filter(x => x._1 == x._2).count() / test.count()

    println("accuracy " + accuracy)
    println("metrics " + metrics.areaUnderPR())
    println("metrics " + metrics.areaUnderROC())

    sc.stop
  }

  def main(args: Array[String])= sparkJob()
}