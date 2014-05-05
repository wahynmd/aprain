/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh.service;

/**
 * Service.java 的作用：webh框架service接口
 * @author huangxt - 2011-11-27 下午4:44:20
 */
public interface Service {

	/** 初始化service */
	public void init();
	
	/** 释放service，使之复原到为未初始化状态 */
	public void destroy();
}
