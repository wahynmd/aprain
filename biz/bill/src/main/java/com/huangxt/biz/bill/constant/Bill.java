/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.bill.constant;

import java.util.Map;

import com.huangxt.common.util.ChineseMappingUtil;

/**
 * Bill.java 的作用：bill业务模块下的中文常量映射
 * @author huangxt - 2012-5-5 下午3:55:29
 */
public class Bill {
	/** 根据类名加载同名同类路径的xml中文映射文件，并获取某个key的值 */
	public static String getItem(String key) {
		return ChineseMappingUtil.getItem(Bill.class, key);
	}
	
	/** 根据类名加载同名同类路径的xml中文映射文件，并获取该配置文件所有的值 */
	public static Map<String, String> getItems() {
		return ChineseMappingUtil.getItems(Bill.class);
	}
}
