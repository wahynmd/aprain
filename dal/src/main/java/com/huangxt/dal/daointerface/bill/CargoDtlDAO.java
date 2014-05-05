/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.daointerface.bill;

import java.util.List;

import com.huangxt.dal.dataobject.bill.CargoDtlDO;

/**
 * CargoDtlDAO.java 的作用：某笔交易的详细记录
 * @author huangxt - 2012-3-3 下午3:18:42
 */
public interface CargoDtlDAO {
	/** 添加一笔交易的详细记录 */
	public void insert(List<CargoDtlDO> cargoDtlDOList);
	/** 根据主表id查询一笔交易的详细记录 */
	public List<CargoDtlDO> getDtlsByPrmId(long prmId);
}
