package samples

object GCLogReader {

//  def main(args: Array[String]): Unit = {
//    import babylon.helpers.traceVars._
//
//    val context = configuration.connectToSparkCluster("MultiGCCSVSample")
//
//    val sqlContext = new SQLContext(context)
//
//    // this is used to implicitly convert an RDD to a DataFrame.
//
////    val schemas = new Map[String,StructType]()
//
//    val schemas = tracesList.map { trace =>
//      (trace, schemaSelect(trace, sqlContext))
//    }.toMap[String,StructType]
//
//    // Check if we haven't already built the paraquet files make them and then don't do
//    // it ever again.
//    tracesList.foreach { trace =>
//      if (!new File(processedTraces + trace).exists()) {
//        getTrace(trace, schemas(trace), sqlContext)
////          .reduce(_ unionAll _).registerTempTable(helpers.trace.toUpperCase())
//
//        sqlContext.sql("SELECT * FROM " + trace.toUpperCase())
//          .write.parquet(processedTraces + trace)
//      } else {
//        sqlContext.read.parquet(processedTraces + trace).registerTempTable(trace.toUpperCase)
//      }
//    }
//
//    // TODO: test if all the records are imported correctly
//    tracesList.map { trace =>
//      println(trace.toUpperCase)
//      sqlContext.sql("SELECT * FROM " + trace.toUpperCase).printSchema()
//    }
//
//
//    // Configure an ML pipeline, which consists of three stages: tokenizer, hashingTF, and lr.
//    val tokenizer = new Tokenizer()
//      .setInputCol("text")
//      .setOutputCol("words")
//    val hashingTF = new HashingTF()
//      .setNumFeatures(1000)
//      .setInputCol(tokenizer.getOutputCol)
//      .setOutputCol("features")
//    val lr = new LogisticRegression()
//      .setMaxIter(10)
//      .setRegParam(0.01)
//    val pipeline = new Pipeline()
//      .setStages(Array(tokenizer, hashingTF, lr))
//
//    context.stop()
//  }
//
//  def verifyRead () = {
//
//  }

}

//    val job_events =  getTrace("job_events", sqlContext)
//    val task_events = getTrace("task_events", sqlContext)
//    val task_usage = getTrace("task_usage", sqlContext)
//    val task_constraints = getTrace("task_constraints", sqlContext)
//    val machine_events = getTrace("machine_events", sqlContext)
//    val machine_attributes = getTrace("machine_attributes", sqlContext)
