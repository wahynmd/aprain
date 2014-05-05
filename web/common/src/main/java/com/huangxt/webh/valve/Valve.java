/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh.valve;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Valve.java 的作用：webh框架的阀接口
 * @author huangxt - 2011-11-26 下午4:53:38
 */
public interface Valve {
	
	/**
	 * 初始化valve
	 */
	public void init(ServletContext servletContext);
	
	/**
	 * 处理valve的逻辑
	 */
	public void invoke(HttpServletRequest req, HttpServletResponse resp);
}
