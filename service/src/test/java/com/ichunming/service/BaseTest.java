package com.ichunming.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class BaseTest {
	// 所有方法执行一次
	@BeforeClass
	public static void beforeClass() {
	}

	// 所有方法执行一次
	@AfterClass
	public static void afterClass() {
	}

	// 每个测试方法执行之前都要执行一次。
	@Before
	public void before() {
	}

	// 每个测试方法执行之后要执行一次。
	@After
	public void after() {
	}
	
	@Test
	public void run() {
	}
}
