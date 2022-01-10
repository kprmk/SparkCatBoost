name := "SparkCatBoost"

version := "0.1"
scalaVersion := "2.11.12" // "2.12.10"
val sparkVersion =  "2.4.0" // "3.0.2"

//% Provided -> будет в исполняемой среде (требование к среде)

libraryDependencies ++= Seq(
  // spark
  "org.apache.spark" %% "spark-sql" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-mllib" % sparkVersion % Provided,
//   catboost
    "ai.catboost" %% "catboost-spark_2.4" % "1.0.3" exclude("org.json4s", "json4s-jackson_2.11"),
  // logging
//  "org.apache.logging.log4j" % "log4j-api" % "2.4.1",
//  "org.apache.logging.log4j" % "log4j-core" % "2.4.1",
)

dependencyOverrides ++= Seq(
  "com.google.guava" % "guava" % "15.0",
)
