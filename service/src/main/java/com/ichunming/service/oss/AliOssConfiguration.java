package com.ichunming.service.oss;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AliOssConfiguration {
	@Value("#{configProperties['oss.endpoint']}")
	private String endpoint;
	@Value("#{configProperties['oss.accessKeyId']}")
	private String accessKeyId;
	@Value("#{configProperties['oss.accessKeySecret']}")
	private String accessKeySecret;
	
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
}
