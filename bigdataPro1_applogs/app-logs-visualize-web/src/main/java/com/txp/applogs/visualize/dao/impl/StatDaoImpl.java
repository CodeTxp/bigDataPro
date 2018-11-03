package com.txp.applogs.visualize.dao.impl;

import com.txp.applogs.visualize.dao.BaseDao;
import com.txp.applogs.visualize.domain.StatBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 统计
 */
@Repository("statDao")
public class StatDaoImpl extends SqlSessionDaoSupport implements BaseDao {
    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
	/**
	 * 查询新增用户
	 */
	public StatBean findNewUsers(){
		return getSqlSession().selectOne("stats.newusers");
	}

    @Override
    public List<StatBean> findThisWeekNewUsers(String appid) {
        return getSqlSession().selectList("stats.selectThisWeekNewusers",appid);
    }
    /**
     * 查询3周的活跃用户数目
     */
    @Override
    public List<StatBean> findWeeksActivers(String appid) {
        return getSqlSession().selectList("stats.selectWeeksActivers",appid);
    }

    @Override
    public List findDaySilenters(String appid) {
        return  getSqlSession().selectOne("stats.selectDaySilenters",appid);
    }
}
