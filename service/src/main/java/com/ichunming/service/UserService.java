/**
 * User Service Interface
 * 2016/09/13 ming
 * v0.1
 */
package com.ichunming.service;

import com.ichunming.model.UserInfo;

public interface UserService {
	
	/**
	 * 根据uid取得用户信息
	 * @param uid
	 * @return
	 */
	public UserInfo getUserByUid(long uid);
}
