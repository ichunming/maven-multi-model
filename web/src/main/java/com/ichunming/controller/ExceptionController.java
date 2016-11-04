package com.ichunming.controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ichunming.constant.ErrorCode;
import com.ichunming.core.exception.ApplicationException;
import com.ichunming.core.exception.InvalidSessionException;
import com.ichunming.vo.BaseResult;

@ControllerAdvice
public class ExceptionController {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},headers = "Accept=application/json;charset=UTF-8", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public BaseResult handleGeneralException(Exception ex) {
		BaseResult result = new BaseResult(ErrorCode.ERR_SYS_INTERNAL_ERROR);
		
		if(ex instanceof ApplicationException) {
			logger.error("Application Exception.");
			result.setCode(ErrorCode.ERR_SYS_INTERNAL_ERROR);
		} else if(ex instanceof IllegalArgumentException) {
			logger.error("Illegal Argument Exception.");
			result.setCode(ErrorCode.ERR_SYS_INTERNAL_ERROR);
		} else if(ex instanceof SQLException) {
			logger.error("Database Exception.");
			result.setCode(ErrorCode.ERR_SYS_DATABASE_ERROR);
		} else if(ex instanceof HttpRequestMethodNotSupportedException) {
			logger.error("Not Supported Http Request.");
			result.setCode(ErrorCode.ERR_SYS_METHOD_NOT_SUPPORT);
		} else if(ex instanceof InvalidSessionException) {
			logger.error("No Session.");
			result.setCode(ErrorCode.ERR_USER_NO_SESSION);
		} else {
			logger.error("Unknown Exception.");
		}
		
		return result;
	}
}
