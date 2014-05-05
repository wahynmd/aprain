/**
 * 版权所有：aprain.com
 */
package com.huangxt.common.groovy;

import groovy.lang.GroovyObject;

/**
 * GroovyHelper.java 的作用：封装对Groovy的依赖
 * @author huangxt - 2012-9-2 下午2:21:38
 */
public class GroovyHelper {
	
	/** 调用一个groovy方法 */
	public static Object invokeMethod(Object groovyObj, String methodName, Object... args) {
		if( !(groovyObj instanceof GroovyObject) ) {
			return null;
		}
		
		GroovyObject gObj = (GroovyObject)groovyObj;
		return gObj.invokeMethod(methodName, args);
	}
}
