package com.ichunming.service.oss;

public class OSSByMainTest {
	private static AliOssConfiguration ossConfiguration;
	
	protected static AliOssConfiguration getAliOssConfiguration() {
		if(null == ossConfiguration) {
			ossConfiguration = new AliOssConfiguration();
			ossConfiguration.setEndpoint("http://oss-cn-shanghai.aliyuncs.com");
			ossConfiguration.setAccessKeyId("LTAInvxxhFI3bxMn");
			ossConfiguration.setAccessKeySecret("7aE72qM1SKEeGq1G7aIa0QeKbauYgH");
		}
		return ossConfiguration;
	}
	
	protected static Bucket getBucket() {
		Bucket bucket = new Bucket();
		bucket.setName("junit-test-bucket");
		bucket.setUrl("junit-test-bucket.oss-cn-shanghai.aliyuncs.com");
		return bucket;
	}
}
