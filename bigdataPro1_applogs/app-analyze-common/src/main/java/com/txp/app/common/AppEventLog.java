package com.txp.app.common;

import java.util.Map;

/**
 * 应用上报的事件相关信息
 *
 */
public class AppEventLog extends AppBaseLog {

	private static final long serialVersionUID = 1L;

	private String eventId;			//事件唯一标识
	private Long eventDurationSecs;	//事件持续时长
	private Map<String,String> paramKeyValueMap;		//参数名/值对

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Long getEventDurationSecs() {
		return eventDurationSecs;
	}

	public void setEventDurationSecs(Long eventDurationSecs) {
		this.eventDurationSecs = eventDurationSecs;
	}

	public Map<String, String> getParamKeyValueMap() {
		return paramKeyValueMap;
	}

	public void setParamKeyValueMap(Map<String, String> paramKeyValueMap) {
		this.paramKeyValueMap = paramKeyValueMap;
	}
}
