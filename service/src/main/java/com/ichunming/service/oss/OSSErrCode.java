package com.ichunming.service.oss;

public enum OSSErrCode {
	OK(0, "success"),
	BT_NOT_EXIST(10, "bucket not exist"),
	BT_EXIST(11, "bucket already exist"),
	RES_NOT_EXIST(20, "resource not exist"),
	OSS_EX(40, "oss exception"),
	CLT_EX(41, "client exception"),
	SYS_EX(99, "system exception");
	
	private int code;
	private String desc;
	
	public int getCode() {
		return code;
	}
	
	private OSSErrCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc;
	}
}
