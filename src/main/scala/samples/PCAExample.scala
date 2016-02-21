package babylon.samples

import babylon.helpers
import org.apache.spark.mllib.linalg.Vectors

// $example on$
import org.apache.spark.ml.feature.{RegexTokenizer, Tokenizer}

// $example off$
import org.apache.spark.sql.SQLContext
import org.apache.spark.ml.feature.PCA
import org.apache.spark.mllib.linalg.Vectors

/**
  * Created by mrez on 1/28/16.
  */
object PCAExample {

  def main(args: Array[String]): Unit = {

    val context = helpers.Configuration.connectToSparkCluster("PCAExample")
    val sqlContext = new SQLContext(context)

    // $example on$
    val data = Array(
      Vectors.sparse(5, Seq((1, 1.0), (3, 7.0))),
      Vectors.dense(2.0, 0.0, 3.0, 4.0, 5.0),
      Vectors.dense(4.0, 0.0, 0.0, 6.0, 7.0)
    )

    println("********")

    (0 until 4).foreach{ i =>
      println(data(0).apply(i))
    }

//    data.foreach(_.toDense.toArray.foreach(print(" %s", _)))

    // data.flatMap(Tuple1.apply).foreach(print)
    println()
    println("********")

    val df = sqlContext.createDataFrame(data.map(Tuple1.apply)).toDF("features")

    df.show(10)

    val pca = new PCA()
      .setInputCol("features")
      .setOutputCol("pcaFeatures")
      .setK(3)
      .fit(df)
    val pcaDF = pca.transform(df)
    val result = pcaDF.select("pcaFeatures")
    result.show()
    context.stop()
  }

}
