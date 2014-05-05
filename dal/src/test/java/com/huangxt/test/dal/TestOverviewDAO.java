/**
 * 版权所有：aprain.com
 */
package com.huangxt.test.dal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huangxt.dal.daointerface.bill.OverviewDAO;
import com.huangxt.dal.dataobject.bill.OverviewDO;

import junit.framework.TestCase;

/**
 * TestOverviewDAO.java 的作用：
 * @author huangxt - 2012-4-10 下午9:33:21
 */
public class TestOverviewDAO extends TestCase {
	private static OverviewDAO overviewDAO;
	
	@Override
	protected void setUp() throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("beanFactory.xml");
		overviewDAO = (OverviewDAO) appContext.getBean("overviewDAO");
	}
	
	@Test
	public void testGetOverviewByCon() {
		System.out.println(overviewDAO.getOverviewByCon(null, null, null, null, null, 0, 3).get(3).getAddress());
		System.out.println(overviewDAO.getOverviewByCon(1L, null, null, null, null, 0, 3).get(1).getMaterial());
	}
	
	@Test
	public void testCountOverviewByCon() {
		System.out.println(overviewDAO.getOverviewSizeByCon(null, null, null, null, null));
		System.out.println(overviewDAO.getOverviewSizeByCon(1L, null, null, null, null));
		System.out.println(overviewDAO.getOverviewSizeByCon(2L, null, null, null, null));
		System.out.println(overviewDAO.getOverviewSizeByCon(7L, null, null, null, null));
	}
	
	@Test
	public void testInsert() {
		System.out.println(overviewDAO.insertForFirstTime(100L, 100L, 100L, 100L, "100addr", 100));
		System.out.println(overviewDAO.insertForFirstTime(101L, 101L, 101L, 101L, "101addr", 101));
	}
	
	@Test
	public void testUpdate() {
		List<OverviewDO> list = new ArrayList<OverviewDO>();
		
		OverviewDO do1 = new OverviewDO();
		do1.setAddress("5");
		do1.setGrade(3L);
		do1.setHeight(2L);
		do1.setMaterial(4L);
		do1.setSize(1L);
		do1.setStock(60);
		list.add(do1);
		
		OverviewDO do2 = new OverviewDO();
		do2.setAddress("11");
		do2.setGrade(9L);
		do2.setHeight(8L);
		do2.setMaterial(10L);
		do2.setSize(7L);
		do2.setStock(120);
		list.add(do2);
		
		overviewDAO.updateOverview(list);
	}
}
