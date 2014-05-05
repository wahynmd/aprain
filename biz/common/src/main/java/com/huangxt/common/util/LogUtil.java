/**
 * 版权所有：aprain.com
 */
package com.huangxt.common.util;

import org.apache.commons.logging.LogFactory;

/**
 * LogUtil.java 的作用：有关日志的工具类
 * @author huangxt - 2012-3-18 下午1:59:34
 */
public class LogUtil {
	/** 逻辑错误的错误日志名字 */
	private static final String Logic_Log_Name = "logicErr";
	/** ip信息的日志名字 */
	private static final String Ip_Log_Name = "ipMsg";
	/** task错误信息的日志名字 */
	private static final String Task_Log_Name = "taskErr";
	
	/** 打印逻辑警告信息 */
	public static void logicWarn(String msg) {
		LogFactory.getLog(Logic_Log_Name).warn(msg);
	}
	
	/** 打印逻辑错误信息 */
	public static void logicErr(String msg) {
		LogFactory.getLog(Logic_Log_Name).error(msg);
	}
	
	/** 打印ip信息 */
	public static void ipMsg(String msg) {
		LogFactory.getLog(Ip_Log_Name).warn(msg);
	}
	
	/** 打印task错误信息 */
	public static void taskErr(String msg) {
		LogFactory.getLog(Task_Log_Name).error(msg);
	}
}
