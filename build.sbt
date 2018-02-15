name := "Ignite2-5-Examples"

version := "0.1"

scalaVersion := "2.11.12"

resolvers += Resolver.mavenLocal

libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.2.1"
libraryDependencies += "org.apache.ignite" % "ignite-spark" % "2.5.0-SNAPSHOT"
libraryDependencies += "org.apache.flink" % "flink-scala_2.11" % "1.4.0"
libraryDependencies += "org.apache.flink" % "flink-clients_2.11" % "1.4.0"
libraryDependencies += "org.apache.flink" % "flink-table_2.11" % "1.4.0"