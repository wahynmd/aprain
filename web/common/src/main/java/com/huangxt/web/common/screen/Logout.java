/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.common.screen;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Logout.java 的作用：网站的通用退出模块
 * @author huangxt - 2012-2-19 下午9:23:35
 */
public class Logout extends ScreenModule {
	private static Logger log = Logger.getLogger(Logout.class.getName());
	
	@Override
	public void execute(RunData rundata, Map<String, Object> context) {
		HttpSession session = rundata.getHttpRequest().getSession(false);
		
		if( session != null ) {
			session.invalidate();
		}
		
		try {
			rundata.sendRedirect(rundata.getUriBroker().get("login_page"));
		} catch (IOException e) {
			log.error("Logout sendRedirect error: ", e);
		}
		
	}
}
