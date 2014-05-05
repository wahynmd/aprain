/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.stock.screen;

import java.util.Map;

import com.huangxt.biz.stock.HelloStockAO;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Hello.java 的作用：测试应用ok状态
 * @author huangxt - 2012-2-11 下午1:06:18
 */
public class Hello extends ScreenModule {
	private HelloStockAO helloStockAO;
	
	@Override
	public void execute(RunData rundata, Map<String,Object> context) {
		helloStockAO = (HelloStockAO) getLogicClassIns("helloStockAO");
		context.put("sayHello", helloStockAO.sayHello());
	}
}
