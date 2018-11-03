package com.txp.applogs.visualize.domain;

import java.io.Serializable;

/**
 * 统计信息
 */
public class StatBean implements Serializable {
    //统计日期
    private String date ;
    //统计数量
    private long count ;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
