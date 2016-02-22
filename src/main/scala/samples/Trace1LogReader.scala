package samples

import org.apache.spark._
import org.apache.spark.sql._
import babylon.helpers.Configuration


// One method for defining the schema of an RDD is to make a case class with the desired column
// names and types.
case class LogRecords(Time: Int, jobID: Int,
                      TaskID: Int, JobType: Int,
                      Normalized_Task_Cores: Float,
                      Normalized_Task_Memory: Float)

object Trace1LogReader {
  def main(args: Array[String]) {
    val sc = Configuration.connectToSparkCluster("GCLogReader")
    val sqlContext = new SQLContext(sc)

    // Select Log File
    val logFilePATH = "/home/mrez/workspace/data/"
    val logFile = logFilePATH + "google-cluster-data-1.csv"


    val logs = sqlContext.load("com.databricks.spark.csv", Map("path" -> logFile, "header" -> "true", "delimiter" -> " "))

    logs.schema.printTreeString()
    logs.select("Time", "ParentID").show(10)
    //logs.show(10)
    println("All: " + logs.select("*").count())
    println("Times: " + logs.select("Time").distinct.count())
    println("ParentID: " + logs.select("ParentID").distinct.count())
    println("TaskID: " + logs.select("TaskID").distinct.count())
    println("JobType: " + logs.select("JobType").distinct.count())
    println("NrmlTaskCores: " + logs.select("NrmlTaskCores").distinct.count())
    println("NrmlTaskMem" + logs.select("NrmlTaskMem").distinct.count())
    println("else: " + logs.select("").distinct.count())

    //val somelogs = logs.sample(true,0.001,1654640)
    //println(somelogs.count())

    //logs.registerTempTable("logs")

    //println(sqlContext.sql("SELECT * FROM logs WHERE 'NrmlTaskCores' > 0.1").count())

    //df.show()

    //val carsRDD = sqlContext.csvFile("/home/mrez/workspace/code/spark-template-app/cars.csv")
    //val ldf = sqlContext
    // Show the content of the DataFrame
    //println(cars.select())
    sc.stop()

  }
}
