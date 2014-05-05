/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.ibatis.bill;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.huangxt.dal.daointerface.bill.CargoPrmDAO;
import com.huangxt.dal.dataobject.bill.CargoPrmDO;

/**
 * IbatisCargoPrmDAO.java 的作用：
 * @author huangxt - 2012-3-3 下午3:24:56
 */
public class IbatisCargoPrmDAO extends SqlMapClientDaoSupport implements CargoPrmDAO {
	
	@SuppressWarnings("unchecked")
	public List<CargoPrmDO> getTradeListByCon(String optor, String addr, String type, Date b, Date e, int start, int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("optor", optor);
		map.put("addr", addr);
		map.put("type", type);
		map.put("b", b);
		map.put("e", e);
		map.put("start", start);
		map.put("pageSize", pageSize);
		
		return (List<CargoPrmDO>) this.getSqlMapClientTemplate().queryForList("MS-QUERY-CARGO-PRM-BY-CON", map);
	}
	
	public int getTradeListSizeByCon(String optor, String addr, String type, Date b, Date e) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("optor", optor);
		map.put("addr", addr);
		map.put("type", type);
		map.put("b", b);
		map.put("e", e);
		
		return (Integer)this.getSqlMapClientTemplate().queryForObject("MS-COUNT-CARGO-PRM-BY-CON", map);
	}
	
	public long insert(CargoPrmDO cargoPrmDO) {
		Long id = (Long)this.getSqlMapClientTemplate().insert("MS-INSERT-CARGO-PRM", cargoPrmDO);
		return id;
	}
	
	public void delete(long id, String operator) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("operator", operator);
		
		this.getSqlMapClientTemplate().update("MS-DELETE-CARGO-PRM", map);
	}
	
	public CargoPrmDO getById(Long id) {
		return (CargoPrmDO)this.getSqlMapClientTemplate().queryForObject("MS-QUERY-CARGO-PRM-BY-ID", id);
	}
}
