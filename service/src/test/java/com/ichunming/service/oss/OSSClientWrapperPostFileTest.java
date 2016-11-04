package com.ichunming.service.oss;

public class OSSClientWrapperPostFileTest extends OSSByMainTest {
	public static void main(String[] args) {
		OSSClientWrapper target = new OSSClientWrapper(getAliOssConfiguration(), getBucket());
		String filePath = "D://temp//test.txt";
		OSSErrCode code = target.post("key-test-file", filePath);
		if(code.getCode() == OSSErrCode.OK.getCode()) {
			System.out.println("SUCCESS");
		} else if(code.getCode() == OSSErrCode.BT_NOT_EXIST.getCode()) {
			System.out.println("BUCKET NOT EXIST");
		} else {
			System.out.println("FAIL");
		}
	}
}
