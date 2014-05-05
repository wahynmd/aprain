/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.huangxt.common.lang.ClassLoaderUtil;
import com.huangxt.common.lang.CommonLangException;
import com.huangxt.webh.constant.CommonConst;
import com.huangxt.webh.module.Module;

/**
 * ModuleLoadService.java 的作用：装载module模块的服务
 * @author huangxt - 2011-11-27 下午4:48:55
 */
public class ModuleLoadService implements Service {
	private static Logger log = Logger.getLogger(ModuleLoadService.class.getName());
	
	public static final String Service_Name = "ModuleLoadService";
	
	private Map<String, Module> moduleCache;
	
	@Override
	public void init() {
		moduleCache = Collections.synchronizedMap(new HashMap<String, Module>());
	}

	@Override
	public void destroy() {
		moduleCache = null;
	}

	public Module getModule(String moduleType, String packageName, String className) {
		String moduleKey = "";
		Module module = null;
		
		if( CommonConst.Is_Web_Layer_Class_Use_Cache ) {
			moduleKey = getModuleKey(moduleType, packageName, className);
			module = moduleCache.get(moduleKey);
			
			if( module != null ) {
				return module;
			}
		}
		
		module = loadModule(moduleType, packageName, className);
		
		if( CommonConst.Is_Web_Layer_Class_Use_Cache ) {
			moduleCache.put(moduleKey, module);
		}
		
		return module;
	}
	
	private String getModuleKey(String moduleType, String packageName, String className) {
		return moduleType + "." + packageName + "." + className;
	}
	
	private Module loadModule(String moduleType, String packageName, String className) {
		String classPath = getModuleClassPath( CommonConst.Web_Layer_Package_Prefix, packageName, moduleType, className );
		Module module = insModule(classPath);
		if( module == null ) {
			module = insModule(CommonConst.Web_Layer_NoScreen_Package_Prefix);
		}
		return module;
	}
	
	private String getModuleClassPath(String prefix, String packageName, String moduleType, String className) {
		return prefix + "." + packageName + "." + moduleType + "." + className;
	}
	
	@SuppressWarnings("rawtypes")
	private static Module insModule(String classPath) {
		Class moduleClass;
		
		try {
			moduleClass = ClassLoaderUtil.loadClass(classPath);
			
			if( !Module.class.isAssignableFrom(moduleClass) ) {
				return null;
			}
		} catch (ClassNotFoundException e) {
			return null;
		} catch (CommonLangException e) {
			log.error("ModuleLoadService.insModule() error, classPath is " + classPath + ". ", e);
			return null;
		}
		
		Module module;
		
		try {
			module = (Module)moduleClass.newInstance();
		} catch (Exception e) {
			log.error("ModuleLoadService.insModule() error, classPath is " + classPath + ". ", e);
			return null;
		}
		
		return module;
	}
	
}
