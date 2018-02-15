import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.ignite.Ignition
import org.apache.ignite.cache.query.SqlFieldsQuery
import org.apache.ignite.configuration.CacheConfiguration
import org.apache.log4j.{Level, Logger}

import scala.collection.JavaConversions._

object FlinkExample extends App {

  println("Reading cache value from flink")

  val CACHE_NAME = "testCache"
  val tableName = "iot_data"

  val CONFIG = getClass.getResource("/cache.xml").getPath

  Ignition.setClientMode(true)
  val ignite = Ignition.start(CONFIG)
  val ccfg = new CacheConfiguration[Any, Any](CACHE_NAME).setSqlSchema("PUBLIC")

  ignite.getOrCreateCache(ccfg)


  Logger.getRootLogger.setLevel(Level.OFF)
  Logger.getLogger("org.apache.ignite").setLevel(Level.OFF)

  // For implicit conversions like converting RDDs to DataFrames

  println()
  println("Reading data from Ignite table:")
  println()

  val cache = ignite.cache[Any, Any](CACHE_NAME)

  val cursor = cache.query(new SqlFieldsQuery(s"SELECT * FROM $tableName limit 1"))
  val clCnt = cursor.getColumnsCount()
  val fNames = (0 until clCnt).map(cursor.getFieldName(_))
  val data = cache.query(new SqlFieldsQuery(s"SELECT * FROM $tableName limit 20")).getAll
  val data1: List[List[String]] = data.toList.map(_.map(_.toString).toList)

  println()
  println("Reading data as Dataset of flink")
  println()


  val env = ExecutionEnvironment.getExecutionEnvironment

  implicit val typeInfo = TypeInformation.of(classOf[List[String]])
  val dataset: DataSet[List[String]] = env.fromCollection(data1)

  println("***********************************************************************")
  println(fNames.mkString("[", ", ", "]"))
  dataset.print()
  println("***********************************************************************")

  ignite.close()

}
