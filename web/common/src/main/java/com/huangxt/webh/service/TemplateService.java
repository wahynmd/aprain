/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh.service;

import java.io.Writer;
import java.util.Map;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.huangxt.common.constance.VarConstance;
import com.huangxt.webh.constant.CommonConst;

/**
 * VelocityTemplateService.java 的作用：模板服务(目前只有velocity)
 * @author huangxt - 2011-12-3 下午2:40:23
 */
public class TemplateService implements Service {

	public static final String Service_Name = "TemplateService";
	private VelocityEngine velocityEngine;
	
	@Override
	public void init() {
		ExtendedProperties conf = new ExtendedProperties();
		
		conf.setProperty(VelocityEngine.RESOURCE_LOADER, "file");
		conf.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, VarConstance.getVMTemplatePath());
		conf.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_CACHE, "true");
		conf.setProperty("file.resource.loader.modificationCheckInterval", "3");
		conf.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		conf.setProperty(VelocityEngine.RUNTIME_LOG, VarConstance.getVMLogPath());
		
		velocityEngine = new VelocityEngine();
		velocityEngine.setExtendedProperties(conf);
		velocityEngine.init();
	}

	@Override
	public void destroy() {
		velocityEngine = null;
	}
	
	@SuppressWarnings("rawtypes")
	public void mergeTemplate(String templateName, Map context, Writer writer ) {
		velocityEngine.mergeTemplate(templateName, CommonConst.VM_Template_Encode, new VelocityContext(context), writer);
	}

}
