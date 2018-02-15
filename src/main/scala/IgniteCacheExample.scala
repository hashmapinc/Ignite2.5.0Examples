import org.apache.ignite.Ignition
import org.apache.ignite.cache.query.SqlFieldsQuery
import org.apache.ignite.configuration.CacheConfiguration
import org.apache.log4j.{Level, Logger}
import scala.collection.JavaConversions._

object IgniteCacheExample extends App {


  println("Reading cache value directly from ignite")

  val CACHE_NAME = "testCache"
  val tableName = "iot_data"

  val CONFIG = getClass.getResource("/cache.xml").getPath

  Ignition.setClientMode(true)
  val ignite = Ignition.start(CONFIG)


  val ccfg = new CacheConfiguration[Any, Any](CACHE_NAME)
  val cache = ignite.getOrCreateCache(ccfg)


  Logger.getRootLogger.setLevel(Level.OFF)
  Logger.getLogger("org.apache.ignite").setLevel(Level.OFF)

  println()
  println("Reading data from Ignite table:")
  println()

  //val cache = ignite.cache[Any, Any](CACHE_NAME)

  private val cursor = cache.query(new SqlFieldsQuery(s"SELECT * FROM $tableName limit 20"))
  val clCnt = cursor.getColumnsCount()
  val fNames = (0 until clCnt).map(cursor.getFieldName(_))
  val data = cache.query(new SqlFieldsQuery(s"SELECT * FROM $tableName limit 20")).getAll

  println("***********************************************************************")
  println(fNames.mkString("[", ", ", "]"))
  data.foreach { row ⇒ println(row.mkString("[", ", ", "]")) }
  println("***********************************************************************")

  ignite.close()

}
