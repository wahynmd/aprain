/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.stock;

import com.huangxt.common.groovy.GroovyHelper;

/**
 * HelloStockAO.java 的作用：测试groovy ok状态
 * @author huangxt - 2012-9-2 下午1:27:05
 */
public class HelloStockAO {
	private Object helloService;
	
	public String sayHello() {
		return (String)GroovyHelper.invokeMethod(helloService, "sayHello", "Hello world", " !!!~");
	}

	public void setHelloService(Object helloService) {
		this.helloService = helloService;
	}
}
