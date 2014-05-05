/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh.module;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huangxt.common.lang.StringUtil;
import com.huangxt.webh.WebhController;
import com.huangxt.webh.constant.CommonConst;
import com.huangxt.webh.constant.ContextKeyConst;
import com.huangxt.webh.rundata.RunData;
import com.huangxt.webh.service.TemplateService;
import com.huangxt.webh.service.UriBrokerService;
import com.huangxt.webh.util.AnalysisUriUtil;

/**
 * ScreenModule.java 的作用：screen模块，表示展示一个页面的逻辑
 * @author huangxt - 2011-11-26 下午6:53:30
 */
public class ScreenModule implements Module {
	private static Logger log = Logger.getLogger(ScreenModule.class.getName());
	private static ApplicationContext appContext = new ClassPathXmlApplicationContext(CommonConst.Spring_Bean_File_Classpath);
	
	public void execute(RunData rundata, Map<String, Object> context) {
		//无screen的页面什么也不执行，有的话就覆盖该方法实现具体的业务逻辑
	}

	@Override
	@SuppressWarnings("unchecked")
	public void invoke(HttpServletRequest req, HttpServletResponse resp) {
		resp.setCharacterEncoding(CommonConst.VM_Template_Encode);
		
		RunData rundata = new RunData(req, resp);
		Map<String, Object> context = new HashMap<String,Object>();
		
		if( req.getAttribute(ContextKeyConst.VM_Context_Key) != null ) {
			Map<String, Object> c = (Map<String, Object>)req.getAttribute(ContextKeyConst.VM_Context_Key);
			context.putAll(c);
			req.removeAttribute(ContextKeyConst.VM_Context_Key);
		}
		
		injectInitData(rundata);
		execute(rundata, context);
		injectPullToolData(rundata, context);
		req.setAttribute(ContextKeyConst.VM_Context_Key, context);
		
		if( !rundata.isRedirect() ) {
			mergeTemplate(req, resp);
		} else if( StringUtil.isNotBlank(rundata.getTarget()) ) {
			req.setAttribute(ContextKeyConst.Redirect_Target_Context_Key, rundata.getTarget());
		}
	}
	
	/** 初始数据的填充，比如：UriBrokerMap */
	private void injectInitData(RunData rundata) {
		UriBrokerService uriBrokerService = getUriBrokerService(rundata.getHttpRequest());
		rundata.getHttpRequest().setAttribute(ContextKeyConst.Uri_Broker_Key, uriBrokerService.getUriMap());
	}
	
	/** 将rundata里req的入参属性放入到context中以便vm渲染使用，相当于PullTool的功能 */
	private void injectPullToolData(RunData rundata, Map<String, Object> context) {
		context.put(ContextKeyConst.Uri_Broker_Context_Key, rundata.getUriBroker());
		context.put(ContextKeyConst.String_Util_Context_Key, com.huangxt.common.lang.StringUtil.class);
	}
	
	/**
	 * 根据名字取得业务逻辑所在类的实例
	 */
	protected Object getLogicClassIns(String springBeanName) {
		return appContext.getBean(springBeanName);
	}
	
	/**
	 * 渲染模板
	 */
	@SuppressWarnings("rawtypes")
	private void mergeTemplate(HttpServletRequest req, HttpServletResponse resp) {
		try {
			TemplateService velocityTemplateService = getVelocityTemplateService(req);
			String templateName = AnalysisUriUtil.getTemplateNameFromPathInfo(req.getPathInfo());
			Map context = (Map)req.getAttribute(ContextKeyConst.VM_Context_Key);
			velocityTemplateService.mergeTemplate(templateName, context, resp.getWriter());
		} catch(Throwable t) {
			log.error("ScreenModule.mergeTemplate() error: ", t);
		}
	}
	
	/**
	 * 取得velocity模板引擎服务
	 */
	private TemplateService getVelocityTemplateService(HttpServletRequest req) {
		ServletContext servletContext = (ServletContext)req.getAttribute(ContextKeyConst.Servlet_Context_Key);
		WebhController controller = (WebhController)servletContext.getAttribute(ContextKeyConst.Servlet_Context_Webh_Controller_Key);
		return (TemplateService)controller.getServices().get(TemplateService.Service_Name);
	}
	
	/**
	 * 取得UriBroker服务
	 */
	private UriBrokerService getUriBrokerService(HttpServletRequest req) {
		ServletContext servletContext = (ServletContext)req.getAttribute(ContextKeyConst.Servlet_Context_Key);
		WebhController controller = (WebhController)servletContext.getAttribute(ContextKeyConst.Servlet_Context_Webh_Controller_Key);
		return (UriBrokerService)controller.getServices().get(UriBrokerService.Service_Name);
	}
}
