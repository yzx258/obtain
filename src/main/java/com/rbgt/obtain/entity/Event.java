package com.rbgt.obtain.entity;

/**
 * @author Administrator
 */
public class Event {

	private String eventName;
	
	private String eventQuiz;
	
	private String eventResults;

	public Event() {
		super();
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventQuiz() {
		return eventQuiz;
	}

	public void setEventQuiz(String eventQuiz) {
		this.eventQuiz = eventQuiz;
	}

	public String getEventResults() {
		return eventResults;
	}

	public void setEventResults(String eventResults) {
		this.eventResults = eventResults;
	}

	@Override
	public String toString() {
		return "Event [eventName=" + eventName + ", eventQuiz=" + eventQuiz
				+ ", eventResults=" + eventResults + "]";
	}
}
