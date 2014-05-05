/**
 * 版权所有：aprain.com
 */
package com.huangxt.test.dal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huangxt.dal.daointerface.bill.ItemDAO;

import junit.framework.TestCase;

/**
 * TestItemDAO.java 的作用：
 * @author huangxt - 2012-4-10 下午8:37:07
 */
public class TestItemDAO extends TestCase {
	private static ItemDAO itemDAO;
	
	@Override
	protected void setUp() throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("beanFactory.xml");
		itemDAO = (ItemDAO) appContext.getBean("itemDAO");
	}
	
	@Test
	public void testGetByTypes() {
		List<String> types = new ArrayList<String>();
		types.add("name");
		System.out.println(itemDAO.getItemListByTypes(types).get(0).getValue());
		System.out.println(itemDAO.getItemListByTypes(types).get(1).getValue());
	}
	
	@Test
	public void testGetByIds() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		System.out.println(itemDAO.getItemListByIds(ids).get(0).getType());
	}
}
