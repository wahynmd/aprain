/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh.rundata;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huangxt.common.lang.StringUtil;
import com.huangxt.webh.constant.ContextKeyConst;

/**
 * RunData.java 的作用：web层请求的运行时数据
 * @author huangxt - 2012-2-19 下午10:00:58
 */
public class RunData {
	private HttpServletRequest req;
	private HttpServletResponse resp;
	
	private boolean isRedirect = false;
	private String target = "";
	
	/** 取得内部重定向的目标url */
	public String getTarget() {
		return target;
	}
	
	public RunData(HttpServletRequest a, HttpServletResponse b) {
		req = a;
		resp = b;
	}
	
	/**
	 * 取得 HttpServletRequest 对象
	 */
	public HttpServletRequest getHttpRequest() {
		return req;
	}
	
	/**
	 * 取得 HttpServletResponse 对象
	 */
	public HttpServletResponse getHttpResponse() {
		return resp;
	}
	
	/**
	 * 判断系统是否调用重定向（包括内部重定向和外部重定向）
	 */
	public boolean isRedirect() {
		return isRedirect;
	}
	
	/**
	 * 外部重定向(形如---http://www.aprain.com/bill/index.htm)
	 * @throws IOException
	 */
	public void sendRedirect(String url) throws IOException {
		isRedirect = true;
		resp.sendRedirect(url);
	}
	
	/**
	 * 内部重定向(形如---index.htm)
	 */
	public void forward(String url) {
		isRedirect = true;
		//先赋值，延迟提交
		target = url;
	}
	
	/**
	 * 取得复杂类型入参属性（如hashmap等），如果没有，则返回null（框架内部的数据，非前台post的数据）
	 */
	public Object getObjectAttr(String key) {
		return req.getAttribute(key);
	}
	
	/**
	 * 取得uriBroker对象
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getUriBroker() {
		return (Map<String, String>) getObjectAttr(ContextKeyConst.Uri_Broker_Key);
	}
	
	/**
	 * 取得字符串参数，如果没有，则返回指定的默认值
	 */
	public String getStringParam(String key, String defaultValue) {
		String result = req.getParameter(key);
		
		if( result == null ) {
			return defaultValue;
		} else {
			return result;
		}
	}
	
	/**
	 * 取得整形参数，如果没有，则返回指定的默认值
	 */
	public Integer getIntParam(String key, Integer defaultValue) {
		if( !StringUtil.isNumeric(req.getParameter(key)) ) {
			return defaultValue;
		}
		if( StringUtil.isBlank(req.getParameter(key)) ) {
			return defaultValue;
		}
		
		Integer result = Integer.valueOf(req.getParameter(key));
		
		if( result == null ) {
			return defaultValue;
		} else {
			return result;
		}
	}
	
	/**
	 * 取得长整形参数，如果没有，则返回指定的默认值
	 */
	public Long getLongParam(String key, Long defaultValue) {
		if( !StringUtil.isNumeric(req.getParameter(key)) ) {
			return defaultValue;
		}
		if( StringUtil.isBlank(req.getParameter(key)) ) {
			return defaultValue;
		}
		
		Long result = Long.valueOf(req.getParameter(key));
		
		if( result == null ) {
			return defaultValue;
		} else {
			return result;
		}
	}
	
	/**
	 * 取得完整的url请求路径，包括域名、端口，但不包括参数串
	 */
	public String getUrlWithoutQueryString() {
		return req.getRequestURL().toString();
	}
	
	/**
	 * 取得完整的url请求路径，包括域名、端口、参数串
	 */
	public String getUrlWithQueryString() {
		if( StringUtil.isNotBlank(req.getQueryString()) ) {
			return req.getRequestURL().append("?").append(req.getQueryString()).toString();
		} else {
			return req.getRequestURL().toString();
		}
	}
	
	/**
	 * 取得请求的url的参数串
	 */
	public String getQueryString() {
		return req.getQueryString().toString();
	}
	
	/**
	 * 取得来访的ip地址
	 */
	public String getRemoteIPAddr() {
		return req.getRemoteAddr();
	}
	
	/**
	 * 取得来访的用户名（返回的有可能是ip地址）
	 * 注：要在tomcat的server.xml文件里的“<Connector port="9091"...”标签里配置 enableLookups="true" 属性。
	 */
	public String getRemoteUserName() {
		return req.getRemoteHost();
	}
}
