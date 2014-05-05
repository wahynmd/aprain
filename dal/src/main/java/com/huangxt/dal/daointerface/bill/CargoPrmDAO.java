/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.daointerface.bill;

import java.util.Date;
import java.util.List;

import com.huangxt.dal.dataobject.bill.CargoPrmDO;

/**
 * CargoPrmDAO.java 的作用：每一笔进货、卖货交易
 * @author huangxt - 2012-3-3 下午3:17:59
 */
public interface CargoPrmDAO {
	/** 根据条件查询交易记录 */
	public List<CargoPrmDO> getTradeListByCon(String optor, String addr, String type, Date b, Date e, int start, int pageSize);
	
	/** 根据条件查询交易记录的条数，用于分页 */
	public int getTradeListSizeByCon(String optor, String addr, String type, Date b, Date e);
	
	/** 插入一笔交易记录 */
	public long insert(CargoPrmDO cargoPrmDO);
	
	/** 删除某一笔交易记录（即：更新该条记录的is_delete字段） */
	public void delete(long id, String operator);
	
	/** 根据主键查询记录 */
	public CargoPrmDO getById(Long id);
}
