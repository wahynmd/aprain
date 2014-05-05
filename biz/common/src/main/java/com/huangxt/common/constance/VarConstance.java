/**
 * 版权所有：aprain.com
 */
package com.huangxt.common.constance;

import java.util.ResourceBundle;

/**
 * VarConstance.java 的作用：一些变量的配置，比如路径等，相当于阿里的antx文件
 * @author huangxt - 2012-2-11 下午6:39:52
 */
public class VarConstance {
	/** 读取vars.properties配置文件 */
	private static ResourceBundle bundel = ResourceBundle.getBundle("vars");
	
	/** 根据key取得对应的属性值 */
	public static String getVarValueByKey(String key) {
		return bundel.getString(key);
	}
	
	/** vm模板的绝对路径 */
	public static String getVMTemplatePath() {
		return bundel.getString("billsys.vm.file.path");
	}
	
	/** velocity log4j日志输出的绝对路径 */
	public static String getVMLogPath() {
		return bundel.getString("billsys.log.file.path") + "/velocity.log";
	}
	
	/** 获取log4j日志输出的目录 */
	public static String getLogPath() {
		return bundel.getString("billsys.log.file.path");
	}
}
