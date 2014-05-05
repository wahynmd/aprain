/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.bill.ao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.huangxt.biz.bill.constant.BillConst;
import com.huangxt.biz.bill.vo.TradeDtlVO;
import com.huangxt.biz.bill.vo.TradeMainVO;
import com.huangxt.biz.bill.vo.TradeSearchParamVO;
import com.huangxt.common.lang.Money;
import com.huangxt.common.util.LogUtil;
import com.huangxt.dal.daointerface.bill.CargoDtlDAO;
import com.huangxt.dal.daointerface.bill.CargoPrmDAO;
import com.huangxt.dal.daointerface.bill.ItemDAO;
import com.huangxt.dal.daointerface.bill.OverviewDAO;
import com.huangxt.dal.dataobject.bill.CargoDtlDO;
import com.huangxt.dal.dataobject.bill.CargoPrmDO;
import com.huangxt.dal.dataobject.bill.ItemDO;
import com.huangxt.dal.dataobject.bill.OverviewDO;

/**
 * TradeAO.java 的作用：交易相关的业务逻辑，比如：进货、售货等。
 * @author huangxt - 2012-3-4 下午1:16:47
 */
public class TradeAO {
	private static Logger log = Logger.getLogger(TradeAO.class);
	
	private TransactionTemplate transactionTemplate;
	private ItemDAO itemDAO;
	private OverviewDAO overviewDAO;
	private CargoPrmDAO cargoPrmDAO;
	private CargoDtlDAO cargoDtlDAO;
	
	/**
	 * 添加一笔交易记录，比如：进货、售货。
	 */
	public Map<String, Object> doAddTradeRecord(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//简单的合法校验和取得入参
		if( (param == null) || (param.get("tradeMainVO") == null) ) {
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Param_Error);
			return result;
		}
		TradeMainVO tradeMainVO = (TradeMainVO)param.get("tradeMainVO");
		
		try {
			if( (tradeMainVO.getTradeDtlVOList() == null) || (tradeMainVO.getTradeDtlVOList().size() == 0) ) {
				result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Param_Error);
				return result;
			}
			
			for( TradeDtlVO vo : tradeMainVO.getTradeDtlVOList() ) {
				int count = overviewDAO.getOverviewSizeByCon( vo.getSizeLong(), vo.getHeightLong(), vo.getGradeLong(), vo.getMaterialLong(), tradeMainVO.getAddr() );
				
				//如果库存的该条记录没有，则先insert
				if( count == 0 ) {
					overviewDAO.insertForFirstTime( vo.getSizeLong(), vo.getHeightLong(), vo.getGradeLong(), vo.getMaterialLong(), tradeMainVO.getAddr(), 0 );
				}
				
				//如果取出来发现有多条，则记录日志。
				if( count > 1 ) {
					LogUtil.logicErr("TradeAO.doAddTradeRecord() error: Overview exist more than one. Detail is " + vo.toString());
					result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Sys_Error);
					return result;
				}
				
				List<OverviewDO> doList = overviewDAO.getOverviewByCon( vo.getSizeLong(), vo.getHeightLong(), vo.getGradeLong(), vo.getMaterialLong(), tradeMainVO.getAddr(), 0, 5 );
				
