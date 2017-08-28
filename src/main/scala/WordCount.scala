package main

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object WordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Word Counter")
    val sc = new SparkContext(conf)
    val textFile = sc.textFile("file:///usr/local/spark/README.md")
    val tokenizedFileData = textFile.flatMap(line => line.split(" "))
    val countPrep = tokenizedFileData.map(word => (word, 1))
    val counts = countPrep.reduceByKey((accumValue, newValue) => accumValue + newValue)
    val sortedCounts = counts.sortBy(kvPair => kvPair._2, false)
    sortedCounts.saveAsTextFile("file:///home/ec2-user/spark/wordcount")
  }
}
