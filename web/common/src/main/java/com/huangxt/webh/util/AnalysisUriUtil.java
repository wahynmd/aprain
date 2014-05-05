/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh.util;

import com.huangxt.common.lang.StringUtil;

/**
 * AnalysisUriUtil.java 的作用：分析请求url的工具类
 * @author huangxt - 2011-11-27 下午12:56:12
 */
public class AnalysisUriUtil {

	/**
	 * 根据“req.getPathInfo()”得到请求的包名
	 */
	public static String getPackageNameFromPathInfo(String pathInfo) {
		String[] pathInfos = StringUtil.split(pathInfo, "/");
		return pathInfos[0];
	}
	
	/**
	 * 根据“req.getPathInfo()”得到请求的类名（包含相对的类路径）
	 */
	public static String getRelativeClassNameFromPathInfo(String pathInfo) {
		String[] pathInfos = StringUtil.split(pathInfo, "/");
		
		String relativeClassPath = "";
		if( pathInfos.length > 2 ) {
			for( int i = 1; i < pathInfos.length - 1; i++ ) {
				relativeClassPath = relativeClassPath + pathInfos[i] + ".";
			}
		}
		return relativeClassPath + StringUtil.capitalize(StringUtil.replace(pathInfos[pathInfos.length-1], ".htm", ""));
	}
	
	/**
	 * 根据“req.getPathInfo()”得到请求的模板名
	 */
	public static String getTemplateNameFromPathInfo(String pathInfo) {
		return StringUtil.replace(pathInfo, ".htm", ".vm");
	}
}
