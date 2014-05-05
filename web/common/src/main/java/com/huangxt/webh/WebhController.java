/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.huangxt.webh.constant.ContextKeyConst;
import com.huangxt.webh.service.ModuleLoadService;
import com.huangxt.webh.service.TemplateService;
import com.huangxt.webh.service.UriBrokerService;

/**
 * WebhController.java 的作用：webh框架的MVC控制器
 * @author huangxt - 2011-11-27 下午3:40:58
 */
public class WebhController {

	private Map<String, Object> services;
	private ServletContext servletContext;
	
	public WebhController(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public void config() {
		services = new HashMap<String, Object>();
		
		ModuleLoadService moduleLoadService = new ModuleLoadService();
		moduleLoadService.init();
		services.put(ModuleLoadService.Service_Name, moduleLoadService);
		
		TemplateService vtService = new TemplateService();
		vtService.init();
		services.put(TemplateService.Service_Name, vtService);
		
		UriBrokerService uriBrokerService = new UriBrokerService();
		uriBrokerService.init();
		services.put(UriBrokerService.Service_Name, uriBrokerService);
		
		servletContext.setAttribute(ContextKeyConst.Servlet_Context_Webh_Controller_Key, this);
	}

	public Map<String, Object> getServices() {
		return services;
	}
}
