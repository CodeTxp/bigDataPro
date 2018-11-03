package com.txp.applogs.visualize.service;

import com.txp.applogs.visualize.domain.StatBean;

import java.util.List;

public interface StatService extends BaseService<StatBean>{
    public StatBean findNewUsers();
    //每周新增用户的统计
    public List<StatBean> findThisWeekNewUsers(String appid);
    //当前周活跃用户数目的统计
    public List<StatBean> findWeeksActivers(String appid);
    //当前天的沉默用户数
    public List<StatBean> findDaySilenters(String appid);
}
