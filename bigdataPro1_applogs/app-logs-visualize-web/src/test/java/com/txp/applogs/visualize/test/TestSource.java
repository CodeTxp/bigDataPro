package com.txp.applogs.visualize.test;

import com.txp.applogs.visualize.domain.StatBean;
import com.txp.applogs.visualize.service.StatService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 */
public class TestSource {
	@Test
	public void testConn() throws SQLException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		DataSource ds = (DataSource) ac.getBean("dataSource");
		Connection conn = ds.getConnection() ;
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select formattime(getdaybegin(-1),'yyyy/MM/dd')");
		if(rs.next()){
			String time = rs.getString(1);
			System.out.println(time);
		}
		rs.close();
		conn.close();
	}

	@Test
	public void testService() throws SQLException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		StatService ss = (StatService) ac.getBean("statService");
        StatBean newUsers = ss.findNewUsers();
        System.out.println(newUsers.getCount());
    }

}
