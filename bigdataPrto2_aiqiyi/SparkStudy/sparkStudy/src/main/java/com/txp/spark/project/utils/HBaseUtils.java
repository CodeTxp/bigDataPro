package com.txp.spark.project.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;


/**
 * 操作Hbase
 */
public class HBaseUtils {

    private HBaseAdmin hBaseAdmin = null;
    private Configuration conf = null;

    /**
     * 私有构造方法
     */
    private HBaseUtils() {
        conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "s202:2181");
        conf.set("hbase.rootdir", "hdfs://s201/user/hbase");
        try {
            hBaseAdmin = new HBaseAdmin(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static HBaseUtils instatnce = null;//单实例

    public static synchronized HBaseUtils getInstatnce() {
        if (null == instatnce)
            instatnce = new HBaseUtils();

        return instatnce;
    }

    /**
     * 根据表名称获取HTable实例
     */
    public HTable getHtable(String tableName) {
        HTable table = null;
        try {
            table = new HTable(conf, tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * 添加数据到hbase中
     *
     * @param tableName 表名
     * @param rowKey    对应的key值
     * @param cf        列族
     * @param colum     列簇里面的对应的列
     * @param value     hbase中对应的值
     */
    public void put(String tableName, String rowKey, String cf, String colum, String value) {
        HTable htable = getHtable(tableName);
        Put put = new Put(Bytes.toBytes(rowKey));
        put.add(Bytes.toBytes(cf), Bytes.toBytes(colum), Bytes.toBytes(value));
        try {
            htable.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String tableName = "category_click";
        String rowKey = "20181030_1";
        String cf = "info";
        String colum = "click_count";
        String value = "100";
        HBaseUtils.getInstatnce().put(tableName, rowKey, cf, colum, value);
    }
}
