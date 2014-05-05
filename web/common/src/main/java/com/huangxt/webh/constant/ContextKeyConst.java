/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh.constant;

/**
 * ContextKeyConst.java 的作用：上下文的key常量
 * @author huangxt - 2011-11-27 下午5:10:14
 */
public class ContextKeyConst {

	/** ServletContext中webhController的key */
	public static final String Servlet_Context_Webh_Controller_Key = "webh.controller";
	
	/** HttpServletRequest中servlet的context的key */
	public static final String Servlet_Context_Key = "J2ee.ServletContext";
	
	/** HttpServletRequest中返回给vm的context的key */
	public static final String VM_Context_Key = "webh.context";
	
	/** HttpServletRequest中内部重定向的目标的key */
	public static final String Redirect_Target_Context_Key = "webh.redirect.target";
	
	//下面的给类似于PullTool使用
	/** request对象中Uriroker的key */
	public static final String Uri_Broker_Key = "webh.uri";
	
	/** 放入context对象中的Uriroker的key */
	public static final String Uri_Broker_Context_Key = "uriBroker";
	/** 放入context对象中的StringUtil的key */
	public static final String String_Util_Context_Key = "stringUtil";
}
