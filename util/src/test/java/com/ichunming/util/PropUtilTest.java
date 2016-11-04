package com.ichunming.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;
import org.junit.Test;

public class PropUtilTest {
	
	@Test
	public void readTest() {
		Map<String, String> info = null;
		try {
			info = PropUtil.read(this.getClass().getClassLoader().getResource("info.properties").getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(info.get("name"), "ming");
		assertEquals(info.get("password"), "1234");
	}
}
