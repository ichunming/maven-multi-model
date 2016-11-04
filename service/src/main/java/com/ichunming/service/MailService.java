/**
 * Mail Service Interface
 * 2016/10/09 ming
 * v0.1
 */
package com.ichunming.service;

import java.util.List;
import java.util.Map;

public interface MailService {
	/**
	 * send simple mail
	 * @param subject
	 * @param content
	 * @param to
	 * @return
	 */
	public boolean send(String subject, String content, String to);
	
	/**
	 * send mail with attachment
	 * @param subject
	 * @param content
	 * @param to
	 * @param attachs
	 * @return
	 */
	public boolean send(String subject, String content, String to, List<Map<String, String>> attachs);
	
	/**
	 * send html mail
	 * @param subject
	 * @param content
	 * @param to
	 * @return
	 */
	public boolean sendHtml(String subject, String content, String to);
	
	/**
	 * send html mail with attachment
	 * @param subject
	 * @param content
	 * @param to
	 * @param attachs
	 * @return
	 */
	public boolean sendHtml(String subject, String content, String to, List<Map<String, String>> attachs);
}
