package com.ichunming.core.exception;

public class ApplicationException extends BaseException{

	private static final long serialVersionUID = 1899456189500068689L;

	public ApplicationException(Throwable cause) {
		super(cause);
	}
	
	public ApplicationException(Throwable cause, String code) {
		super(cause, code);
	}
	
	public ApplicationException(String code, String message) {
		super(code, message);
	}

	public ApplicationException(Throwable cause, String code, String message) {
		super(cause, code, message);
	}
}
