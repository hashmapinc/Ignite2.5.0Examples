import org.apache.ignite.spark.IgniteDataFrameSettings._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession


object SparkDFWriteExample extends App {

  println("Example to write DF in ignite cache")

  val tableName = "iot_data"
  val CONFIG = getClass.getResource("/cache.xml").getPath

  import org.apache.ignite.Ignition

  Ignition.setClientMode(true)
  val ignite = Ignition.start(CONFIG)

  val spark = SparkSession
    .builder()
    .appName("Ignite Data Producer")
    .master("local")
    .getOrCreate()


  Logger.getRootLogger.setLevel(Level.INFO)
  Logger.getLogger("org.apache.ignite").setLevel(Level.INFO)

  // For implicit conversions like converting RDDs to DataFrames
  import spark.implicits._

    val df = InputDataLoader.load[DeviceIoTData](spark, getClass.getResource("/iot_devices.json").getPath).toDF()

    println()
    println("Writing Data Frame to Ignite:")
    println()

    //Writing content of data frame to Ignite.

  df.write
      .format(FORMAT_IGNITE)
      .option(OPTION_CONFIG_FILE, CONFIG)
      .option(OPTION_TABLE, tableName)
      .option(OPTION_CREATE_TABLE_PRIMARY_KEY_FIELDS, "device_id")
      .option(OPTION_CREATE_TABLE_PARAMETERS, "template=replicated")
      .save()

    println("Done!")
    ignite.close()
}









