package babylon.samples

import babylon.helpers
import org.apache.spark.{SparkConf, SparkContext}

// $example on$
import org.apache.spark.ml.feature.{RegexTokenizer, Tokenizer}

// $example off$
import org.apache.spark.sql.SQLContext

/**
  * Created by mrez on 1/28/16.
  */
object TokenizerExample {

  def main(args: Array[String]): Unit = {


    val context = helpers.Configuration.connectToSparkCluster("TokenizerExample")
    val sqlContext = new SQLContext(context)

    // $example on$
    val sentenceDataFrame = sqlContext.createDataFrame(Seq(
      (0, "Hi I heard about Spark"),
      (1, "I wish Java could use case classes"),
      (2, "Logistic,regression,models,are,neat")
    )).toDF("label", "sentence")

    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
    val regexTokenizer = new RegexTokenizer()
      .setInputCol("sentence")
      .setOutputCol("words")
      .setPattern("\\W") // alternatively .setPattern("\\w+").setGaps(false)

    val tokenized = tokenizer.transform(sentenceDataFrame)
    tokenized.select("words", "label").take(3).foreach(println)
    val regexTokenized = regexTokenizer.transform(sentenceDataFrame)
    regexTokenized.select("words", "label").take(3).foreach(println)
    // $example off$
    context.stop()
  }

}
