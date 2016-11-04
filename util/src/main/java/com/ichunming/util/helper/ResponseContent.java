package com.ichunming.util.helper;

public class ResponseContent {

	// 状态值
	private int statusCode;
	// 响应内容
	private String content;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
