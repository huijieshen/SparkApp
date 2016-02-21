package templates

import org.apache.spark._
import org.apache.spark.streaming._ // not necessary in Spark 1.3+

object KafkaExample {
  def main(args: Array[String]) {
    if (args.length < 2) {
      System.err.println("Usage: NetworkWordCount <hostname> <port>")
      System.exit(1)
    }

    StreamingExamples.setStreamingLogLevels()

    // Create the context with a 1 second batch size
    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    val ssc = new StreamingContext(conf, Seconds(1))

    // Create a DStream that will connect to hostname:port, like localhost:9999
    val lines = ssc.socketTextStream("localhost", 9997)
    // Split each line into words
    val words = lines.flatMap(_.split(" ")) // not necessary in Spark 1.3+
    // Count each word in each batch // not necessary in Spark 1.3+

    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)

    // Print the first ten elements of each RDD generated in this DStream to the console
    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
