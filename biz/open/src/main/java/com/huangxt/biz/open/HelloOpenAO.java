/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.open;

import java.util.List;

import org.apache.log4j.Logger;

import com.huangxt.dal.daointerface.HelloDAO;
import com.huangxt.dal.dataobject.HelloDO;

/**
 * HelloAO.java 的作用：测试open package ok状态
 * @author huangxt - 2012-11-10 下午6:29:40
 */
public class HelloOpenAO {
	private static Logger log = Logger.getLogger(HelloOpenAO.class.getName());
	private HelloDAO helloDAO;
	
	public String getUserName() {
		log.warn("oh,yeah! log write success!");
		
		List<HelloDO> list = helloDAO.getHelloDOList();
		if( (list != null) && (list.size() > 0) ) {
			return list.get(0).getValue();
		} else {
			return "huangxt";
		}
	}

	public void setHelloDAO(HelloDAO helloDAO) {
		this.helloDAO = helloDAO;
	}
}
