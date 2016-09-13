package com.ichunming.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.ichunming.model.UserInfo;

public class UserInfoDaoTest extends BaseTest {
	
	@Test
	public void userInfoDaoTest() throws Exception {
		// get UserInfoDao
		UserInfoDao userInfoDao = sqlSession.getMapper(UserInfoDao.class);
		UserInfo userInfo = userInfoDao.select(1L);
		
		// verify result
		assertNotNull(userInfo);
		assertEquals("Ming", userInfo.getUsername());
	}
}
