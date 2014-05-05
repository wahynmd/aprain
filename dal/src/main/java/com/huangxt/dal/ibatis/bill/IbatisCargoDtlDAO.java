/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.ibatis.bill;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.huangxt.dal.daointerface.bill.CargoDtlDAO;
import com.huangxt.dal.dataobject.bill.CargoDtlDO;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * IbatisCargoDtlDAO.java 的作用：
 * @author huangxt - 2012-3-3 下午3:26:16
 */
public class IbatisCargoDtlDAO extends SqlMapClientDaoSupport implements CargoDtlDAO {
	public void insert(final List<CargoDtlDO> cargoDtlDOList) {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				for( CargoDtlDO cargoDtlDO : cargoDtlDOList ) {
					executor.insert("MS-INSERT-CARGO-DTL", cargoDtlDO);
				}
				executor.executeBatch();
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<CargoDtlDO> getDtlsByPrmId(long prmId) {
		return (List<CargoDtlDO>) this.getSqlMapClientTemplate().queryForList("MS-QUERY-CARGO-DTLS-BY-ID", prmId);
	}
}
