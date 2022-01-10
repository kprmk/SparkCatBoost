name := "SparkCatBoost"

version := "0.1"
scalaVersion := "2.11.12" // "2.12.10"
val sparkVersion =  "2.4.0" // "3.0.2"

//% Provided -> that dependency will be in env of execution (not to be included in JAR)
// For running (project) comment % Provided TODO Fix that

libraryDependencies ++= Seq(
  // spark
  "org.apache.spark" %% "spark-sql" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-mllib" % sparkVersion % Provided,
  // catboost
  "ai.catboost" %% "catboost-spark_2.4" % "1.0.3" exclude("org.json4s", "json4s-jackson_2.11")
)

dependencyOverrides ++= Seq(
  "com.google.guava" % "guava" % "15.0"
)

//assemblyMergeStrategy in assembly := {
//  case PathList("META-INF", "classnames.properties")     => MergeStrategy.filterDistinctLines
//  case PathList("META-INF", "classnames-jdk.properties") => MergeStrategy.filterDistinctLines
//  case PathList("META-INF", _*)                          => MergeStrategy.discard
//  case _                                                 => MergeStrategy.first
//}
