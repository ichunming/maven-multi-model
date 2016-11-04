package com.ichunming.core.exception;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 7480148024830731339L;

	private String code;
	
	private String errMsg;  
	
    public BaseException(Throwable cause) {
		super(cause);
    }
    
    public BaseException(Throwable cause, String code) {
		super(cause);
    	this.code = code;
	}
    
    public BaseException(String code, String errMsg) {
		this.code = code;
		this.errMsg = errMsg;
	}
    
    public BaseException(Throwable cause,String code, String errMsg) {
		this(cause);
		this.code = code;
		this.errMsg = errMsg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
