/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.common.screen;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.huangxt.common.constance.CommonConstance;
import com.huangxt.common.constance.KeyConstance;
import com.huangxt.web.util.AuthUtil;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Login.java 的作用：网站的通用登录模块
 * @author huangxt - 2012-2-19 下午8:19:10
 */
public class Login extends ScreenModule {
	private static Logger log = Logger.getLogger(Login.class.getName());
	
	/** 完成登录后url重定向的参数的key */
	private static final String Redirect_Url_Key = "target";
	/** 登录的用户名的参数的key */
	private static final String Username_Key = "user";
	/** 登录的用户密码的参数的key */
	private static final String Password_Key = "pass";
	/** context中登录页面中action的url的key */
	private static final String Url_Context_Key = "url";
	/** context中登录是否登录错误的key */
	private static final String Err_Context_Key = "error";
	
	@Override
	public void execute(RunData rundata, Map<String,Object> context) {
		//如果已经登录的话，跳转到默认首页
		if( AuthUtil.isLogin(rundata) ) {
			try {
				rundata.sendRedirect(rundata.getUriBroker().get("bill_index_page"));
				return ;
			} catch (IOException e) {
				log.error("Login sendRedirect error: ", e);
			}
		}
		
		String username = rundata.getStringParam(Username_Key, null);
		String password = rundata.getStringParam(Password_Key, null);
		String url = rundata.getUrlWithQueryString();
		
		if( (username == null) || (password == null) ) {
			context.put(Url_Context_Key, url);
			return ;
		}
		
		//如果用户名密码匹配成功
		if( isUserAndPassRight(username, password) ) {
			HttpSession session = rundata.getHttpRequest().getSession();
			session.setAttribute(KeyConstance.Session_Username_Key, username);
			session.setAttribute(KeyConstance.Session_Userpass_Key, password);
			session.setAttribute(KeyConstance.Session_Is_User_Login_Key, CommonConstance.Session_User_Has_Login);
			session.setMaxInactiveInterval(CommonConstance.Session_Timeout_Seconds);
			
			try {
				String target = rundata.getStringParam(Redirect_Url_Key, null);
				
				if( target == null ) {	//如果没有重定向参数，则跳转到默认首页
					rundata.sendRedirect(rundata.getUriBroker().get("bill_index_page"));
				} else {
					rundata.sendRedirect(target);
				}
				
			} catch (IOException e) {
				log.error("Login sendRedirect error: ", e);
			}
		} else {
			context.put(Err_Context_Key, "error");
			context.put(Url_Context_Key, url);
		}
	}
	
	/** 判断用户名密码是否正确 */
	//TODO 暂时写死用户名密码
	private boolean isUserAndPassRight(String username, String password) {
		if( "hxt".equals(username) && "qazwsx".equals(password) ) {
			return true;
		} else if( "hzh".equals(username) && "27785".equals(password) ) {
			return true;
		} else if( "lj".equals(username) && "870420".equals(password) ) {
			return true;
		} else if( "seller".equals(username) && "xinfuda999".equals(password) ) {
			return true;
		} else if( "guest".equals(username) && "guest".equals(password) ) {
			return true;
		} else {
			return false;
		}
	}
}
