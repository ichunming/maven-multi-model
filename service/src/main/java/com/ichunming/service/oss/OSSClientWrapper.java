/**
 * User Service
 * 2016/10/09 ming
 * v0.1
 */
package com.ichunming.service.oss;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

public class OSSClientWrapper {
	private static final Logger logger = LoggerFactory.getLogger(OSSClientWrapper.class);
	
	private AliOssConfiguration ossConfiguration;
	
	private Bucket bucket;
	
	private OSSClient ossClient;

	public OSSClientWrapper() {}
	
	public OSSClientWrapper(AliOssConfiguration ossConfiguration, Bucket bucket) {
		this.ossConfiguration = ossConfiguration;
		this.bucket = bucket;
		this.createBucket(bucket.getName());
	}
	
	/**
	 * 上传资源
	 * @param key
	 * @param is
	 */
	public OSSErrCode post(String key, InputStream is) {
		try {
			open();
			// check bucket
			logger.debug("check bucket exist or not...");
			if(!this.ossClient.doesBucketExist(this.bucket.getName())) {
				logger.debug("bucket[" + this.bucket.getName() + "] does not exist!");
				return OSSErrCode.BT_NOT_EXIST;
			}
			logger.debug("bucket[" + this.bucket.getName() + "] exist!");
			logger.debug("post resource to AliOss...");
			// put object
			this.ossClient.putObject(this.bucket.getName(), key, is);
			logger.debug("post resource to AliOss success.");
		} catch (OSSException oe) {
			logger.error("fail to post resource to AliOss(OSSException)." + "Message:" + oe.getErrorMessage());
			return OSSErrCode.OSS_EX;
        } catch (ClientException ce) {
        	logger.error("fail to post resource to AliOss(ClientException)." + "Message:" + ce.getErrorMessage());
        	return OSSErrCode.OSS_EX;
        } catch (Exception e) {
        	logger.error("fail to post resource to AliOss(Exception)." + "Message:" + e.getMessage());
        	return OSSErrCode.SYS_EX;
        } finally {
        	close();
        }
		return OSSErrCode.OK;
	}

	/**
	 * 上传资源(文件)
	 * @param key
	 * @param filePath
	 */
	public OSSErrCode post(String key, String filePath) {
		try {
			open();
			// check bucket
			logger.debug("check bucket exist or not...");
			if(!this.ossClient.doesBucketExist(this.bucket.getName())) {
				logger.debug("bucket[" + this.bucket.getName() + "] does not exist!");
				return OSSErrCode.BT_NOT_EXIST;
			}
			logger.debug("bucket[" + this.bucket.getName() + "] exist!");
			logger.debug("post file to AliOss...");
			// put object
			this.ossClient.putObject(this.bucket.getName(), key, new File(filePath));
			logger.debug("post file to AliOss success.");
		} catch (OSSException oe) {
			logger.error("fail to post file to AliOss(OSSException)." + "Message:" + oe.getErrorMessage());
			return OSSErrCode.OSS_EX;
        } catch (ClientException ce) {
        	logger.error("fail to post file to AliOss(ClientException)." + "Message:" + ce.getErrorMessage());
        	return OSSErrCode.OSS_EX;
        } catch (Exception e) {
        	logger.error("fail to post file to AliOss(Exception)." + "Message:" + e.getMessage());
        	return OSSErrCode.SYS_EX;
        } finally {
        	close();
        }
		return OSSErrCode.OK;
	}
	
	/**
	 * 取得资源
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		OSSObject ossObject = null;
		String result = null;
		try {
			open();
			// check resource
			logger.debug("check resource exist or not...");
			if(!this.ossClient.doesObjectExist(this.bucket.getName(), key)) {
				logger.debug("resource [" + this.bucket.getName() + "," + key + "] does not exist");
				return null;
			}
			logger.debug("resource [" + this.bucket.getName() + "," + key + "] exist");
			logger.debug("get resource from AliOss...");
			// get resource
			ossObject = this.ossClient.getObject(this.bucket.getName(), key);
			if(null == ossObject) {
				logger.debug("get resource from AliOss fail");
				return null;
			} else {
				InputStream inputStream = ossObject.getObjectContent();
				StringBuilder objectContent = new StringBuilder();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				String line;
				while(null != (line = reader.readLine())) {
					objectContent.append(line);
				}
				inputStream.close();
				result = objectContent.toString();
			}
			logger.debug("get resource from AliOss success.");
		} catch (OSSException oe) {
			logger.error("fail to get resource to AliOss(OSSException)." + "Message:" + oe.getErrorMessage());
        } catch (ClientException ce) {
        	logger.error("fail to get resource to AliOss(ClientException)." + "Message:" + ce.getErrorMessage());
        } catch (Exception e) {
        	logger.error("fail to get resource to AliOss(Exception)." + "Message:" + e.getMessage());
        } finally {
        	close();
        }
		return result;
	}
	
	/**
	 * 删除资源
	 * @param key
	 */
	public OSSErrCode delete(String key) {
		try{
			open();
			// check resource
			logger.debug("check resource exist or not...");
			if(!this.ossClient.doesObjectExist(this.bucket.getName(), key)) {
				logger.debug("resource [" + this.bucket.getName() + "," + key + "] does not exist");
				return OSSErrCode.RES_NOT_EXIST;
			}
			logger.debug("bucket[" + this.bucket.getName() + "] exist!");
			logger.debug("delete resource from AliOss...");
			// delete resource
			this.ossClient.deleteObject(this.bucket.getName(), key);
			logger.debug("delete resource from AliOss success");
		} catch (OSSException oe) {
			logger.error("fail to delete resource to AliOss(OSSException)." + "Message:" + oe.getErrorMessage());
			return OSSErrCode.OSS_EX;
        } catch (ClientException ce) {
        	logger.error("fail to delete resource to AliOss(ClientException)." + "Message:" + ce.getErrorMessage());
        	return OSSErrCode.CLT_EX;
        } catch (Exception e) {
        	logger.error("fail to delete resource to AliOss(Exception)." + "Message:" + e.getMessage());
        	return OSSErrCode.SYS_EX;
        } finally {
        	close();
        }
		return OSSErrCode.OK;
	}
	
