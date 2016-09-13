/**
 * mail util
 * 2016/09/07 ming
 * v0.1
 */
package com.ichunming.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);
	
	// host
	private String host;
	
	// username
	private String username;
	
	// password
	private String password;
	
	// charset
	private String charset = "utf-8";
	
	// from
	private String fromEmail;
	
	// constructor
	public MailUtil(String host, String username, String password, String fromEmail) {
		this.host = host;
		this.username = username;
		this.password = password;
		this.fromEmail = fromEmail;
	}
	
	// constructor
	public MailUtil(String host, String username, String password, String fromEmail, String charset) {
		this.host = host;
		this.username = username;
		this.password = password;
		this.fromEmail = fromEmail;
		this.charset = charset;
	}
	
	/**
	 * 发送简单邮件
	 * @param subject
	 * @param content
	 * @param to
	 * @return
	 */
	public boolean send(String subject, String content, String to) {
		SimpleEmail email = new SimpleEmail();
        try {
	        // 封装mail
	        encapMail(email, subject, content, to);
	        // 发送邮件
	        email.send();
		} catch (EmailException e) {
			logger.error("邮件发送失败");
			return false;
		}

        logger.debug("邮件发送成功");
		return true;
	}
	
	/**
	 * 发送带有附件的邮件
	 * @param subject
	 * @param content
	 * @param to
	 * @param attachs
	 * @return
	 */
	public boolean send(String subject, String content, String to, List<Map<String, String>> attachs) {
		MultiPartEmail email = new MultiPartEmail();
        try {
			// 封装mail
        	encapMail(email, subject, content, to);
	        // 添加附件
        	encapAttach(email, attachs);
        	// 发送邮件
	        email.send();
		} catch (EmailException e) {
			logger.error("邮件发送失败");
			return false;
		}

        logger.debug("邮件发送成功");
		return true;
	}
	
	/**
	 * 封装mail信息
	 * @param email
	 * @param subject
	 * @param content
	 * @param to
	 * @throws EmailException
	 */
	private void encapMail(Email email, String subject, String content, String to) throws EmailException {
		email.setHostName(host); // 发送服务器
        email.setAuthentication(username, password); // 发送邮件的用户名和密码  
        email.setCharset(charset); //邮件编码方式
		email.addTo(to);
        email.setFrom(fromEmail); // 发送邮箱
        email.setSubject(subject); // 主题
        email.setMsg(content); // 内容
	}
	
	/**
	 * 封装附件
	 * @param email
	 * @param attachs
	 * @throws EmailException
	 */
	private void encapAttach(MultiPartEmail email, List<Map<String, String>> attachs) throws EmailException {
		if(null != attachs && attachs.size() > 0) {
			EmailAttachment att = null;
        	for(Map<String, String> attach : attachs) {
        		att = new EmailAttachment();
        		att.setName(attach.get("name"));
        		att.setPath(attach.get("path"));
        		email.attach(att);
        	}
        }
	}
}
