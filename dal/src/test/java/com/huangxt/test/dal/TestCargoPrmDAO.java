/**
 * 版权所有：aprain.com
 */
package com.huangxt.test.dal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huangxt.dal.daointerface.bill.CargoPrmDAO;
import com.huangxt.dal.dataobject.bill.CargoPrmDO;

import junit.framework.TestCase;

/**
 * TestCargoPrmDAO.java 的作用：
 * @author huangxt - 2012-4-11 下午8:57:42
 */
public class TestCargoPrmDAO extends TestCase {
	private static CargoPrmDAO cargoPrmDAO;
	
	@Override
	protected void setUp() throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("beanFactory.xml");
		cargoPrmDAO = (CargoPrmDAO) appContext.getBean("cargoPrmDAO");
	}
	
	@Test
	public void testCountCargoPrmByCon() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date b = df.parse("2012-03-03 00:00:00");
			Date e = df.parse("2012-05-03 23:59:59");
			
			System.out.println(cargoPrmDAO.getTradeListSizeByCon("hxt", "1", "5", b, e));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetCargoPrmByCon() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date b = df.parse("2012-03-03 00:00:00");
			Date e = df.parse("2012-03-03 23:59:59");
			
			System.out.println(cargoPrmDAO.getTradeListByCon("2", "1", "3", b, e, 0, 5).get(0).getComment());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInsert() {
		CargoPrmDO cargoPrmDO = new CargoPrmDO();
		
		cargoPrmDO.setAddress("1");
		cargoPrmDO.setComment("2");
		cargoPrmDO.setIsDelete("n");
		cargoPrmDO.setName("3");
		cargoPrmDO.setOperator("hxt");
		cargoPrmDO.setType("5");
		
		System.out.println(cargoPrmDAO.insert(cargoPrmDO));
	}
	
	@Test
	public void testDelete() {
		cargoPrmDAO.delete(6, "hxt1");
	}
	
}
