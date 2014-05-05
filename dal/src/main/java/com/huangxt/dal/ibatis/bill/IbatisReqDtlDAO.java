/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.ibatis.bill;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.huangxt.dal.daointerface.bill.ReqDtlDAO;
import com.huangxt.dal.dataobject.bill.ReqDtlDO;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * IbatisReqDtlDAO.java 的作用：
 * @author huangxt - 2012-3-3 下午3:28:10
 */
public class IbatisReqDtlDAO extends SqlMapClientDaoSupport implements ReqDtlDAO {
	public void insert(final List<ReqDtlDO> reqDtlDOList) {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				for( ReqDtlDO reqDtlDO : reqDtlDOList ) {
					executor.insert("MS-INSERT-REQ-DTL", reqDtlDO);
				}
				executor.executeBatch();
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<ReqDtlDO> getDtlsByPrmId(long prmId) {
		return (List<ReqDtlDO>) this.getSqlMapClientTemplate().queryForList("MS-QUERY-REQ-DTLS-BY-ID", prmId);
	}
}
