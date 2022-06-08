name := "spark-cat-boost"

version := "0.1"
scalaVersion := "2.11.12"
val sparkVersion =  "2.4.0"
val catBoostVersion = "0.26" // "1.0.3"

//% Provided -> that dependency will be in env of execution (not to be included in JAR)
// For running (project) comment % Provided TODO Fix that

//resolvers ++= Seq(
//  "bintray-spark-packages" at "https://dl.bintray.com/spark-packages/maven",
//  "Typesafe Simple Repository" at "https://repo.typesafe.com/typesafe/simple/maven-releases",
//  "MavenRepository" at //TODO "https://search.maven.org/artifact/ai.catboost/catboost-spark_2.4_2.11"//"https://mvnrepository.com"
//)
//resolvers += MavenCache("local-maven", file("path/to/maven-repo/releases"))

libraryDependencies ++= Seq(
  // spark
  "org.apache.spark" %% "spark-sql" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-mllib" % sparkVersion  % Provided from("file://"),
  // catboost
    "ai.catboost" %% "catboost-spark_2.4" % catBoostVersion exclude("org.json4s", "json4s-jackson_2.11")
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
