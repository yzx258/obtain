package com.rbgt.obtain.entity;

import java.util.List;

public class EventData<T> {

	private List<T> data;
	
	private String msg;
	
	private String note;

	public EventData() {
		super();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "EventData [data=" + data + ", msg=" + msg + ", note=" + note
				+ "]";
	}
}
