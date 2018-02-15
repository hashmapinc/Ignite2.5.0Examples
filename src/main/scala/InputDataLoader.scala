import org.apache.spark.sql.{Encoder, SparkSession}

object InputDataLoader {
  def load[T <: Product : Encoder](spark: SparkSession, pathToData: String) = {
    spark.read.json(pathToData).as[T]
  }
}