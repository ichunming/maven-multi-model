package com.ichunming.service.oss;

public class OSSClientWrapperDeleteTest extends OSSByMainTest {
	public static void main(String[] args) {
		OSSClientWrapper target = new OSSClientWrapper(getAliOssConfiguration(), getBucket());
		OSSErrCode code = target.delete("key-hello-world");
		if(code.getCode() == OSSErrCode.OK.getCode()) {
			System.out.println("SUCCESS");
		} else if(code.getCode() == OSSErrCode.RES_NOT_EXIST.getCode()) {
			System.out.println("RESOURCE NOT EXIST");
		} else {
			System.out.println("FAIL");
		}
	}
}
