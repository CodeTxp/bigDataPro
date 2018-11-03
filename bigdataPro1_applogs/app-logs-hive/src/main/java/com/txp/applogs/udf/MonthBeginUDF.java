package com.txp.applogs.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 计算day所在月起始毫秒数
 */
@Description(name = "udf_getmonthbegin",
		value = "getmonthbegin",
		extended = "getmonthbegin() ;\r\n" +
						   " getmonthbegin(2) \r\n" +
						   " getmonthbegin('2017/06/29 01:02:03') \r\n" +
						   " getmonthbegin('2017/06/29 01:02:03',2) \r\n" +
						   " getmonthbegin(date_obj) \r\n" +
						   " getmonthbegin(date_obj,2)")
@UDFType(deterministic = true, stateful = false)
public class MonthBeginUDF extends UDF {

	/**
	 * 计算现在的起始时刻(毫秒数)
	 */
	public long evaluate() throws ParseException {
		return DateUtil.getMonthBeginTime(new Date()).getTime() ;
	}

	/**
	 * 指定周偏移量
	 */
	public long evaluate(int offset) throws ParseException {
		return DateUtil.getMonthBeginTime(new Date(),offset).getTime();
	}

	/**
	 *
	 */
	public long evaluate(Date d) throws ParseException {
		return DateUtil.getMonthBeginTime(d).getTime();
	}

	/**
	 */
	public long evaluate(Date d,int offset) throws ParseException {
		return DateUtil.getMonthBeginTime(d,offset).getTime();
	}

	/**
	 */
	public long evaluate(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d = sdf.parse(dateStr);
		return DateUtil.getMonthBeginTime(d).getTime();
	}
	/**
	 */
	public long evaluate(String dateStr,int offset) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d = sdf.parse(dateStr);
		return DateUtil.getMonthBeginTime(d, offset).getTime();
	}

	/**
	 */
	public long evaluate(String dateStr, String fmt) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		Date d = sdf.parse(dateStr);
		return DateUtil.getMonthBeginTime(d).getTime();
	}

	/**
	 */
	public long evaluate(String dateStr, String fmt,int offset) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		Date d = sdf.parse(dateStr);
		return DateUtil.getMonthBeginTime(d, offset).getTime();
	}
}