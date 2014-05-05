/**
 * 版权所有：aprain.com
 */
package com.huangxt.test.dal;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huangxt.dal.daointerface.HelloDAO;

/**
 * TestHelloDAO.java 的作用：单元测试
 * @author huangxt - 2012-2-18 下午4:59:13
 */
public class TestHelloDAO extends TestCase {
	private static HelloDAO helloDAO;
	
	@Override
	protected void setUp() throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("beanFactory.xml");
		helloDAO = (HelloDAO) appContext.getBean("helloDAO");
	}
	
	@Test
	public void testHello() {
		assertTrue(helloDAO.getHelloDOList().size() > 0);
		System.out.println("success");
	}
}
