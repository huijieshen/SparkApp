package babylon.samples



import babylon.helpers
import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.mllib.stat.{MultivariateStatisticalSummary, Statistics}
import org.apache.spark.ml.feature.Tokenizer
import org.apache.spark.ml.feature.HashingTF
import org.apache.spark.ml.feature.IDF
import org.apache.spark.streaming._
import org.apache.spark.sql.functions._

object wc {
  def main(args: Array[String]) {
    val context = helpers.Configuration.connectToSparkCluster("StreamCPI")
    val sqlContext = new SQLContext(context)


    val iters = 10000000

    import java.util.concurrent.ThreadLocalRandom
    import scala.math.exp

    val t1 = System.currentTimeMillis()
    val seqData =  (1 to iters).par.aggregate(0.0)((acc, x) => {
      val num = ThreadLocalRandom.current.nextDouble()
      acc + exp(num * num)
    }, _ + _)

    println("in ", System.currentTimeMillis() - t1 , " -> " , seqData)


    context.stop()
  }
}
