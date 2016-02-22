package samples

object stdd {

  def main(args: Array[String]): Unit = {

    println("nothing")
//    val context = Configuration.connectToSparkCluster("MultiCSVSample")
//    val sqlContext = new SQLContext(context)

//    import sqlContext.implicits._


//    val they = context.parallelize(Array(1, 2, 3, 1, 4, 4, 10, 5, 6, 10)).toDF().describe() //.collect().map(a => print(a(0)))
//    they.show
//    they.printSchema()
//    they.registerTempTable("SUMS")

    //    val otherCount = sqlContext.sql("SELECT * FROM SUMS").show()
//    val a = sqlContext.sql("SELECT * FROM SUMS").map(
//      Row => if (Row.getString(0) == "stddev") TestSomething(Row.get(1)) else Option(null)
//    )

    //    a.exists(print)
//    a.map(a => if (a.isDefined) print(a.getOrElse() + " is stddev"))
    //    val nums = otherCount.collect.map()
    //      nums.map(print)
    //    nums.
    //    print(nums)
    //    nums.collect.map(println)

    //      select("_1").show()//.select("count").map(_(0)).collect().foreach(println)
    //    val mean = they.groupBy().mean().head.getDouble(0)
    //    val them = they.map(row => row(0)).collect()


//    context.stop()
  }

}
