/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.util;

import javax.servlet.http.HttpSession;

import com.huangxt.common.constance.CommonConstance;
import com.huangxt.common.constance.KeyConstance;
import com.huangxt.common.lang.StringUtil;
import com.huangxt.webh.rundata.RunData;

/**
 * AuthUtil.java 的作用：登录相关的工具类
 * @author huangxt - 2012-2-19 下午8:39:39
 */
public class AuthUtil {
	
	/**
	 * 判断访问的用户是否登录
	 */
	public static boolean isLogin(RunData rundata) {
		HttpSession session = rundata.getHttpRequest().getSession(false);
		
		if( session == null ) {
			return false;
		}
		
		Object isLogin = session.getAttribute(KeyConstance.Session_Is_User_Login_Key);
		if( isLogin instanceof String ) {
			if( StringUtil.equals((String)isLogin, CommonConstance.Session_User_Has_Login) ) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 从session中取得登录的用户名
	 */
	public static String getUserName(RunData rundata) {
		HttpSession session = rundata.getHttpRequest().getSession(false);
		
		if( session == null ) {
			return "";
		}
		
		Object username = session.getAttribute(KeyConstance.Session_Username_Key);
		if( isLogin(rundata) && (username instanceof String) && StringUtil.isNotBlank((String)username) ) {
			return (String)username;
		}
		
		return "";
	}
	
}
