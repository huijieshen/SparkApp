import sbt.Keys._
import sbt._
import xerial.sbt.Pack._

import scala.util.Properties

object AppBuild extends Build {

  lazy val some = Properties.propOrEmpty("driver")

  lazy val versions = Map(
    'slf4j -> Properties.envOrElse("SL4J_VERSION", "1.7.12"),
    'spark -> Properties.envOrElse("SPARK_VERSION", "1.6.0"),
    'hadoop -> Properties.envOrElse("HADOOP_VERSION", "2.6.0"),
    'sparkcsv -> "1.2.0"
  )

  lazy val root = Project(id = "root", base = file("."),
    settings = Seq(
      name := "SparkApp",
      version := "0.1",
      scalaVersion := "2.10.5",

      // Change default location of resources
      resourceDirectory in Compile := file("./resources"),
      // Change default location of unmanaged libraries
      unmanagedBase in Compile := file("./lib"),

      // Apparently sbt mistakes different versions of scala and this fixes it
      fork := true,

      ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) },

      // Resolvers
      resolvers ++= Seq(
        "JBoss Repository" at "http://repository.jboss.org/nexus/content/repositories/releases/",
        "Spray Repository" at "http://repo.spray.cc/",
        "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
        "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/",
        "Apache HBase" at "https://repository.apache.org/content/repositories/releases",
        "Twitter Maven Repo" at "http://maven.twttr.com/",
        "scala-tools" at "https://oss.sonatype.org/content/groups/scala-tools",
        "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
        "Second Typesafe repo" at "http://repo.typesafe.com/typesafe/maven-releases/",
        "Mesosphere Public Repository" at "http://downloads.mesosphere.io/maven",
        "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
        Resolver.sonatypeRepo("public")
      ),

      // External libraries
      libraryDependencies ++= Seq(
        "org.apache.spark" %% "spark-core" % versions('spark) withSources() withJavadoc(),
        "org.apache.spark" %% "spark-sql" % versions('spark) withSources() withJavadoc(),
        "org.apache.spark" %% "spark-hive" % versions('spark) withSources() withJavadoc(),
        "org.apache.spark" %% "spark-streaming" % versions('spark) withSources() withJavadoc(),
        "org.apache.spark" %% "spark-streaming-kafka" % versions('spark) withSources() withJavadoc(),
        "org.apache.spark" %% "spark-streaming-flume" % versions('spark) withSources() withJavadoc(),
        "org.apache.spark" %% "spark-mllib" % versions('spark) withSources() withJavadoc(),
//        "com.databricks"   %% "spark-csv_2.10" % versions('sparkcsv),
        "com.typesafe.akka" %% "akka-actor" % "2.3.14",
//        "org.slf4j" % "slf4j-log4j12" % versions('slf4j) % "runtime",
//        "org.slf4j" % "slf4j-api"     % versions('slf4j) % "provided",
        "org.slf4j" % "slf4j-nop"     % versions('slf4j) % "test"
      )
    )

    ++ packSettings
      ++ Seq(
        packMain := Map(
          "template" -> "SparkApp"
        )
      )
  )
}
