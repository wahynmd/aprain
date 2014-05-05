/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.ibatis.bill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.huangxt.dal.daointerface.bill.ReqPrmDAO;
import com.huangxt.dal.dataobject.bill.ReqPrmDO;

/**
 * IbatisReqPrmDAO.java 的作用：
 * @author huangxt - 2012-3-3 下午3:27:17
 */
public class IbatisReqPrmDAO extends SqlMapClientDaoSupport implements ReqPrmDAO {
	public long insert(ReqPrmDO reqPrmDO) {
		return (Long)this.getSqlMapClientTemplate().insert("MS-INSERT-REQ-PRM", reqPrmDO);
	}
	
	public void updateState(long id, String state, String operator) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("operator", operator);
		map.put("state", state);
		
		this.getSqlMapClientTemplate().update("MS-UP-REQ-PRM-STATE", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<ReqPrmDO> getAllWaitReqRecord(int start, int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("pageSize", pageSize);
		
		return (List<ReqPrmDO>)this.getSqlMapClientTemplate().queryForList("MS-QUERY-ALL-WAIT-REQ-PRM", map);
	}
	
	public int getAllWaitReqRecordCount() {
		return (Integer)this.getSqlMapClientTemplate().queryForObject("MS-COUNT-ALL-WAIT-REQ-PRM");
	}
	
	public ReqPrmDO getReqRecord(long id) {
		return (ReqPrmDO)this.getSqlMapClientTemplate().queryForObject("MS-QUERY-REQ-PRM-BY-ID", id);
	}
}
