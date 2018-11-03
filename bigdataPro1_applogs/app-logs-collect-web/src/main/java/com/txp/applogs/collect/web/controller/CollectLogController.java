package com.txp.applogs.collect.web.controller;

import com.alibaba.fastjson.JSONObject;

import com.txp.app.common.*;
import com.txp.app.util.GeoUtil;
import com.txp.app.util.PropertiesUtil;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 */
@Controller()
@RequestMapping("/coll")
public class CollectLogController {
	/**
	 * 地理信息缓存
	 */
	private Map<String,GeoInfo> cache = new HashMap<String, GeoInfo>();

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	@ResponseBody
	public AppLogEntity collect(@RequestBody AppLogEntity e, HttpServletRequest req) {

		//1.修正时间
		long myTime = System.currentTimeMillis() ;
		long clientTime = Long.parseLong(req.getHeader("clientTime"));
		long diff = myTime - clientTime ;
		verifyTime(e,diff) ;

		//2.基本属性复制
		copyBaseProperties(e);

		//3.处理ip地址问题
		String clientIp = req.getRemoteAddr();
		processIp(e , clientIp);

		//4.发送log给kafka主题
		sendMessage(e);
		return e;
	}

	/**
	 * 消息发送
	 */
	public void sendMessage(AppLogEntity e) {
		//创建配置对象
		Properties props = new Properties();
		props.put("metadata.broker.list", "s202:9092");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "1");

		//创建生产者
		Producer<Integer, String> producer = new Producer<Integer, String>(new ProducerConfig(props));
		sendSingleLog(producer,Constants.TOPIC_APP_STARTUP,e.getAppStartupLogs());
		sendSingleLog(producer,Constants.TOPIC_APP_ERRROR,e.getAppErrorLogs());
		sendSingleLog(producer,Constants.TOPIC_APP_EVENT,e.getAppEventLogs());
		sendSingleLog(producer,Constants.TOPIC_APP_PAGE,e.getAppPageLogs());
		sendSingleLog(producer,Constants.TOPIC_APP_USAGE,e.getAppUsageLogs());

		//发送消息
		producer.close();
	}

	/**
	 * 发送单个的log消息给kafka
	 */
	private void sendSingleLog(Producer<Integer, String> producer,String topic , AppBaseLog[] logs){
		for (AppBaseLog log : logs) {
			String logMsg = JSONObject.toJSONString(log);
			//创建消息
			KeyedMessage<Integer, String> data = new KeyedMessage<Integer, String>(topic, logMsg);
			producer.send(data);
		}
	}

	/**
	 * 处理ip client地址问题
	 */
	private void processIp(AppLogEntity e, String clientIp) {
		GeoInfo info = cache.get(clientIp);
		if(info == null){
			info = new GeoInfo();
			info.setCountry(GeoUtil.getCountry(clientIp));
			info.setProvince(GeoUtil.getProvince(clientIp));
			cache.put(clientIp,info) ;
		}
		for(AppStartupLog log : e.getAppStartupLogs()){
			log.setCountry(info.getCountry());
			log.setProvince(info.getProvince());
			log.setIpAddress(clientIp);
		}
	}

	/**
	 * 修正时间
	 */
	private void verifyTime(AppLogEntity e,long diff){
		//startuplog
		for(AppBaseLog log : e.getAppStartupLogs()){
			log.setCreatedAtMs(log.getCreatedAtMs() + diff );
		}
		for(AppBaseLog log : e.getAppUsageLogs()){
			log.setCreatedAtMs(log.getCreatedAtMs() + diff );
		}
		for(AppBaseLog log : e.getAppPageLogs()){
			log.setCreatedAtMs(log.getCreatedAtMs() + diff );
		}
		for(AppBaseLog log : e.getAppEventLogs()){
			log.setCreatedAtMs(log.getCreatedAtMs() + diff );
		}
		for(AppBaseLog log : e.getAppErrorLogs()){
			log.setCreatedAtMs(log.getCreatedAtMs() + diff );
		}
	}

	/**
	 * 复制基本属性
	 */
	private void copyBaseProperties(AppLogEntity e){
		PropertiesUtil.copyProperties(e, e.getAppStartupLogs());
		PropertiesUtil.copyProperties(e, e.getAppErrorLogs());
		PropertiesUtil.copyProperties(e, e.getAppEventLogs());
		PropertiesUtil.copyProperties(e, e.getAppPageLogs());
		PropertiesUtil.copyProperties(e, e.getAppUsageLogs());
	}
}