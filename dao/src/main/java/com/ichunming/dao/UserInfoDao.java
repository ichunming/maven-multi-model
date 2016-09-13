/**
 * UserInfoDao
 * 2016/09/13 ming
 * v0.1
 */
package com.ichunming.dao;

import com.ichunming.model.UserInfo;

public interface UserInfoDao {
	/**
	 * 查找用户信息
	 * @param uid
	 * @return
	 */
	public UserInfo select(Long uid);

	/**
	 * 插入用户信息
	 * @param userInfo
	 * @return
	 */
	public void insert(UserInfo userInfo);

	/**
	 * 更新用户信息
	 * @param userInfo
	 * @return
	 */
	public void update(UserInfo userInfo);

	/**
	 * 删除用户信息
	 * @param uid
	 * @return
	 */
	public void delete(Integer uid);
	
	/**
	 * 通过用户名查找用户信息
	 * @param username
	 * @return
	 */
	public UserInfo selectByUsername(String username);
}