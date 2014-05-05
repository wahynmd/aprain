/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.bill.screen;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.huangxt.common.lang.StringEscapeUtil;
import com.huangxt.web.util.AuthUtil;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Index.java 的作用：帐务系统的默认首页
 * @author huangxt - 2012-2-19 下午8:35:38
 */
public class Index extends ScreenModule {
	private static Logger log = Logger.getLogger(Index.class);
	
	@Override
	public void execute(RunData rundata, Map<String,Object> context) {
		if( !AuthUtil.isLogin(rundata) ) {
			try {
				String target = StringEscapeUtil.escapeURL(rundata.getUrlWithoutQueryString());
				rundata.sendRedirect(rundata.getUriBroker().get("login_page") + "?target=" + target);
			} catch (IOException e) {
				log.error("Index sendRedirect error: ", e);
			}
			
			return ;
		}
		
		context.put("user", AuthUtil.getUserName(rundata));
	}
}
