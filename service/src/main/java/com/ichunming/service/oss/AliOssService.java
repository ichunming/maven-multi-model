/**
 * User Service
 * 2016/10/09 ming
 * v0.1
 */
package com.ichunming.service.oss;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ichunming.util.XMLUtil;

@Service
public class AliOssService {
	private Logger logger = LoggerFactory.getLogger(AliOssService.class);
	
	private Map<String, OSSClientWrapper> ossClientMap;
	
	@Autowired
	private AliOssConfiguration ossConfiguration;
	
	/**
	 * initial
	 * @return
	 */
	private OSSErrCode initOssClientMap() {
		ossClientMap = new HashMap<String, OSSClientWrapper>();
		
		logger.debug("init oss client...");
		
		// load bucket.xml
		logger.debug("load bucket.xml");
		List<Bucket> buckets = XMLUtil.read(this.getClass().getClassLoader().getResource("bucket.xml").getPath(), Bucket.class);
		if(null == buckets || buckets.size() < 1) {
			logger.error("read bucket from configure fail");
			return OSSErrCode.SYS_EX;
		}
		
		// create client
		for(Bucket bucket : buckets) {
			ossClientMap.put(bucket.getName(), new OSSClientWrapper(ossConfiguration, bucket));
		}
		return OSSErrCode.OK;
	}
	
	/**
	 * post
	 * @param bucketName
	 * @param key
	 * @param is
	 * @return
	 */
	public String post(String bucketName, String key, InputStream is) {
		// get client
		OSSClientWrapper ossClientWrapper = getClient(bucketName);
		if(null == ossClientWrapper) {
			logger.error("bucket[" + bucketName + "] not exists");
			return null;
		}
		OSSErrCode code = ossClientWrapper.post(key, is);
		if(OSSErrCode.OK.getCode() == code.getCode()) {
			// return resource path
			return ossClientWrapper.getBucket().getUrl() + "/" + key;
		}
		
		return null;
	}
	
	/**
	 * post
	 * @param bucketName
	 * @param key
	 * @param filePath
	 * @return
	 */
	public String post(String bucketName, String key, String filePath) {
		// get client
		OSSClientWrapper ossClientWrapper = getClient(bucketName);
		if(null == ossClientWrapper) {
			logger.error("bucket[" + bucketName + "] not exists");
			return null;
		}
		OSSErrCode code = ossClientWrapper.post(key, filePath);
		if(OSSErrCode.OK.getCode() == code.getCode()) {
			// return resource path
			return ossClientWrapper.getBucket().getUrl() + "/" + key;
		}
		
		return null;
	}
	
	/**
	 * delete
	 * @param bucketName
	 * @param key
	 * @return
	 */
	public OSSErrCode delete(String bucketName, String key) {
		// get client
		OSSClientWrapper ossClientWrapper = getClient(bucketName);
		if(null == ossClientWrapper) {
			logger.error("bucket[" + bucketName + "] not exists");
			return OSSErrCode.SYS_EX;
		}
		return ossClientWrapper.delete(key);
	}
	
	/**
	 * get client
	 * @param bucket
	 * @return
	 */
	private OSSClientWrapper getClient(String bucket) {
		// check ossClientMap
		if(null == ossClientMap || ossClientMap.isEmpty()) {
			if(OSSErrCode.OK.getCode() != initOssClientMap().getCode()) {
				return null;
			}
		}
		// check ossClientWrapper
		return ossClientMap.get(bucket);
	}
	
}
