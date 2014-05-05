/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.daointerface.bill;

import java.util.List;
import com.huangxt.dal.dataobject.bill.OverviewDO;

/**
 * OverviewDAO.java 的作用：每个品种货物的总览
 * @author huangxt - 2012-3-3 下午3:14:02
 */
public interface OverviewDAO {
	/** 根据条件查询货物总览信息，如果入参为null表示无此参数条件 */
	public List<OverviewDO> getOverviewByCon(Long sz, Long h, Long grd, Long mtrl, String addr, int start, int pageSize);
	
	/** 根据条件查询货物总览信息的总条数，用于分页，如果入参为null表示无此参数条件 */
	public int getOverviewSizeByCon(Long sz, Long h, Long grd, Long mtrl, String addr);
	
	/** 根据条件批量更新货物的总览信息 */
	public void updateOverview(List<OverviewDO> doList);
	
	/** 第一次更新货物总览信息时，要先插入该条记录 */
	public long insertForFirstTime(long size, long height, long grade, long material, String addr, int stock);
}
