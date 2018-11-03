package com.txp.spark.project

import com.txp.spark.project.dao.{CategoryClickCountDao, CategorySearchCountDao}
import com.txp.spark.project.domain.{CategoryClickCount, CategorySerachCount, ClickLog}
import com.txp.spark.project.utils.DateUtils
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

object StatStreamingApp {

  def main(args: Array[String]): Unit = {

    val streamingContext = new StreamingContext("local[*]", "StatStreamingApp", Seconds(5))

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "s202:9092,s203:9092,s204:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "use_a_separate_group_id_for_each_stream",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("flumeTopic")
    val logs = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    ).map(_.value())

    ///stream.print()
    //156.29.167.10   2018-10-31 10:39:18     "GET /toukouxu/821 HTTP/1.0"    https://www.sogou.com/web?qu=▒Գ▒        200
    //确定格式
    var cleanLog = logs.map(line => {
      var infos = line.split("\t")
      var url = infos(2).split(" ")(1)
      var categoryId = 0
      if (url.startsWith("/www")) {
        categoryId = url.split("/")(2).toInt
      }
      ClickLog(infos(0), DateUtils.parseToMin(infos(1)), categoryId, infos(3), infos(4).toInt)
    }).filter(log => log.categoryId != 0)

    cleanLog.print()
    //每个类别的每天的点击量 day_creategoryId
    cleanLog.map(log => {
      //获取log里面的时间 类别
      (log.time.substring(0, 8) + "_" + log.categoryId, 1)
    }).reduceByKey(_ + _).foreachRDD(rdd => {
      rdd.foreachPartition(partitions => {
        val list = new ListBuffer[CategoryClickCount]
        partitions.foreach(pair => {
          list.append(CategoryClickCount(pair._1, pair._2))
        })
        CategoryClickCountDao.save(list)
      })
    })

    //每个栏目下面从渠道过来的流量
    //  ip                time                 categoryId              refer                   statusCode
    //156.10.100.29	2018-10-30 18:58:40	"GET www/4 HTTP/1.0"	https://www.sogou.com/web?qu=猎场	404
    cleanLog.map(log => {
      val url_refer=log.refer
      var host=""
      if(url_refer=="-"){
          host="AiQiYi"  //来自爱奇艺视频自己
      }else{
        val url = url_refer.replace("//", "/")
        val splits = url.split("/")
        if (splits.length > 2) {
          host = splits(1) //得到www.sogou.com
        }
      }
      (host, log.time)
    }).filter(x => x._1 != "").map(x => {
      (x._2.substring(0, 8) + "_" + x._1, 1)
    }).reduceByKey(_ + _).foreachRDD(rdd => {
      rdd.foreachPartition(partitions => {
        val list = new ListBuffer[CategorySerachCount]
        partitions.foreach(pair => {
          list.append(CategorySerachCount(pair._1, pair._2))
        })
        CategorySearchCountDao.save(list)
      })
    })
    streamingContext.start()
    streamingContext.awaitTermination()

  }

}
