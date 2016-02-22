package samples

import babylon.helpers.Configuration

object TemplateApp {

  def main(args: Array[String]) {
    val sc = Configuration.connectToSparkCluster("TemplateApp")

    // the SparkContext is now available as sc
    //val f = sc.textFile("README.md")
    //println(f.count)

    /*
    val sqlContext = new SQLContext(sc)
    val df = sqlContext.
      load("com.databricks.spark.csv",
        Map("path" -> "/home/mrez/workspace/data/google-cluster-data-1.csv",
          "header" -> "true",
          "delimiter" -> " "))


    df.select("Time") show (10)
    */
    //val byTime = df.map(_.split(" ")).groupBy(_(1))

    // GraphX is also available here

//    val input = sc.parallelize(List(1, 2, 3, 4))
//    val result = input.fold(0)((x, y) => (x + y))
//    println(result)

    sc.stop()
  }

}
