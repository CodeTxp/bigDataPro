package com.txp.spark.project.utils

import java.util.Date

import org.apache.commons.lang3.time.FastDateFormat

/**
  * 时间格式转换
  */
object DateUtils {
  val YYYYMMDDHHMMSS_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")
  val TAG_FORMAT = FastDateFormat.getInstance("yyyyMMdd")

  /**
    * 把当前时间转换成时间戳
    *
    * @param time
    * @return
    */
  def getTime(time: String) = {
    YYYYMMDDHHMMSS_FORMAT.parse(time).getTime
  }

  /**
    * 转换成目标格式yyyyMMdd
    *
    * @param time
    * @return
    */
  def parseToMin(time: String) = {
    TAG_FORMAT.format(new Date(getTime(time)))
  }

  def main(args: Array[String]): Unit = {
    print(parseToMin("2017-11-26 00:36:26"))
  }
}
