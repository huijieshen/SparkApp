package SparkApp.samples

import babylon.helpers

// $example on$
import org.apache.spark.ml.feature.{SQLTransformer, RegexTokenizer, Tokenizer}

// $example off$
import org.apache.spark.sql.SQLContext

/**
  * Created by mrez on 1/28/16.
  */
object SQLTransform {

  def main(args: Array[String]): Unit = {


    val context = helpers.Configuration.connectToSparkCluster("TokenizerExample")
    val sqlContext = new SQLContext(context)

    // $example on$
    val df = sqlContext.createDataFrame(
      Seq((0, 1.0, 3.0), (2, 2.0, 5.0))).toDF("id", "v1", "v2")

    val sqlTrans = new SQLTransformer().setStatement(
      "SELECT *, (v1 + v2) AS v3, (v1 * v2) AS v4 FROM __THIS__")

    sqlTrans.transform(df).show()
    context.stop()
  }

}
