/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.stock.groovy.service

/**
 * HelloService.groovy 的作用：web应用里第一个groovy服务，整合spring测试
 * @author huangxt - 2012-9-2 下午1:13:32
 */
class HelloService {
	private static String name = "";
	
	public String sayHello(String sayHello, String dot) {
		return sayHello + ", " + name + dot;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
