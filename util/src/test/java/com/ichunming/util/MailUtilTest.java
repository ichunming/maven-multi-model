package com.ichunming.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class MailUtilTest {
	@Test
	public void test() {
		
		boolean result = true;
		
		// send simple email
		/*String host = "***";
		String username = "***@**.com";
		String password = "***";
		String fromEmail = "***@**.com";
		MailUtil mailUtil = new MailUtil(host, username, password, fromEmail);
		
		result = mailUtil.send("主题", "内容", "***@**.com");*/
		
		// send email with attachment
		/*List<Map<String, String>> attachs = new ArrayList<Map<String, String>>();
		Map<String, String> attach = new HashMap<String, String>();
		attach.put("name", "test.txt");
		attach.put("path", "test.txt");
		attachs.add(attach);
		result = mailUtil.send("主题", "内容", "***@qq.com", attachs);*/
		assertTrue(result);
	}
}
