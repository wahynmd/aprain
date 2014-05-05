/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.huangxt.dal.daointerface.HelloDAO;
import com.huangxt.dal.dataobject.HelloDO;

/**
 * IbatisHelloDAO.java 的作用：
 * @author huangxt - 2012-2-18 下午1:55:38
 */
public class IbatisHelloDAO extends SqlMapClientDaoSupport implements HelloDAO {

	@SuppressWarnings("unchecked")
	public List<HelloDO> getHelloDOList() {
		return (List<HelloDO>)this.getSqlMapClientTemplate().queryForList("MS-QUERY-HELLO");
	}
}
