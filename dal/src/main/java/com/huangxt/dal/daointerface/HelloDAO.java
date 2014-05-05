/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.daointerface;

import java.util.List;

import com.huangxt.dal.dataobject.HelloDO;

/**
 * HelloDAO.java 的作用：应用ok状态DAO
 * @author huangxt - 2012-2-18 下午1:43:17
 */
public interface HelloDAO {

	/** 取得数据库ok表里的所有记录 */
	public List<HelloDO> getHelloDOList();
}
