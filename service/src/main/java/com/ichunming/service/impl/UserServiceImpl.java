/**
 * User Service
 * 2016/09/13 ming
 * v0.1
 */
package com.ichunming.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ichunming.dao.UserInfoDao;
import com.ichunming.model.UserInfo;
import com.ichunming.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	/**
	 * 根据uid取得用户信息
	 * @param uid
	 * @return
	 */
	@Override
	public UserInfo getUserByUid(long uid) {
		logger.debug("用户信息取得:" + uid);
		return userInfoDao.select(uid);
	}
}
