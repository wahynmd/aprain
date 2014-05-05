/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.bill.screen;

import java.util.Map;

import com.huangxt.biz.bill.HelloBillAO;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Hello.java 的作用：测试应用ok状态
 * @author huangxt - 2012-2-11 下午1:06:18
 */
public class Hello extends ScreenModule {
	private HelloBillAO helloBillAO;
	
	@Override
	public void execute(RunData rundata, Map<String,Object> context) {
		helloBillAO = (HelloBillAO) getLogicClassIns("helloBillAO");
		context.put("user", helloBillAO.getUserName());
	}
}