				//如果操作类型为售货，那么要经过额外的流程：如果库存记录有，但是不够，则返回提示错误
				if( BillConst.Trade_Type_Sale.equals(tradeMainVO.getType()) ) {
					if( doList.get(0).getStock() < vo.getNum() ) {
						result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Lack);
						return result;
					}
				}
			}
			
			
			final CargoPrmDO cargoPrmDO = new CargoPrmDO();
			
			cargoPrmDO.setAddress(tradeMainVO.getAddr());
			cargoPrmDO.setComment(tradeMainVO.getComment());
			cargoPrmDO.setIsDelete(BillConst.Trade_Record_Is_Delete_No);
			cargoPrmDO.setName(tradeMainVO.getName());
			cargoPrmDO.setOperator(tradeMainVO.getOperator());
			cargoPrmDO.setType(tradeMainVO.getType());
			
			final List<CargoDtlDO> cargoDtlDOList = new ArrayList<CargoDtlDO>();
			
			for( TradeDtlVO vo : tradeMainVO.getTradeDtlVOList() ) {
				CargoDtlDO cargoDtlDO = new CargoDtlDO();
				
				cargoDtlDO.setGrade(vo.getGradeLong());
				cargoDtlDO.setHeight(vo.getHeightLong());
				cargoDtlDO.setMaterial(vo.getMaterialLong());
				cargoDtlDO.setNum(vo.getNum());
				cargoDtlDO.setPrice(vo.getPrice().getAmount().toString());
				cargoDtlDO.setSize(vo.getSizeLong());
				
				cargoDtlDOList.add(cargoDtlDO);
			}
			
			//插入数据库，并更新总览表的状态
			transactionTemplate.execute(
				new  TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						long mainId = cargoPrmDAO.insert(cargoPrmDO);
						
						List<OverviewDO> overviewDOList = new ArrayList<OverviewDO>();
						
						for( CargoDtlDO cargoDtlDO : cargoDtlDOList) {
							cargoDtlDO.setPrmId(mainId);
							
							OverviewDO overviewDO = new OverviewDO();
							overviewDO.setAddress(cargoPrmDO.getAddress());
							overviewDO.setGrade(cargoDtlDO.getGrade());
							overviewDO.setHeight(cargoDtlDO.getHeight());
							overviewDO.setMaterial(cargoDtlDO.getMaterial());
							overviewDO.setSize(cargoDtlDO.getSize());
							
							if( BillConst.Trade_Type_Sale.equals(cargoPrmDO.getType()) ) {
								overviewDO.setStock( 0-cargoDtlDO.getNum() );
							} else {
								overviewDO.setStock(cargoDtlDO.getNum());
							}
							
							overviewDOList.add(overviewDO);
						}
						
						overviewDAO.updateOverview(overviewDOList);
						cargoDtlDAO.insert(cargoDtlDOList);
					}
				}
			);
			
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Success);
		} catch(Throwable t) {
			log.error("TradeAO.doAddTradeRecord() error: ", t);
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Sys_Error);
		}
		
		return result;
	}
	
	/**
	 * 历史交易记录列表的展示
	 */
	public Map<String, Object> doShowHistoryList(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//简单的合法校验和取得入参
		if( (param == null) || (param.get("tradeSearchParamVO") == null) ) {
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Param_Error);
			return result;
		}
		TradeSearchParamVO tradeSearchParamVO = (TradeSearchParamVO)param.get("tradeSearchParamVO");
		
		try {
			//查询总条数，计算分页
			int count = cargoPrmDAO.getTradeListSizeByCon(tradeSearchParamVO.getOperator(), tradeSearchParamVO.getAddress(), tradeSearchParamVO.getTradeType(), tradeSearchParamVO.getDateBegin(), tradeSearchParamVO.getDateEnd());
			
			if( count == 0 ) {
				result.put("maxPage", 0);
				result.put("tradeMainVOList", null);
				result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Success);
				return result;
			}
			
			int maxPage = (int)Math.ceil((double)count/BillConst.Page_Size);
			
			if( (tradeSearchParamVO.getPage()-1)*BillConst.Page_Size >= count ) {
				tradeSearchParamVO.setPage(maxPage);
			}
			
			//根据入参条件，分页搜索历史交易明细记录
			List<CargoPrmDO> cargoPrmDOList = cargoPrmDAO.getTradeListByCon(tradeSearchParamVO.getOperator(), tradeSearchParamVO.getAddress(), tradeSearchParamVO.getTradeType(), tradeSearchParamVO.getDateBegin(), tradeSearchParamVO.getDateEnd(), (tradeSearchParamVO.getPage()-1)*BillConst.Page_Size, BillConst.Page_Size);
			
			//将DO的list转换成VO的list
			List<TradeMainVO> tradeMainVOList = new ArrayList<TradeMainVO>();
			
			for( CargoPrmDO cargoPrmDO : cargoPrmDOList ) {
				TradeMainVO vo = new TradeMainVO();
				
				vo.setAddr(cargoPrmDO.getAddress());
				vo.setComment(cargoPrmDO.getComment());
				vo.setGmtCreate(cargoPrmDO.getGmtCreate());
				vo.setId(cargoPrmDO.getId());
				vo.setName(cargoPrmDO.getName());
				vo.setOperator(cargoPrmDO.getOperator());
				vo.setType(cargoPrmDO.getType());
				
				tradeMainVOList.add(vo);
			}
			
			result.put("maxPage", maxPage);
			result.put("tradeMainVOList", tradeMainVOList);
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Success);
		} catch (Throwable t) {
			log.error("TradeAO.doShowHistoryList() error: ", t);
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Sys_Error);
		}
		
		return result;
	}
	
	/**
	 * 历史交易记录Detail的展示
	 */
	public Map<String, Object> doShowHistoryDtl(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//简单的合法校验和取得入参
		if( (param == null) || (param.get("tradeMainId") == null) ) {
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Param_Error);
			return result;
		}
		
		Long tradeMainId = (Long)param.get("tradeMainId");
		
		try {
			//调用DAO将detail的每条信息查询出来
			List<CargoDtlDO> doList = cargoDtlDAO.getDtlsByPrmId(tradeMainId);
			
			if( (doList == null) || (doList.size() == 0) ) {
				result.put("tradeDtlVOList", null);
				result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Success);
				return result;
			}
			
			//将itemId转换成具体的文案
			Set<Long> idsSet = new HashSet<Long>();
			for( CargoDtlDO cargoDtlDO : doList ) {
				idsSet.add(cargoDtlDO.getGrade());
				idsSet.add(cargoDtlDO.getHeight());
				idsSet.add(cargoDtlDO.getMaterial());
				idsSet.add(cargoDtlDO.getSize());
			}
			
			List<ItemDO> itemDOsList = itemDAO.getItemListByIds(new ArrayList<Long>(idsSet));
			Map<Long,String> itemMap = new HashMap<Long,String>();
			for( ItemDO itemDO : itemDOsList ) {
				itemMap.put(itemDO.getId(), itemDO.getValue());
			}
			
			List<TradeDtlVO> tradeDtlVOList = new ArrayList<TradeDtlVO>();
			for(CargoDtlDO cargoDtlDO : doList ) {
				TradeDtlVO vo = new TradeDtlVO();
				
				vo.setPrice(new Money(cargoDtlDO.getPrice()));
				vo.setNum(cargoDtlDO.getNum());
				vo.setGrade(itemMap.get(cargoDtlDO.getGrade()));
				vo.setHeight(itemMap.get(cargoDtlDO.getHeight()));
				vo.setMaterial(itemMap.get(cargoDtlDO.getMaterial()));
				vo.setSize(itemMap.get(cargoDtlDO.getSize()));
				
				tradeDtlVOList.add(vo);
			}
			
			CargoPrmDO cargoPrmDO = cargoPrmDAO.getById(tradeMainId);
			
			TradeMainVO vo = new TradeMainVO();
			vo.setAddr(cargoPrmDO.getAddress());
			vo.setComment(cargoPrmDO.getComment());
			vo.setGmtCreate(cargoPrmDO.getGmtCreate());
			vo.setName(cargoPrmDO.getName());
			vo.setOperator(cargoPrmDO.getOperator());
			vo.setType(cargoPrmDO.getType());
			
			result.put("tradePrmVO", vo);
			result.put("tradeDtlVOList", tradeDtlVOList);
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Success);
		} catch (Throwable t) {
			log.error("TradeAO.doShowHistoryDtl() error: ", t);
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Sys_Error);
		}
		
		return result;
	}
	
	/**
	 * 删除某一条历史交易记录
	 */
	public Map<String, Object> doDelTradeRecord(final Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//简单的合法校验和取得入参
		if( (param == null) || (param.get("tradeMainId") == null) || (param.get("operator") == null) ) {
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Param_Error);
			return result;
		}
		
		final Long mainId = (Long)param.get("tradeMainId");
		
		try {
			//根据主表id查询出附属的detail列表以及主表信息
			final List<CargoDtlDO> doList = cargoDtlDAO.getDtlsByPrmId(mainId);
			final CargoPrmDO prmDO = cargoPrmDAO.getById(mainId);
			
			//根据不同类型进行判断，如果类型是进货的话，回滚要进行减操作，所以要判断现有库存是否足够
			if( BillConst.Trade_Type_Buy.equals(prmDO.getType()) ) {
				for( CargoDtlDO cargoDtlDO : doList ) {
					int count = overviewDAO.getOverviewSizeByCon( cargoDtlDO.getSize(), cargoDtlDO.getHeight(), cargoDtlDO.getGrade(), cargoDtlDO.getMaterial(), prmDO.getAddress() );
					
					if( count == 1 ) {
						List<OverviewDO> overviewDOList = overviewDAO.getOverviewByCon( cargoDtlDO.getSize(), cargoDtlDO.getHeight(), cargoDtlDO.getGrade(), cargoDtlDO.getMaterial(), prmDO.getAddress(), 0, 5 );
						
						if( overviewDOList.get(0).getStock() < cargoDtlDO.getNum() ) {
							result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Lack);
							return result;
						}
					} else {
						LogUtil.logicErr("Delete one Trade record error, tradeMainId is " + mainId);
						result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Sys_Error);
						return result;
					}
				}
			}
			
			//如果经过上述校验并合法的话，下面就进入事务，更新主表状态、更新预览表的数据
			transactionTemplate.execute(
				new  TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						cargoPrmDAO.delete(mainId, (String)param.get("operator"));
						
						List<OverviewDO> overviewDOList = new ArrayList<OverviewDO>(doList.size());
						for( CargoDtlDO cargoDtlDO : doList) {
							OverviewDO overviewDO = new OverviewDO();
							
							overviewDO.setAddress(prmDO.getAddress());
							overviewDO.setGrade(cargoDtlDO.getGrade());
							overviewDO.setHeight(cargoDtlDO.getHeight());
							overviewDO.setMaterial(cargoDtlDO.getMaterial());
							overviewDO.setSize(cargoDtlDO.getSize());
							
							if( BillConst.Trade_Type_Sale.equals(prmDO.getType()) ) {
								overviewDO.setStock( cargoDtlDO.getNum() );
							} else {
								overviewDO.setStock( 0-cargoDtlDO.getNum() );
							}
							
							overviewDOList.add(overviewDO);
						}
						
						overviewDAO.updateOverview(overviewDOList);
					}
				}
			);
			
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Success);
		} catch (Throwable t) {
			log.error("TradeAO.doShowHistoryDtl() error: ", t);
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Sys_Error);
		}
		
		return result;
	}
	
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}
	public void setOverviewDAO(OverviewDAO overviewDAO) {
		this.overviewDAO = overviewDAO;
	}
	public void setCargoPrmDAO(CargoPrmDAO cargoPrmDAO) {
		this.cargoPrmDAO = cargoPrmDAO;
	}
	public void setCargoDtlDAO(CargoDtlDAO cargoDtlDAO) {
		this.cargoDtlDAO = cargoDtlDAO;
	}
}
