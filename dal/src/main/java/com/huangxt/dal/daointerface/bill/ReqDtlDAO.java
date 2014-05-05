/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.daointerface.bill;

import java.util.List;

import com.huangxt.dal.dataobject.bill.ReqDtlDO;

/**
 * ReqDtlDAO.java 的作用：某笔要货的详细记录
 * @author huangxt - 2012-3-3 下午3:20:58
 */
public interface ReqDtlDAO {
	/** 添加一笔要货的详细记录 */
	public void insert(List<ReqDtlDO> reqDtlDOList);
	/** 根据主表id查询一笔要货的详细记录 */
	public List<ReqDtlDO> getDtlsByPrmId(long prmId);
}
