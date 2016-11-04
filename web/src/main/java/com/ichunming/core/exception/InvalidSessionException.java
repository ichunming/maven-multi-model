package com.ichunming.core.exception;

public class InvalidSessionException extends BaseException{
	
	private static final long serialVersionUID = 6365716125040387849L;

	public InvalidSessionException(Throwable cause) {
		super(cause);
	}

	public InvalidSessionException(Throwable cause, String code) {
		super(cause, code);
	}
	
	public InvalidSessionException(String code, String message) {
		super(code, message);
	}

	public InvalidSessionException(Throwable cause, String code, String message) {
		super(cause, code, message);
	}
}
