package com.ichunming.constant;

public class ErrorCode {

	public static final Long ERRCODE_SUCCESS = 0L;
	
	// 系统级错误码
	public static final Long ERR_SYS_INTERNAL_ERROR = 1000L;
	public static final Long ERR_SYS_SERVER_BUSY = 1001L;
	public static final Long ERR_SYS_DATABASE_ERROR = 1002L;
	public static final Long ERR_SYS_NO_RESOURCE_FOUND = 1003L;
	public static final Long ERR_SYS_METHOD_NOT_SUPPORT= 1004L;
	public static final Long ERR_SYS_BAD_REQUEST = 1005L;
	
	// 用户级错误码
	public static final Long ERR_USER_NO_SESSION = 2001L;
	
	// 服务级错误码
	public static final Long ERR_SVR_SEND_MAIL_FAIL = 3001L;
}
