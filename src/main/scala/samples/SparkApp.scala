package babylon.samples

import babylon.helpers
import babylon.helpers.Configuration._
import org.apache.spark.sql.SQLContext


object SparkApp {

  def main(args: Array[String])= {

    val context = helpers.Configuration.connectToSparkCluster("SparkApp")
    val sqlContext = new SQLContext(context)

    context.stop
    }
}

