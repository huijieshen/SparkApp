package babylon.helpers

import java.io.{FileInputStream, File}
import java.util.Map.Entry
import java.util.Properties

import com.typesafe.config.ConfigValueType._
import com.typesafe.config.{ConfigFactory, ConfigValue}
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Try

object Configuration {

  // Initialize most of the variables

  val confPath = System.getProperty("os.name") match {
    case "Mac OS X" => "/Users/mrez/workspace/local/"
    case "Linux" => "/home/mrez/workspace/code/babylon/"

  }

  val (driver, dataFolder, mainLogFolder, schemaFile, traceList, preparedTraces) =
    try {
      val prop = new Properties()
      prop.load(new FileInputStream(confPath + "resources/shared.properties"))
      (
        prop.getProperty("driver"),
        prop.getProperty("dataFolder"),
        prop.getProperty("mainLogFolder"),
        prop.getProperty("schemaFile"),
        prop.getProperty("tracesList").split(" ").map(_.trim),
        prop.getProperty("preparedTraces")
        )
    } catch {
      case e: Exception =>
        e.printStackTrace()
        sys.exit(1)
    }


  private def getPropertiesList(key: String) = {
    val list = Try(config.getConfig(key).entrySet().toArray).getOrElse(Array())
    list.map(x => {
      val p = x.asInstanceOf[Entry[String, ConfigValue]]
      val k = p.getKey

      val v = p.getValue.valueType match {
        case BOOLEAN => config.getBoolean(key + "." + k)
        case STRING => config.getString(key + "." + k)
        case NUMBER => config.getDouble(key + "." + k)
        case _ => config.getString(key + "." + k)
      }
      (k.replace("_", "."), v.toString)
    })
  }

  // Spark configuration - loading from "resources/application.conf"
  private val config = ConfigFactory.load()
  lazy val SPARK_MASTER_HOST = Try(config.getString("spark.master_host")).getOrElse("local")
  lazy val SPARK_MASTER_PORT = Try(config.getInt("spark.master_port")).getOrElse(7077)
  lazy val SPARK_HOME = Try(config.getString("spark.home")).getOrElse("/home/spark")
  lazy val SPARK_MEMORY = Try(config.getString("spark.memory")).getOrElse("1g")
  lazy val SPARK_DRIVER_MEMORY = Try(config.getString("spark.driver.memory")).getOrElse("1g")
  lazy val SPARK_OPTIONS = getPropertiesList("spark.options")
  lazy val SPARK_DEFAULT_PAR = Try(config.getString("spark_default_parallelism")).getOrElse("8")

  def connectToSparkCluster(appName: String): SparkContext = {
    // get the name of the packaged
    val thisPackagedJar = new File("target/scala-2.10").listFiles.
      filter(x => x.isFile && x.getName.toLowerCase.takeRight(4) == ".jar").toList.map(_.toString)

    // Scan for external libraries in folder 'lib'
    // All the JAR files in this folder will be shipped to the cluster
    val libs = new File("lib").listFiles.
      filter(x => x.isFile && x.getName.toLowerCase.takeRight(4) == ".jar").
      toList.map(_.toString)

    val master = if (SPARK_MASTER_HOST.toUpperCase == "LOCAL") "local[16]" else SPARK_MASTER_HOST + ":" + SPARK_MASTER_PORT

    // Spark Context configuration
    val scConf =
      SPARK_OPTIONS.fold(
        new SparkConf()
          .setMaster(master)
          .setAppName(appName)
          .set("spark.executor.memory", SPARK_MEMORY)
          .setSparkHome(SPARK_HOME)
          .setJars(libs ++ thisPackagedJar)
          .set("spark.default.parallelism",SPARK_DEFAULT_PAR)
//          .set("spark.task.cpus","8")
          .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      )((c, p) => {
        // apply each spark option from the configuration file in section "spark.options"
        val (k, v) = p.asInstanceOf[(String, String)]
        c.asInstanceOf[SparkConf].set(k, v)
      }
      ).asInstanceOf[SparkConf]
    // Create and return the spark context to be used through the entire KYC application
    new SparkContext(scConf)
  }
}
