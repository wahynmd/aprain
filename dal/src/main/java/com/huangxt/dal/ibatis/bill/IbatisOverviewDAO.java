/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.ibatis.bill;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.huangxt.dal.daointerface.bill.OverviewDAO;
import com.huangxt.dal.dataobject.bill.OverviewDO;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * IbatisOverviewDAO.java 的作用：
 * @author huangxt - 2012-3-3 下午3:23:58
 */
public class IbatisOverviewDAO extends SqlMapClientDaoSupport implements OverviewDAO {
	@SuppressWarnings("unchecked")
	public List<OverviewDO> getOverviewByCon(Long sz, Long h, Long grd, Long mtrl, String addr, int start, int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("size", sz);
		map.put("height", h);
		map.put("grade", grd);
		map.put("material", mtrl);
		map.put("address", addr);
		map.put("start", start);
		map.put("pageSize", pageSize);
		
		return (List<OverviewDO>) this.getSqlMapClientTemplate().queryForList("MS-QUERY-BY-CON", map);
	}
	
	public int getOverviewSizeByCon(Long sz, Long h, Long grd, Long mtrl, String addr) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("size", sz);
		map.put("height", h);
		map.put("grade", grd);
		map.put("material", mtrl);
		map.put("address", addr);
		
		return (Integer)this.getSqlMapClientTemplate().queryForObject("MS-COUNT-BY-CON", map);
	}
	
	public void updateOverview(final List<OverviewDO> doList) {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				for( OverviewDO overviewDO : doList ) {
					executor.update("MS-UPDATE-OVERVIEW", overviewDO);
				}
				executor.executeBatch();
				return null;
			}
		});
	}
	
	public long insertForFirstTime(long size, long height, long grade, long material, String addr, int stock) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("size", size);
		map.put("height", height);
		map.put("grade", grade);
		map.put("material", material);
		map.put("address", addr);
		map.put("stock", stock);
		
		return (Long)this.getSqlMapClientTemplate().insert("MS-INSERT-OVERVIEW", map);
	}
}
