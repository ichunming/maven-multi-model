package com.ichunming.util;

import static org.junit.Assert.assertEquals;
import java.util.Map;
import org.junit.Test;

public class PropUtilTest {
	
	@Test
	public void readTest() {
		Map<String, String> info = PropUtil.read(this.getClass().getClassLoader().getResource("info.properties").getPath());
		assertEquals(info.get("name"), "ming");
		assertEquals(info.get("password"), "1234");
	}
}
