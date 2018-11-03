package com.txp.applogs.visualize.service.impl;

import com.txp.applogs.visualize.dao.BaseDao;
import com.txp.applogs.visualize.domain.StatBean;
import com.txp.applogs.visualize.service.StatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 统计服务
 */
@Service("statService")
public class StatServiceImpl extends BaseServiceImpl<StatBean> implements StatService {
    @Resource(name="statDao")
    public void setDao(BaseDao<StatBean> dao) {
        super.setDao(dao);
    }
    /**
     * 查询新增用户
     */
    @Override
    public StatBean findNewUsers() {
        return getDao().findNewUsers();
    }

    //查询本周内天指定app新增用户
    @Override
    public List<StatBean> findThisWeekNewUsers(String appid) {
        return getDao().findThisWeekNewUsers(appid);
    }
    //当前周的新增用户数目的统计
    @Override
    public List<StatBean> findWeeksActivers(String appid) {
        return getDao().findWeeksActivers(appid);
    }

    @Override
    public List<StatBean> findDaySilenters(String appid) {
        return getDao().findDaySilenters(appid);
    }
}
