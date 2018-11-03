package com.txp.app.flume.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.txp.app.common.AppBaseLog;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.apache.flume.interceptor.TimestampInterceptor.Constants.*;

/**
 * 自定义flume的拦截器,提取body中的createTimeMS字段作为header
 */
public class LogCollInterceptor implements Interceptor {

	private final boolean preserveExisting;

	private LogCollInterceptor(boolean preserveExisting) {
		this.preserveExisting = preserveExisting;
	}

	public void initialize() {
	}

	/**
	 * Modifies events in-place.
	 */
	public Event intercept(Event event) {
		Map<String, String> headers = event.getHeaders();
		//处理时间
		byte[] json = event.getBody();
		String jsonStr = new String(json);
		save(jsonStr);
		AppBaseLog log = JSONObject.parseObject(jsonStr , AppBaseLog.class);
		long time = log.getCreatedAtMs();
		headers.put(TIMESTAMP, Long.toString(time));
		save(time +"");

		//处理log类型的头
		//pageLog
		String logType = "" ;
		if(jsonStr.contains("pageId")){
			logType = "page" ;
		}
		//eventLog
		else if (jsonStr.contains("eventId")) {
			logType = "event";
		}
		//usageLog
		else if (jsonStr.contains("singleUseDurationSecs")) {
			logType = "usage";
		}
		//error
		else if (jsonStr.contains("errorBrief")) {
			logType = "error";
		}
		//startup
		else if (jsonStr.contains("network")) {
			logType = "startup";
		}
		headers.put("logType", logType);
		save(logType);
		return event;
	}

	/**
	 * Delegates to {@link #intercept(Event)} in a loop.
	 *
	 * @param events
	 * @return
	 */
	public List<Event> intercept(List<Event> events) {
		for (Event event : events) {
			intercept(event);
		}
		return events;
	}

	public void close() {
	}

	/**
	 */
	public static class Builder implements Interceptor.Builder {

		private boolean preserveExisting = PRESERVE_DFLT;

		public Interceptor build() {
			return new LogCollInterceptor(preserveExisting);
		}

		public void configure(Context context) {
			preserveExisting = context.getBoolean(PRESERVE, PRESERVE_DFLT);
		}
	}

	/**
	 *保存
	 */
	private void save(String log)  {
		try {
			FileWriter fw = new FileWriter("/home/txp/l.log",true);
			fw.append(log + "\r\n");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class Constants {
		public static String TIMESTAMP = "timestamp";
		public static String PRESERVE = "preserveExisting";
		public static boolean PRESERVE_DFLT = false;
	}

}
