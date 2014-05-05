/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.personal.screen;

import java.io.IOException;
import java.util.Map;

import com.huangxt.common.lang.StringUtil;
import com.huangxt.common.util.LogUtil;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Mylove.java 的作用：你懂的
 * @author huangxt - 2012-3-11 下午1:53:22
 */
public class Mylove extends ScreenModule {

	@Override
	public void execute(RunData rundata, Map<String, Object> context) {
		String name = rundata.getStringParam("name", null);
		String msg = rundata.getStringParam("msg", null);
		String ip = rundata.getRemoteIPAddr();
		
		if( StringUtil.isNotBlank(name) && StringUtil.isNotBlank(msg) ) {
			LogUtil.ipMsg("Visit mylove page, name is " + name + ", msg is " + msg + ", ip is " + ip);
			
			try {
				rundata.sendRedirect(rundata.getUriBroker().get("personal_package") + "/mylove.htm");
			} catch (IOException e) {
				
			}
		} else {
			LogUtil.ipMsg("Visit mylove page, ip is " + ip);
		}
	}
}
