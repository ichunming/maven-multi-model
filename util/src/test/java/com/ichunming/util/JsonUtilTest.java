package com.ichunming.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import com.ichunming.util.entity.TestBaseResult;
import com.ichunming.util.entity.User;

public class JsonUtilTest {
	
	@Test
	public void toJson() {
		User user = new User("User1", "p1");
		String json = JsonUtil.toJson(user);
		System.out.println(json);
		
		User obj = JsonUtil.fromJson(json, User.class);
		assertEquals(obj.getName(), user.getName());
	}
	
	@Test
	public void fromJsonTest() {
		List<User> users = Arrays.asList(new User("User1", "p1"), new User("User2", "p2"));
		TestBaseResult ret = new TestBaseResult();
		ret.setCode(0L);
		ret.setData(users);
		String json = JsonUtil.toJson(ret);
		System.out.println(json);
		
		List<User> objs = JsonUtil.fromJson(json, "data", User.class);
		assertEquals(objs.size(), users.size());
	}
	
	@Test
	public void fromJsonExTest() {
		User obj = JsonUtil.fromJson("not json data", User.class);
		List<User> objs = JsonUtil.fromJson("{\"code\":0,\"dataEx\":[{\"name\":\"User1\",\"password\":\"p1\"},{\"name\":\"User2\",\"password\":\"p2\"}]}",
							"data", User.class);
		assertNull(obj);
		assertNull(objs);
	}
}