/**
 * 版权所有：aprain.com
 */
package com.huangxt.task.tools;

import java.io.File;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

/**
 * GroovyTest.java 的作用：测试Groovy脚本
 * @author huangxt - 2012-9-1 下午8:35:51
 */
public class GroovyTest {
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		Class scriptClass = null;
		
		try {
			scriptClass = new GroovyClassLoader().parseClass(new File("/personal/workspace_3.7/aprain/task/src/main/java/com/huangxt/task/groovy/helloworld/Hello.groovy"));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		GroovyObject newInstance;
		try {
			newInstance = (GroovyObject) scriptClass.newInstance();
			String result = (String) newInstance.invokeMethod("sayHello", "hxt");
			System.out.println(result);
		}  catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
