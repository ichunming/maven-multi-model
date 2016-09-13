package com.ichunming.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ichunming.dao.UserInfoDao;
import com.ichunming.model.UserInfo;
import com.ichunming.service.impl.UserServiceImpl;

@RunWith(PowerMockRunner.class)
public class UserServiceImplTest extends BaseTest{
	@InjectMocks
	private UserServiceImpl target;
	@Mock
	private UserInfoDao userInfoDao;
	
	@Test
	public void getMailUserCountTest() {
		// mock data
		// userInfoDao
		UserInfo mockUserInfo = new UserInfo();
		mockUserInfo.setUsername("Ming");
		when(userInfoDao.select(Mockito.anyLong())).thenReturn(mockUserInfo);
		
		// test method
		UserInfo result = target.getUserByUid(1L);
		
		// verify result
		assertNotNull(result);
		assertEquals("Ming", result.getUsername());
	}
}
