/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh.valve;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huangxt.webh.WebhController;
import com.huangxt.webh.constant.CommonConst;
import com.huangxt.webh.constant.ContextKeyConst;
import com.huangxt.webh.module.Module;
import com.huangxt.webh.service.ModuleLoadService;
import com.huangxt.webh.util.AnalysisUriUtil;

/**
 * PerformScreenValve.java 的作用：执行screen模块的代码
 * @author huangxt - 2011-11-26 下午5:04:40
 */
public class PerformScreenValve implements Valve {
	private ModuleLoadService moduleLoadService;
	
	@Override
	public void init(ServletContext servletContext) {
		WebhController controller = (WebhController)servletContext.getAttribute(ContextKeyConst.Servlet_Context_Webh_Controller_Key);
		moduleLoadService = (ModuleLoadService)controller.getServices().get(ModuleLoadService.Service_Name);
	}

	@Override
	public void invoke(HttpServletRequest req, HttpServletResponse resp) {
		String packageName = AnalysisUriUtil.getPackageNameFromPathInfo(req.getPathInfo());
		String className = AnalysisUriUtil.getRelativeClassNameFromPathInfo(req.getPathInfo());
		
		Module module = moduleLoadService.getModule(CommonConst.Screen_Module_Type ,packageName, className);
		module.invoke(req, resp);
	}
}
