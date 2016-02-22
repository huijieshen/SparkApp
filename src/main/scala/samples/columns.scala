package samples

import babylon.helpers.Configuration

/**
 * Created by mrez on 5/28/15.
 */
object columns {
  def main(args: Array[String]): Unit = {
    val sc = Configuration.connectToSparkCluster("ColumnsExample")
    println()
    val rdd = sc.parallelize((1 to 4).map(x=>Seq("x_0", "x_1", "x_2", "x_3")))
//    println(rdd.count(), "1111111111111111111111")
//    print(rdd.toDebugString)
    val rdd1 = rdd.flatMap{x=>{(0 to x.size - 1).map(idx=>(idx, x(idx)))}}
  //  println(rdd1.count(), "222222222222222222222222")
//    print(rdd1.toDebugString)
    val rdd2 = rdd1.map(x=>(x,1))
    //println(rdd2.first(), "33333333333333333333")
//    println(rdd2)
    val rdd3 = rdd2.reduceByKey(_+_)
  //  println(rdd3.first(), "44444444444444444444444")
//    println(rdd3)

    rdd3.take(4)





    sc.stop()
  }

}
