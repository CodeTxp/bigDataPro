package com.txp.applogs.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 计算day所在周起始毫秒数
 */
@Description(name = "udf_getweekbegin",
		value = "getweekbegin",
		extended = "getweekbegin() ;\r\n"
						   + " getweekbegin(2) \r\n"
						   + " getweekbegin('2018/10/29 01:02:03') \r\n"
						   + " getweekbegin('2018/10/29 01:02:03',2) \r\n"
						   + " getweekbegin(date_obj) \r\n"
						   + " getweekbegin(date_obj,2)")
public class WeekBeginUDF extends UDF {

	/**
	 * 计算现在的起始时刻(毫秒数)
	 */
	public long evaluate() throws ParseException {
		return DateUtil.getWeekBeginTime(new Date()).getTime() ;
	}

	/**
	 * 指定周偏移量
	 */
	public long evaluate(int offset) throws ParseException {
		return DateUtil.getWeekBeginTime(new Date(),offset).getTime();
	}

	/**
	 *
	 */
	public long evaluate(Date d) throws ParseException {
		return DateUtil.getWeekBeginTime(d).getTime();
	}

	/**
	 */
	public long evaluate(Date d,int offset) throws ParseException {
		return DateUtil.getWeekBeginTime(d,offset).getTime();
	}

	/**
	 */
	public long evaluate(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d = sdf.parse(dateStr);
		return DateUtil.getWeekBeginTime(d).getTime();
	}
	/**
	 */
	public long evaluate(String dateStr,int offset) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d = sdf.parse(dateStr);
		return DateUtil.getWeekBeginTime(d, offset).getTime();
	}

	/**
	 */
	public long evaluate(String dateStr, String fmt) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		Date d = sdf.parse(dateStr);
		return DateUtil.getWeekBeginTime(d).getTime();
	}

	/**
	 */
	public long evaluate(String dateStr, String fmt,int offset) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		Date d = sdf.parse(dateStr);
		return DateUtil.getWeekBeginTime(d, offset).getTime();
	}
}