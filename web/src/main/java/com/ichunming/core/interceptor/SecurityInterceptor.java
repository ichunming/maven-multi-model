package com.ichunming.core.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ichunming.core.exception.InvalidSessionException;

public class SecurityInterceptor extends HandlerInterceptorAdapter{
	private static Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

	private List<String> excludeUrls;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		logger.debug(url);
		if(excludeUrls.contains(url)) {
			return true;
		}
		
		HttpSession session = request.getSession(false);
		if (session == null || "".equalsIgnoreCase((String)session.getAttribute("name"))) {
			logger.warn("No Session." );
			throw new InvalidSessionException("2001","No Session.");
		} else {
			logger.debug((String)session.getAttribute("name"));
		}
		return true;
	}
}