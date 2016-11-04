/**
 * mail util
 * created 2016/09/07
 * by ming
 */
package com.ichunming.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ichunming.util.helper.MailConfiguration;

public class MailUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);
	
	private static final short SIMPLE_MAIL = 0;
	
	private static final short MULTIPART_MAIL = 1;
	
	private static final short HTML_MAIL = 2;
	
	private MailUtil(){}
	
	/**
	 * 发送简单邮件
	 * @param subject
	 * @param content
	 * @param to
	 * @return
	 * @throws EmailException 
	 */
	public static boolean send(MailConfiguration config, String subject, String content, String to) throws EmailException {
		SimpleEmail email = (SimpleEmail) createEmail(config, SIMPLE_MAIL);
        // 封装mail
        encapMail(email, subject, content, to);
        // 发送邮件
        email.send();

        logger.debug("邮件发送成功");
		return true;
	}
	
	/**
	 * 发送带有附件的简单邮件
	 * @param subject
	 * @param content
	 * @param to
	 * @param attachs
	 * @return
	 * @throws EmailException 
	 */
	public static boolean send(MailConfiguration config, String subject, String content, String to, List<Map<String, String>> attachs) throws EmailException {
		MultiPartEmail email = (MultiPartEmail) createEmail(config, MULTIPART_MAIL);
		// 封装mail
    	encapMail(email, subject, content, to);
        // 添加附件
    	encapAttach(email, attachs);
    	// 发送邮件
        email.send();

        logger.debug("邮件发送成功");
		return true;
	}
	
	/**
	 * 发送html格式邮件
	 * @param config
	 * @param subject
	 * @param content
	 * @param to
	 * @return
	 * @throws EmailException 
	 */
	public static boolean sendHtml(MailConfiguration config, String subject, String content, String to) throws EmailException {
		HtmlEmail email = (HtmlEmail) createEmail(config, HTML_MAIL);
		// 封装mail
    	encapMail(email, subject, content, to);
    	// 发送邮件
        email.send();

        logger.debug("邮件发送成功");
		return true;
	}
	
	/**
	 * 发送带有附件的html格式邮件
	 * @param config
	 * @param subject
	 * @param content
	 * @param to
	 * @param attachs
	 * @return
	 * @throws EmailException 
	 */
	public static boolean sendHtml(MailConfiguration config, String subject, String content, String to, List<Map<String, String>> attachs) throws EmailException {
		HtmlEmail email = (HtmlEmail) createEmail(config, HTML_MAIL);
		// 封装mail
    	encapMail(email, subject, content, to);
        // 添加附件
    	encapAttach(email, attachs);
    	// 发送邮件
        email.send();

        logger.debug("邮件发送成功");
		return true;
	}
	
	private static Email createEmail(MailConfiguration config, short type) throws EmailException {
		Email email;
		if(type == SIMPLE_MAIL) {
			email = new SimpleEmail();
		} else if(type == MULTIPART_MAIL) {
			email = new MultiPartEmail();
		} else {
			email = new HtmlEmail();
		}
		email.setHostName(config.getHost()); // 发送服务器
        email.setAuthentication(config.getUsername(), config.getPassword()); // 发送邮件的用户名和密码  
        email.setCharset(config.getCharset()); //邮件编码方式
        email.setFrom(config.getFrom(), config.getFromName()); // 发送邮箱
		return email;
	}
	
	/**
	 * 封装mail信息
	 * @param email
	 * @param subject
	 * @param content
	 * @param to
	 * @throws EmailException
	 */
	private static void encapMail(Email email, String subject, String content, String to) throws EmailException {
		email.addTo(to);
        email.setSubject(subject); // 主题
        email.setMsg(content); // 内容
	}
	
	/**
	 * 封装附件
	 * @param email
	 * @param attachs
	 * @throws EmailException
	 */
	private static void encapAttach(MultiPartEmail email, List<Map<String, String>> attachs) throws EmailException {
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