	/**
	 * 新建bucket
	 * @param bucketName
	 */
	private OSSErrCode createBucket(String bucketName) {
		open();
		try{
			// check bucket
			logger.debug("check bucket exist or not...");
			if(!this.ossClient.doesBucketExist(bucketName)) {
				logger.debug("bucket:[" + bucketName + "] does not exist");
				// create bucket
				logger.debug("create public read bucket:[" + bucketName + "]...");
				CreateBucketRequest createBucketRequest= new CreateBucketRequest(bucketName);
				// 设置bucket权限: 公共读
				createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
				ossClient.createBucket(createBucketRequest);
				this.ossClient.createBucket(bucketName);
				logger.debug("create bucket:[" + bucketName + "]success.");
				return OSSErrCode.OK;
			} else {
				logger.debug("bucket:[" + bucketName + "] already exist.");
				return OSSErrCode.BT_EXIST;
			}
		} catch (OSSException oe) {
			logger.error("fail to create bucket on AliOss(OSSException)." + "Message:" + oe.getErrorMessage());
			return OSSErrCode.OSS_EX;
        } catch (ClientException ce) {
        	logger.error("fail to create bucket on AliOss(ClientException)." + "Message:" + ce.getErrorMessage());
        	return OSSErrCode.CLT_EX;
        } catch (Exception e) {
        	logger.error("fail to create bucket on AliOss(Exception)." + "Message:" + e.getMessage());
        	return OSSErrCode.SYS_EX;
        } finally {
        	close();
        }
	}
	
	/**
	 * 删除bucket
	 * @param bucketName
	 */
	@SuppressWarnings("unused")
	private OSSErrCode deleteBucket() {
		try{
			open();
			// check bucket
			if(this.ossClient.doesBucketExist(this.bucket.getName())) {
				clearBucket();
				// delete bucket
				logger.debug("delete bucket:[" + this.bucket.getName() + "]...");
				this.ossClient.deleteBucket(this.bucket.getName());
				logger.debug("delete bucket:[" + this.bucket.getName() + "]success.");
				return OSSErrCode.OK;
			} else {
				logger.debug("bucket:[" + this.bucket.getName() + "] does not exist.");
				return OSSErrCode.BT_NOT_EXIST;
			}
		} catch (OSSException oe) {
			logger.error("fail to create bucket on AliOss(OSSException)." + "Message:" + oe.getErrorMessage());
			return OSSErrCode.OSS_EX;
        } catch (ClientException ce) {
        	logger.error("fail to create bucket on AliOss(ClientException)." + "Message:" + ce.getErrorMessage());
        	return OSSErrCode.CLT_EX;
        } catch (Exception e) {
        	logger.error("fail to create bucket on AliOss(Exception)." + "Message:" + e.getMessage());
        	return OSSErrCode.SYS_EX;
        } finally {
        	close();
        }
	}
	
	/**
	 * clear bucket
	 */
	private void clearBucket() {
		// request
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(this.bucket.getName());

		// object list
		ObjectListing listing = ossClient.listObjects(listObjectsRequest);

		// delete object
		for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
			this.ossClient.deleteObject(this.bucket.getName(), objectSummary.getKey());
		}
	}
	
	/**
	 * 临时授权访问
	 * @param key
	 * @return
	 */
	public String getSignedUrl(String key) {
		open();
		// expiration:30 minutes
		Date expiration = new Date(new Date().getTime() + 30 * 60 * 1000);
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(this.bucket.getName(), key, HttpMethod.GET);
		request.setExpiration(expiration);
		// create signed url
		URL signedUrl = this.ossClient.generatePresignedUrl(request);
		
		close();
		return signedUrl.toString();
	}
	
	/**
	 * 打开客户端
	 * @return
	 */
	private void open() {
		if(null == this.ossClient) {
			this.ossClient = new OSSClient(ossConfiguration.getEndpoint(), ossConfiguration.getAccessKeyId(), ossConfiguration.getAccessKeySecret()); 
		}
	}
	
	/**
	 * 关闭客户端
	 * @param ossClient
	 */
	private void close() {
		if(null != this.ossClient) {
			ossClient.shutdown();
		}
		this.ossClient = null;
	}

	public Bucket getBucket() {
		return bucket;
	}
}
