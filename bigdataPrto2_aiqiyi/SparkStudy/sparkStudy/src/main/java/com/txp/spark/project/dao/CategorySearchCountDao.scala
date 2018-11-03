package com.txp.spark.project.dao

import com.txp.spark.project.domain.CategorySerachCount
import com.txp.spark.project.utils.HBaseUtils
import org.apache.hadoop.hbase.client.Get
import org.apache.hadoop.hbase.util.Bytes

import scala.collection.mutable.ListBuffer

object CategorySearchCountDao {
  val tableName = "category_serach_count"
  val cf = "info"
  val qualifer = "click_count"

  //保存数据
  def save(list: ListBuffer[CategorySerachCount]): Unit = {
    val table = HBaseUtils.getInstatnce.getHtable(tableName)
    for (els <- list) {
      table.incrementColumnValue(Bytes.toBytes(els.day_serach_category), Bytes.toBytes(cf),
        Bytes.toBytes(qualifer), els.clickCount)
    }
  }

  def count(day_category: String): Long = {
    val table = HBaseUtils.getInstatnce.getHtable(tableName)
    val get = new Get(Bytes.toBytes(day_category))
    val value = table.get(get).getValue(Bytes.toBytes(cf), Bytes.toBytes(qualifer))
    if (value == null) {
      0L
    } else {
      Bytes.toLong(value)
    }
  }

  def main(args: Array[String]): Unit = {
    //    val list=new ListBuffer[CategoryClickCount]
    //    list.append(CategoryClickCount("20171122_9",300))
    //    list.append(CategoryClickCount("20171122_8",100))
    //    list.append(CategoryClickCount("20171122_7",200))
    //    save(list)

    //    print(count("20171122_9")+"----------"+count("20171122_8"))
    print(count("20181031_1"))
  }
}
