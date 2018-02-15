import org.apache.ignite.Ignition
import org.apache.ignite.configuration.CacheConfiguration
import org.apache.ignite.spark.IgniteDataFrameSettings._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession



object SparkDFReadExample extends App {
  println("Reading DF from ignite cache")
  val CONFIG = getClass.getResource("/cache.xml").getPath
  val tableName = "iot_data"

  Ignition.setClientMode(true)
  val ignite = Ignition.start(CONFIG)

  val spark = SparkSession
    .builder()
    .appName("Ignite Data Consumer")
    .master("local")
    .getOrCreate()



  Logger.getRootLogger.setLevel(Level.OFF)
  Logger.getLogger("org.apache.ignite").setLevel(Level.OFF)

  val df1 = spark.read
    .format(FORMAT_IGNITE) //Data source type.
    .option(OPTION_TABLE, tableName) //Table to read.
    .option(OPTION_CONFIG_FILE, CONFIG) //Ignite config.
    .load()

  println("***********************************************************************")
  df1.show()
  println("***********************************************************************")

  ignite.close()
}

