package com.ichunming.vo;

public class BaseResult {
	private String code;
	private Object data;
	
	public BaseResult() {}
	
	public BaseResult(String code) {
		this.code = code;
	}
	
	public BaseResult(String code, Object data) {
		this.code = code;
		this.data = data;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
