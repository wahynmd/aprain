/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.ibatis.bill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.huangxt.dal.daointerface.bill.ItemDAO;
import com.huangxt.dal.dataobject.bill.ItemDO;

/**
 * IbatisItemDAO.java 的作用：
 * @author huangxt - 2012-3-3 下午3:23:07
 */
public class IbatisItemDAO extends SqlMapClientDaoSupport implements ItemDAO {
	
	@SuppressWarnings("unchecked")
	public List<ItemDO> getItemListByTypes(List<String> type) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("types", type);
		return (List<ItemDO>) this.getSqlMapClientTemplate().queryForList("MS-QUERY-BY-TYPE-LIST", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemDO> getItemListByIds(List<Long> ids) {
		Map<String, List<Long>> map = new HashMap<String, List<Long>>();
		map.put("ids", ids);
		return (List<ItemDO>) this.getSqlMapClientTemplate().queryForList("MS-QUERY-BY-ID-LIST", map);
	}
}
