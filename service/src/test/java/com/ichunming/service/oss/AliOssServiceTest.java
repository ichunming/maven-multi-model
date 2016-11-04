package com.ichunming.service.oss;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.ichunming.service.BaseTest;

public class AliOssServiceTest extends BaseTest{
	@InjectMocks
	private AliOssService target;
	@Mock
	private Map<String, OSSClientWrapper> ossClientMap;
	@Mock
	private OSSClientWrapper ossClientWrapper;
	
	private static Bucket bucket;
	
	// 所有方法执行一次
	@BeforeClass
	public static void beforeClass() {
		bucket = new Bucket();
		bucket.setName("test-bucket");
		bucket.setUrl("test-bucket-url");
	}
	
	@Test
	public void postTest() {
		// mock data
		// ossClientWrapper
		InputStream is = new ByteArrayInputStream("".getBytes());
		when(ossClientWrapper.post("TEMP_POST_KEY", is)).thenReturn(OSSErrCode.OK);
		when(ossClientWrapper.getBucket()).thenReturn(bucket);
		when(ossClientMap.get(Mockito.anyString())).thenReturn(ossClientWrapper);
		
		String res = target.post(bucket.getName(), "TEMP_POST_KEY", is);
		
		assertNotNull(res);
		assertEquals(res, bucket.getUrl() + "/" + "TEMP_POST_KEY");
	}
	
	@Test
	public void postFileTest() {
		// mock data
		// ossClientWrapper
		when(ossClientWrapper.post(Mockito.anyString(), Mockito.anyString())).thenReturn(OSSErrCode.OK);
		when(ossClientWrapper.getBucket()).thenReturn(bucket);
		when(ossClientMap.get(Mockito.anyString())).thenReturn(ossClientWrapper);
		
		String res = target.post(bucket.getName(), "TEMP_POST_KEY", "");
		
		assertNotNull(res);
		assertEquals(res, bucket.getUrl() + "/" + "TEMP_POST_KEY");
	}
	
	@Test
	public void deleteTest() {
		when(ossClientWrapper.delete(Mockito.anyString())).thenReturn(OSSErrCode.OK);
		when(ossClientMap.get(Mockito.anyString())).thenReturn(ossClientWrapper);
		OSSErrCode code = target.delete(bucket.getName(), "TEMP_DELETE_KEY");
		assertNotNull(code);
		assertEquals(code.getCode(), OSSErrCode.OK.getCode());
	}
}
