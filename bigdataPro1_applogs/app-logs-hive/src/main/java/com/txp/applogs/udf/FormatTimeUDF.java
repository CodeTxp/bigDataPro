package com.txp.applogs.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将long型的时间片格式化成指定日期格式
 */
@Description(name = "udf_formattime",
		value = "formattime",
		extended = "formattime() ;\r\n"
						   + " formattime(1234567,'yyyy/MM/01') \r\n"
						   + " formattime('1234567','yyyy/MM/dd')")
public class FormatTimeUDF extends UDF {

	/**
	 * 格式化时间,long型
	 */
	public String evaluate(long ms,String fmt) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt) ;
		Date d = new Date();
		d.setTime(ms);
		return sdf.format(d) ;
	}
	/**
	 * 格式化时间，string类型
	 */
	public String evaluate(String ms,String fmt) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt) ;
		Date d = new Date();
		d.setTime(Long.parseLong(ms));
		return sdf.format(d) ;
	}

	/**
	 * 格式化
	 */
	public String evaluate(long ms ,String fmt , int week) throws ParseException {
		Date d = new Date();
		d.setTime(ms);
		//周内第一天
		Date firstDay = DateUtil.getWeekBeginTime(d) ;
		SimpleDateFormat sdf = new SimpleDateFormat(fmt) ;
		return sdf.format(firstDay) ;
	}
}