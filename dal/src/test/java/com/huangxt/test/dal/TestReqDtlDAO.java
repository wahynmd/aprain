/**
 * 版权所有：aprain.com
 */
package com.huangxt.test.dal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huangxt.dal.daointerface.bill.ReqDtlDAO;
import com.huangxt.dal.dataobject.bill.ReqDtlDO;

import junit.framework.TestCase;

/**
 * TestReqDtlDAO.java 的作用：
 * @author huangxt - 2012-4-16 下午10:15:32
 */
public class TestReqDtlDAO extends TestCase {
	private static ReqDtlDAO reqDtlDAO;
	
	@Override
	protected void setUp() throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("beanFactory.xml");
		reqDtlDAO = (ReqDtlDAO) appContext.getBean("reqDtlDAO");
	}
	
	@Test
	public void testInsert() {
		ReqDtlDO reqDtlDO1 = new ReqDtlDO();
		
		reqDtlDO1.setGrade(4L);
		reqDtlDO1.setHeight(2L);
		reqDtlDO1.setMaterial(3L);
		reqDtlDO1.setNum(50);
		reqDtlDO1.setPrice("123.45");
		reqDtlDO1.setPrmId(100L);
		reqDtlDO1.setSize(1L);
		
		ReqDtlDO reqDtlDO2 = new ReqDtlDO();
		
		reqDtlDO2.setGrade(40L);
		reqDtlDO2.setHeight(20L);
		reqDtlDO2.setMaterial(30L);
		reqDtlDO2.setNum(500);
		reqDtlDO2.setPrice("678.90");
		reqDtlDO2.setPrmId(1000L);
		reqDtlDO2.setSize(10L);
		
		
		List<ReqDtlDO> doList = new ArrayList<ReqDtlDO>();
		doList.add(reqDtlDO1);
		doList.add(reqDtlDO2);
		
		reqDtlDAO.insert(doList);
	}
	
	@Test
	public void testQueryById() {
		System.out.println(reqDtlDAO.getDtlsByPrmId(1000L).get(0).getPrice());
		System.out.println(reqDtlDAO.getDtlsByPrmId(1000L).get(1).getPrice());
	}
}
