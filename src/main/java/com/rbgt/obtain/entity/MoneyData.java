package com.rbgt.obtain.entity;

import java.util.List;

public class MoneyData<T> {

	private List<T> data;
	
	private String msg;
	
	private String allMoney;
	
	public MoneyData() {
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

	public String getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(String allMoney) {
		this.allMoney = allMoney;
	}

	@Override
	public String toString() {
		return "MoneyData [data=" + data + ", msg=" + msg + ", allMoney="
				+ allMoney + "]";
	}
}
