package com.ichunming.service.oss;

public class OSSClientWrapperGetSignedUrlTest extends OSSByMainTest {
	public static void main(String[] args) {
		OSSClientWrapper target = new OSSClientWrapper(getAliOssConfiguration(), getBucket());
		String url = target.getSignedUrl("key-hello-world");
		System.out.println(url);
	}
}
