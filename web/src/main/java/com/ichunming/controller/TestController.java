/**
 * HomeController
 * 2016/09/13 ming
 * v0.1
 */
package com.ichunming.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ichunming.constant.ErrorCode;
import com.ichunming.model.UserInfo;
import com.ichunming.service.MailService;
import com.ichunming.service.UserService;
import com.ichunming.vo.BaseResult;

@Controller
@ResponseBody
@RequestMapping("/v1/test")
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private MailService mailService;
	
	@RequestMapping(value = "user/info", method = {RequestMethod.POST, RequestMethod.GET}, headers = "Accept=application/json;charset=UTF-8", produces = { "application/json;charset=UTF-8" })
	public BaseResult info() {
		// 用户信息取得
		logger.debug("用户信息取得");
		UserInfo userInfo = userService.getUserByUid(1L);
		BaseResult result = new BaseResult(0L, userInfo);
		return result;
	}
	
	@RequestMapping(value = "mail/send", method = {RequestMethod.POST, RequestMethod.GET}, headers = "Accept=application/json;charset=UTF-8", produces = { "application/json;charset=UTF-8" })
	public BaseResult send() {
		// 用户信息取得
		logger.debug("发送邮件");
		boolean res = mailService.send("test", "this is a test mail", "407201244@qq.com");
		BaseResult result;
		if(res) {
			result = new BaseResult();
		} else {
			result = new BaseResult(ErrorCode.ERR_SVR_SEND_MAIL_FAIL);
		}
		
		return result;
	}
	
	@RequestMapping(value = "user/login", method = {RequestMethod.POST, RequestMethod.GET}, headers = "Accept=application/json;charset=UTF-8", produces = { "application/json;charset=UTF-8" })
	public BaseResult login(HttpServletRequest request) {
		// 用户登入
		logger.debug("用户登入");
		logger.debug("debug log...");
		
		request.getSession(true).setAttribute("name", "ming");
		
		return new BaseResult();
	}
}
