/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.open.screen;

import java.util.Map;

import com.huangxt.biz.open.HelloOpenAO;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Hello.java 的作用：测试open package ok状态
 * @author huangxt - 2012-11-10 下午1:06:18
 */
public class Hello extends ScreenModule {
	private HelloOpenAO helloOpenAO;
	
	@Override
	public void execute(RunData rundata, Map<String,Object> context) {
		helloOpenAO = (HelloOpenAO) getLogicClassIns("helloOpenAO");
		context.put("user", helloOpenAO.getUserName());
	}
}
