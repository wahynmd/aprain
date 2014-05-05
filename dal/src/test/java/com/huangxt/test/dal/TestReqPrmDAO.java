/**
 * 版权所有：aprain.com
 */
package com.huangxt.test.dal;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huangxt.dal.daointerface.bill.ReqPrmDAO;
import com.huangxt.dal.dataobject.bill.ReqPrmDO;

import junit.framework.TestCase;

/**
 * TestReqPrmDAO.java 的作用：
 * @author huangxt - 2012-4-17 下午2:35:25
 */
public class TestReqPrmDAO extends TestCase {
	private static ReqPrmDAO reqPrmDAO;
	
	@Override
	protected void setUp() throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("beanFactory.xml");
		reqPrmDAO = (ReqPrmDAO) appContext.getBean("reqPrmDAO");
	}
	
	@Test
	public void testCountReqPrm() {
		System.out.println(reqPrmDAO.getAllWaitReqRecordCount());
	}
	
	@Test
	public void testGetReqPrm() {
		System.out.println(reqPrmDAO.getAllWaitReqRecord(0, 2).get(0).getGmtCreate());
		System.out.println(reqPrmDAO.getAllWaitReqRecord(0, 2).get(1).getName());
	}
	
	@Test
	public void testGetReqPrmById() {
		System.out.println(reqPrmDAO.getReqRecord(1L).getName());
	}
	
	@Test
	public void testInsert() {
		ReqPrmDO reqPrmDO = new ReqPrmDO();
		
		reqPrmDO.setAddress("kufang");
		reqPrmDO.setComment("nothing");
		reqPrmDO.setName("haha22");
		reqPrmDO.setOperator("hxt");
		reqPrmDO.setExpectTime("2012...");
		reqPrmDO.setIsPay("no_hy");
		reqPrmDO.setState("wait");
		
		System.out.println(reqPrmDAO.insert(reqPrmDO));
	}
	
	@Test
	public void testUpdateState() {
		reqPrmDAO.updateState(1L, "complete", "hxt1");
		reqPrmDAO.updateState(2L, "cancel", "hxt1");
	}
}
