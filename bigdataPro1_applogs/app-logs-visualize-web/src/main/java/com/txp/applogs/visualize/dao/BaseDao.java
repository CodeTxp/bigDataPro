package com.txp.applogs.visualize.dao;

import com.txp.applogs.visualize.domain.StatBean;

import java.util.List;

public interface BaseDao<T> {
    public StatBean findNewUsers();
    //每周新增用户的统计
    public List<StatBean> findThisWeekNewUsers(String appid);
    //当前周的新增用户数目的统计
    public List<StatBean> findWeeksActivers(String appid);

    public List<T> findDaySilenters(String appid);
}