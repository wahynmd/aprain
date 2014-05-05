/**
 * 版权所有：aprain.com
 */
package com.huangxt.test.dal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huangxt.dal.daointerface.bill.CargoDtlDAO;
import com.huangxt.dal.dataobject.bill.CargoDtlDO;

import junit.framework.TestCase;

/**
 * TestCargoDtlDAO.java 的作用：
 * @author huangxt - 2012-4-16 下午9:47:48
 */
public class TestCargoDtlDAO extends TestCase {
	private static CargoDtlDAO cargoDtlDAO;
	
	@Override
	protected void setUp() throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("beanFactory.xml");
		cargoDtlDAO = (CargoDtlDAO) appContext.getBean("cargoDtlDAO");
	}
	
	@Test
	public void testInsert() {
		CargoDtlDO cargoDtlDO1 = new CargoDtlDO();
		
		cargoDtlDO1.setGrade(4L);
		cargoDtlDO1.setHeight(2L);
		cargoDtlDO1.setMaterial(3L);
		cargoDtlDO1.setNum(50);
		cargoDtlDO1.setPrice("123.45");
		cargoDtlDO1.setPrmId(100L);
		cargoDtlDO1.setSize(1L);
		
		CargoDtlDO cargoDtlDO2 = new CargoDtlDO();
		
		cargoDtlDO2.setGrade(40L);
		cargoDtlDO2.setHeight(20L);
		cargoDtlDO2.setMaterial(30L);
		cargoDtlDO2.setNum(500);
		cargoDtlDO2.setPrice("678.90");
		cargoDtlDO2.setPrmId(1000L);
		cargoDtlDO2.setSize(10L);
		
		
		List<CargoDtlDO> doList = new ArrayList<CargoDtlDO>();
		doList.add(cargoDtlDO1);
		doList.add(cargoDtlDO2);
		
		cargoDtlDAO.insert(doList);
	}
	
	@Test
	public void testQueryById() {
		System.out.println(cargoDtlDAO.getDtlsByPrmId(1000L).get(0).getPrice());
		System.out.println(cargoDtlDAO.getDtlsByPrmId(1000L).get(1).getPrice());
	}
}
