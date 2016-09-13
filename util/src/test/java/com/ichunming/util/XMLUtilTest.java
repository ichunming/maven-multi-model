package com.ichunming.util;

import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import com.ichunming.util.entity.User;

public class XMLUtilTest {

	@Test
	public void readTest() {
		String path = this.getClass().getClassLoader().getResource("info.xml").getPath();
		List<User> users = XMLUtil.read(path, User.class);
		for(User user : users) {
			System.out.println(user.toString());
		}
		assertTrue(2 == users.size());
	}
	
	@Test
	public void writeTest() {
		String fold = this.getClass().getClassLoader().getResource("").getPath();
		List<User> users = Arrays.asList(new User("ming", "1234"), new User("ning", "1234"));
		System.out.println(fold);
		XMLUtil.write(users, fold + "out_user.xml", User.class);
	}
}
