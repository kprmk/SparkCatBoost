import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.ml.linalg._
import org.apache.spark.sql.types._
import ai.catboost.spark._
import ru.yandex.catboost.spark.catboost4j_spark.core.src.native_impl.EModelType

object run extends App {
  println("Start")
  val spark = SparkSession.builder()
    .master("local[*]")
    .appName("ClassifierTest")
    .getOrCreate();
  spark.sparkContext.setLogLevel("WARN")
  println(spark)

  val srcDataSchema = Seq(
    StructField("features", SQLDataTypes.VectorType),
    StructField("label", StringType)
  )

  val trainData = Seq(
    Row(Vectors.dense(0.1, 0.2, 0.11), "0"),
    Row(Vectors.dense(0.97, 0.82, 0.33), "1"),
    Row(Vectors.dense(0.13, 0.22, 0.23), "1"),
    Row(Vectors.dense(0.8, 0.62, 0.0), "0")
  )

  println(spark.sparkContext.parallelize(trainData))
  println(StructType(srcDataSchema))

  val trainDf = spark.createDataFrame(spark.sparkContext.parallelize(trainData), StructType(srcDataSchema))
  println(trainDf)
  println("End")

    val trainPool = new Pool(trainDf)

  val evalData = Seq(
    Row(Vectors.dense(0.22, 0.33, 0.9), "1"),
    Row(Vectors.dense(0.11, 0.1, 0.21), "0"),
    Row(Vectors.dense(0.77, 0.0, 0.0), "1")
  )

  val evalDf = spark.createDataFrame(spark.sparkContext.parallelize(evalData), StructType(srcDataSchema))
  val evalPool = new Pool(evalDf)

  val classifier = new CatBoostClassifier

  // train a model
  val model = classifier.fit(trainPool, Array[Pool](evalPool))

  // apply the model
  val predictions = model.transform(evalPool.data)
  println("predictions")
  predictions.show()

  val HOME_DIR = System.getenv("HOME")

  // save the model
  val savedModelPath = s"file://$HOME_DIR/binclass_model"
  println(savedModelPath)
  model.write.overwrite().save(savedModelPath)

  // save the model as a local file in CatBoost native format
  val savedNativeModelPath = s"$HOME_DIR/binclass_model.json"
  println(savedNativeModelPath)
  model.saveNativeModel(
    savedNativeModelPath,
    format=EModelType.Json
  )

  // load the model (can be used in a different Spark session)
  val loadedModel = CatBoostClassificationModel.load(savedModelPath)

  val predictionsFromLoadedModel = loadedModel.transform(evalPool.data)
  println("predictionsFromLoadedModel")
  predictionsFromLoadedModel.show()


  // load the model as a local file in CatBoost native format

  val loadedNativeModel = CatBoostClassificationModel.loadNativeModel(savedNativeModelPath)

  val predictionsFromLoadedNativeModel = loadedNativeModel.transform(evalPool.data)
  println("predictionsFromLoadedNativeModel")
  predictionsFromLoadedNativeModel.show()

}


