package com.txp.applogs.test;

import com.txp.applogs.udf.DateUtil;
import org.junit.Test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestHive {


    /**
     * 测试
     */
    @Test
    public void testFunction(){
        Date dayBeginTime = DateUtil.getDayBeginTime(new Date(),-1);
        Date WeekBeginTime = DateUtil.getWeekBeginTime(new Date(),1);
        Date MonthBeginTime = DateUtil.getMonthBeginTime(new Date(),1);
        System.out.println(MonthBeginTime);
    }

    /**
     * 计算某一天的起始时间
     * @throws ParseException
     */
    @Test
    public void testStartTime() throws ParseException {
        Date d=new Date();
        long ms = getZeroDate(d).getTime();
        System.out.println(ms);
    }


    /**
     * 计算某一天的结束时间
     * @throws ParseException
     */
    @Test
    public void testEndTime() throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d=new Date();
        Date zeroDate = getZeroDate(d);
        //日历
        Calendar c = Calendar.getInstance();
        c.setTime(zeroDate);
        c.add(Calendar.DAY_OF_MONTH ,1);
        Date endDate = c.getTime();
        long endMS = endDate.getTime();
        System.out.println(endMS);
    }

    /**
     * 周起始时间
     */
    @Test
    public  void testWeekStartTime(){
        Date d=new Date();
        Date zeroDate = getZeroDate(d);
        Calendar c = Calendar.getInstance();
        c.setTime(zeroDate);
        //今天是这一周的第几天
        int n = c.get(Calendar.DAY_OF_WEEK);
        //向前翻  n-1天   得到一周的起止时间    也就是周日的时刻
        c.add(Calendar.DAY_OF_WEEK,-(n-1));
        //周日c
        long  ms = c.getTimeInMillis();
        System.out.println(ms);
    }

    /**
     * 周结束时间
     */
    @Test
    public  void testWeekEndTime(){
        Date d=new Date();
        Calendar c = Calendar.getInstance();
        //设置日历时间
        c.setTime(d);
        int n = c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DAY_OF_WEEK,(8-n));
        //
//        Date weekFirstDate = c.getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
//        System.out.println(sdf.format(weekFirstDate));
        long ms = c.getTimeInMillis();
        System.out.println(ms);
    }


    /**
     * 月起始时间
     */
    @Test
    public  void testMonthStartTime() throws ParseException {
        Date d=new Date();
        Date zeroDate = getZeroDate(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/01 00:00:00");
        long ms = sdf.parse(sdf.format(zeroDate)).getTime();
        System.out.println(ms);
    }

    /**
     * 月结束时间
     */
    @Test
    public  void testMonthEndTime() throws ParseException {
        Date d=new Date();
        Date zeroDate = getZeroDate(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/01 00:00:00");
        Date firstDay = sdf.parse(sdf.format(zeroDate));
        Calendar c = Calendar.getInstance();
        //设置日历时间
        c.setTime(firstDay);
        c.add(Calendar.MONTH,1);
        long ms = c.getTime().getTime();
        System.out.println(ms);
    }
    /**
     * 得到指定的date的零时刻
     * @param date
     * @return
     */
    private Date getZeroDate(Date date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
