# Exploring upcoming Ignite 2.5.0-snapshot library to stored data in cache with Spark and Flink frameworks.

Contains examples to shared data across spark jobs (dataframes) and across frameworks (flink)

To use ignite 2.5.0-SNAPSHOT version, clone ignite from github. 

# Building the Binaries
NOTE: JDK version should be 1.7.0-* or >= 1.8.0-u40.

#Unpack the source package
$ unzip -q apache-ignite-2.3.0-src.zip

$ cd apache-ignite-2.3.0-src
 
#Build In-Memory Data Fabric release (without LGPL dependencies)

$ mvn clean package -DskipTests

#To create local maven jar of 2.5.0-snapshot

$ mvn clean install -DskipTests

For other type of binaries please follow ignite download documentation.


# Ignite Configuring

If you are running ignite in a distributed environment. Please provied the host IP in cache.xml

# Running ignite node

./ignite.sh PATH_TO_CACHE_DIR/cache.xml

  


