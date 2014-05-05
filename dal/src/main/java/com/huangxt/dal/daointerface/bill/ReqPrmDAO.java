/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.daointerface.bill;

import java.util.List;

import com.huangxt.dal.dataobject.bill.ReqPrmDO;

/**
 * ReqPrmDAO.java 的作用：每一笔要货记录
 * @author huangxt - 2012-3-3 下午3:20:05
 */
public interface ReqPrmDAO {
	/** 添加一笔要货记录 */
	public long insert(ReqPrmDO reqPrmDO);
	/** 更新一笔要货的状态 */
	public void updateState(long id, String state, String operator);
	/** 取得分页的未完成状态的要货记录 */
	public List<ReqPrmDO> getAllWaitReqRecord(int start, int pageSize);
	/** 取得分页的未完成状态的要货记录的数量，用于分页 */
	public int getAllWaitReqRecordCount();
	/** 根据主键查找某笔要货的主表记录 */
	public ReqPrmDO getReqRecord(long id);
}
