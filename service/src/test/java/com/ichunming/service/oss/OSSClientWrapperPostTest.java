package com.ichunming.service.oss;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class OSSClientWrapperPostTest extends OSSByMainTest {
	public static void main(String[] args) {
		OSSClientWrapper target = new OSSClientWrapper(getAliOssConfiguration(), getBucket());
		InputStream is = new ByteArrayInputStream("hello world".getBytes());
		OSSErrCode code = target.post("key-hello-world", is);
		if(code.getCode() == OSSErrCode.OK.getCode()) {
			System.out.println("SUCCESS");
		} else if(code.getCode() == OSSErrCode.BT_NOT_EXIST.getCode()) {
			System.out.println("BUCKET NOT EXIST");
		} else {
			System.out.println("FAIL");
		}
	}
}
