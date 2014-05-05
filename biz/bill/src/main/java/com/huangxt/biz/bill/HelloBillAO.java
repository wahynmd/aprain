/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.bill;

import java.util.List;

import org.apache.log4j.Logger;

import com.huangxt.dal.daointerface.HelloDAO;
import com.huangxt.dal.dataobject.HelloDO;

/**
 * HelloBillAO.java 的作用：测试应用ok状态
 * @author huangxt - 2012-2-11 下午6:29:40
 */
public class HelloBillAO {
	private static Logger log = Logger.getLogger(HelloBillAO.class.getName());
	private HelloDAO helloDAO;
	
	public String getUserName() {
		log.warn("bill log write success!");
		
		List<HelloDO> list = helloDAO.getHelloDOList();
		if( (list != null) && (list.size() > 0) ) {
			return list.get(0).getValue();
		} else {
			return "huangxt's bill system";
		}
	}

	public void setHelloDAO(HelloDAO helloDAO) {
		this.helloDAO = helloDAO;
	}
}
