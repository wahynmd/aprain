/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh.module;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huangxt.webh.rundata.RunData;

/**
 * Module.java 的作用：一个模块，比如screen、action等
 * @author huangxt - 2011-11-26 下午6:40:08
 */
public interface Module {

	/**
	 * 执行module的用户部分逻辑
	 */
	public void execute(RunData rundata, Map<String,Object> context);
	
	/**
	 * 执行一个module的整个流程
	 */
	public void invoke(HttpServletRequest req, HttpServletResponse resp);
}
