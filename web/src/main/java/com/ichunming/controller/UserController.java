/**
 * HomeController
 * 2016/09/13 ming
 * v0.1
 */
package com.ichunming.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ichunming.model.UserInfo;
import com.ichunming.service.UserService;
import com.ichunming.vo.BaseResult;

@Controller
@ResponseBody
@RequestMapping("/v01/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "info", method = {RequestMethod.POST, RequestMethod.GET}, headers = "Accept=application/json;charset=UTF-8", produces = { "application/json;charset=UTF-8" })
	public BaseResult info() {
		// 用户信息取得
		logger.debug("用户信息取得");
		UserInfo userInfo = userService.getUserByUid(1L);
		BaseResult result = new BaseResult("0", userInfo);
		return result;
	}
}
