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
import com.huangxt.biz.bill.vo.ReqDtlVO;
import com.huangxt.biz.bill.vo.ReqMainVO;
import com.huangxt.common.lang.Money;
import com.huangxt.dal.daointerface.bill.CargoDtlDAO;
import com.huangxt.dal.daointerface.bill.CargoPrmDAO;
import com.huangxt.dal.daointerface.bill.ItemDAO;
import com.huangxt.dal.daointerface.bill.OverviewDAO;
import com.huangxt.dal.daointerface.bill.ReqDtlDAO;
import com.huangxt.dal.daointerface.bill.ReqPrmDAO;
import com.huangxt.dal.dataobject.bill.CargoDtlDO;
import com.huangxt.dal.dataobject.bill.CargoPrmDO;
import com.huangxt.dal.dataobject.bill.ItemDO;
import com.huangxt.dal.dataobject.bill.OverviewDO;
import com.huangxt.dal.dataobject.bill.ReqDtlDO;
import com.huangxt.dal.dataobject.bill.ReqPrmDO;

/**
 * RequireAO.java 的作用：要货相关的业务逻辑
 * @author huangxt - 2012-3-4 下午1:17:15
 */
public class RequireAO {
	private static Logger log = Logger.getLogger(RequireAO.class.getName());
	
	private TransactionTemplate transactionTemplate;
	private ItemDAO itemDAO;
	private OverviewDAO overviewDAO;
	private ReqPrmDAO reqPrmDAO;
	private ReqDtlDAO reqDtlDAO;
	private CargoPrmDAO cargoPrmDAO;
	private CargoDtlDAO cargoDtlDAO;
	
	/**
	 * 添加一笔要货记录。
	 */
	public Map<String, Object> doAddReqRecord(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//简单的合法校验和取得入参
		if( (param == null) || (param.get("reqMainVO") == null) ) {
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Param_Error);
			return result;
		}
		ReqMainVO reqMainVO = (ReqMainVO)param.get("reqMainVO");
		
		try {
			if( (reqMainVO.getReqDtlVOList() == null) || (reqMainVO.getReqDtlVOList().size() == 0) ) {
				result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Param_Error);
				return result;
			}
			
			final ReqPrmDO reqPrmDO = new ReqPrmDO();
			
			reqPrmDO.setAddress(reqMainVO.getAddr());
			reqPrmDO.setComment(reqMainVO.getComment());
			reqPrmDO.setExpectTime(reqMainVO.getExpectTime());
			reqPrmDO.setName(reqMainVO.getName());
			reqPrmDO.setOperator(reqMainVO.getOperator());
			reqPrmDO.setState(BillConst.Req_State_Wait_Complete);
			reqPrmDO.setDetailAddr(reqMainVO.getDetailAddr());
			reqPrmDO.setPhone(reqMainVO.getPhone());
			if( !BillConst.Addr_XiaoKu.equals(reqMainVO.getAddr()) ) {
				reqPrmDO.setIsPay(reqMainVO.getIsPay());
			} else {
				reqPrmDO.setIsPay("");
			}
			
			final List<ReqDtlDO> reqDtlDOList = new ArrayList<ReqDtlDO>();
			
			for( ReqDtlVO vo : reqMainVO.getReqDtlVOList() ) {
				ReqDtlDO reqDtlDO = new ReqDtlDO();
				
				reqDtlDO.setGrade(vo.getGradeLong());
				reqDtlDO.setHeight(vo.getHeightLong());
				reqDtlDO.setMaterial(vo.getMaterialLong());
				reqDtlDO.setNum(vo.getNum());
				reqDtlDO.setPrice(vo.getPrice().getAmount().toString());
				reqDtlDO.setSize(vo.getSizeLong());
				reqDtlDO.setComment(vo.getComment());
				
				reqDtlDOList.add(reqDtlDO);
			}
			
			//插入数据库，并更新总览表的状态
			transactionTemplate.execute(
				new  TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						long mainId = reqPrmDAO.insert(reqPrmDO);
						
						for( ReqDtlDO reqDtlDO : reqDtlDOList) {
							reqDtlDO.setPrmId(mainId);
						}
						reqDtlDAO.insert(reqDtlDOList);
					}
				}
			);
			
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Success);
		} catch(Throwable t) {
			log.error("RequireAO.doAddReqRecord() error: ", t);
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Sys_Error);
		}
		
		return result;
	}
	
	/**
	 * 历史要货记录列表的展示
	 */
	public Map<String, Object> doShowHistoryList(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			int page = (Integer)param.get("page");
			
			if( page < 1 ) {
				page = 1;
			}
			
			//查询总条数，计算分页
			int count = reqPrmDAO.getAllWaitReqRecordCount();
			
			if( count == 0 ) {
				result.put("maxPage", 0);
				result.put("reqMainVOList", null);
				result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Success);
				return result;
			}
			
			int maxPage = (int)Math.ceil((double)count/BillConst.Page_Size);
			
			if( (page-1)*BillConst.Page_Size >= count ) {
				page = maxPage;
			}
			
			//根据入参条件，分页搜索历史交易明细记录
			List<ReqPrmDO> reqPrmDOList = reqPrmDAO.getAllWaitReqRecord((page-1)*BillConst.Page_Size, BillConst.Page_Size);
			
			//将DO的list转换成VO的list
			List<ReqMainVO> reqMainVOList = new ArrayList<ReqMainVO>();
			
			if( reqPrmDOList != null ) {
				for( ReqPrmDO reqPrmDO : reqPrmDOList ) {
					ReqMainVO vo = new ReqMainVO();
					
					vo.setAddr(reqPrmDO.getAddress());
					vo.setComment(reqPrmDO.getComment());
					vo.setGmtModify(reqPrmDO.getGmtModify());
					vo.setId(reqPrmDO.getId());
					vo.setName(reqPrmDO.getName());
					vo.setOperator(reqPrmDO.getOperator());
					vo.setExpectTime(reqPrmDO.getExpectTime());
					vo.setIsPay(reqPrmDO.getIsPay());
					
					reqMainVOList.add(vo);
				}
			}
			result.put("maxPage", maxPage);
			result.put("reqMainVOList", reqMainVOList);
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Success);
		} catch (Throwable t) {
			log.error("RequireAO.doShowHistoryList() error: ", t);
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Sys_Error);
		}
		
		return result;
	}
	
	/**
	 * 历史要货记录Detail的展示
	 */
	public Map<String, Object> doShowHistoryDtl(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//简单的合法校验和取得入参
		if( (param == null) || (param.get("reqMainId") == null) ) {
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Param_Error);
			return result;
		}
		
		Long reqMainId = (Long)param.get("reqMainId");
		
		try {
			//调用DAO将detail的每条信息查询出来
			List<ReqDtlDO> doList = reqDtlDAO.getDtlsByPrmId(reqMainId);
			
			if( (doList == null) || (doList.size() == 0) ) {
				result.put("reqDtlVOList", null);
				result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Success);
				return result;
			}
			
			//将itemId转换成具体的文案
			Set<Long> idsSet = new HashSet<Long>();
			for( ReqDtlDO reqDtlDO : doList ) {
				idsSet.add(reqDtlDO.getGrade());
				idsSet.add(reqDtlDO.getHeight());
				idsSet.add(reqDtlDO.getMaterial());
				idsSet.add(reqDtlDO.getSize());
			}
			
			List<ItemDO> itemDOsList = itemDAO.getItemListByIds(new ArrayList<Long>(idsSet));
			Map<Long,String> itemMap = new HashMap<Long,String>();
			for( ItemDO itemDO : itemDOsList ) {
				itemMap.put(itemDO.getId(), itemDO.getValue());
			}
			
			List<ReqDtlVO> reqDtlVOList = new ArrayList<ReqDtlVO>();
			for(ReqDtlDO reqDtlDO : doList ) {
				ReqDtlVO vo = new ReqDtlVO();
				
				vo.setPrice(new Money(reqDtlDO.getPrice()));
				vo.setNum(reqDtlDO.getNum());
				vo.setGrade(itemMap.get(reqDtlDO.getGrade()));
				vo.setHeight(itemMap.get(reqDtlDO.getHeight()));
				vo.setMaterial(itemMap.get(reqDtlDO.getMaterial()));
				vo.setSize(itemMap.get(reqDtlDO.getSize()));
				vo.setComment(reqDtlDO.getComment());
				
				reqDtlVOList.add(vo);
			}
			
			//再将对应的主表记录查询出来以便在页面上展示
			ReqMainVO vo = new ReqMainVO();
			ReqPrmDO reqPrmDO = reqPrmDAO.getReqRecord(reqMainId);
			
			vo.setAddr(reqPrmDO.getAddress());
			vo.setComment(reqPrmDO.getComment());
			vo.setGmtModify(reqPrmDO.getGmtModify());
			vo.setId(reqPrmDO.getId());
			vo.setName(reqPrmDO.getName());
			vo.setOperator(reqPrmDO.getOperator());
			vo.setExpectTime(reqPrmDO.getExpectTime());
			vo.setIsPay(reqPrmDO.getIsPay());
			vo.setDetailAddr(reqPrmDO.getDetailAddr());
			vo.setPhone(reqPrmDO.getPhone());
			
			result.put("reqPrmVO", vo);
			result.put("reqDtlVOList", reqDtlVOList);
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Success);
		} catch (Throwable t) {
			log.error("RequireAO.doShowHistoryDtl() error: ", t);
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Sys_Error);
		}
		
		return result;
	}
	
	/**
	 * 更新某一条要货记录的状态，比如：完成或者取消
	 */
	public Map<String, Object> doUpReqState(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//简单的合法校验和取得入参
		if( (param == null) || (param.get("reqMainId") == null) || (param.get("operator") == null) || (param.get("type") == null) ) {
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Param_Error);
			return result;
		}
		
		final Long reqMainId = (Long)param.get("reqMainId");
		final String operator = (String)param.get("operator");
		final String type = (String)param.get("type");	//操作类型：完成、取消
		
		try {
			if( BillConst.Req_State_Cancel.equals(type) ) {	//如果操作类型是取消某笔要货申请
				reqPrmDAO.updateState(reqMainId, BillConst.Req_State_Cancel, operator);
			} else if( BillConst.Req_State_Complete.equals(type) ) { //如果操作类型是完成某笔要货申请
				//首先确认要操作的这条记录是待完成状态的
				final ReqPrmDO reqPrmDO = reqPrmDAO.getReqRecord(reqMainId);
				
				if( !BillConst.Req_State_Wait_Complete.equals(reqPrmDO.getState()) ) {
					result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Param_Error);
					return result;
				}
				
				//取出该条记录的要货明细
				final List<ReqDtlDO> doList = reqDtlDAO.getDtlsByPrmId(reqMainId);
				
				if( (doList == null) || (doList.size() == 0) ) {
					result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Param_Error);
					return result;
				}
				
				//循环每一条明细，判断该品种的库存是否够
				for( ReqDtlDO reqDtlDO : doList ) {
					int count = overviewDAO.getOverviewSizeByCon( reqDtlDO.getSize(), reqDtlDO.getHeight(), reqDtlDO.getGrade(), reqDtlDO.getMaterial(), BillConst.Addr_DaKu );
					
					//如果库存的该条记录没有，则先insert
					if( count == 0 ) {
						overviewDAO.insertForFirstTime( reqDtlDO.getSize(), reqDtlDO.getHeight(), reqDtlDO.getGrade(), reqDtlDO.getMaterial(), BillConst.Addr_DaKu, 0 );
						result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Lack);
						return result;
					}
					
					//如果取出来发现有多条，则记录日志。
					if( count > 1 ) {
						log.error("RequireAO.doUpReqState() error: Overview exist more than one.");
						result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Sys_Error);
						return result;
					}
					
					List<OverviewDO> overviewDoList = overviewDAO.getOverviewByCon( reqDtlDO.getSize(), reqDtlDO.getHeight(), reqDtlDO.getGrade(), reqDtlDO.getMaterial(), BillConst.Addr_DaKu, 0, 5 );
					
					//如果库存记录有，但是不够，则返回提示错误
					if( overviewDoList.get(0).getStock() < reqDtlDO.getNum() ) {
						result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Lack);
						return result;
					}
				}
				
				//如果要货是发到小库的，则循环判断小库的该品种的总览记录是否存在，不存在的insert
				if( BillConst.Addr_XiaoKu.equals(reqPrmDO.getAddress()) ) {
					for( ReqDtlDO reqDtlDO : doList ) {
						int count = overviewDAO.getOverviewSizeByCon( reqDtlDO.getSize(), reqDtlDO.getHeight(), reqDtlDO.getGrade(), reqDtlDO.getMaterial(), BillConst.Addr_XiaoKu );
						
						if( count == 0 ) {
							overviewDAO.insertForFirstTime( reqDtlDO.getSize(), reqDtlDO.getHeight(), reqDtlDO.getGrade(), reqDtlDO.getMaterial(), BillConst.Addr_XiaoKu, 0 );
						}
					}
				}
				
				//如果库存都足够，则进入事务，先更新要货记录的状态，然后更新货物总览的数量(如果要货地点是小库要加以注意)
				transactionTemplate.execute(
					new  TransactionCallbackWithoutResult() {
						@Override
						protected void doInTransactionWithoutResult(TransactionStatus status) {
							//先更新要货主表的状态
							reqPrmDAO.updateState(reqMainId, BillConst.Req_State_Complete, operator);
							
							//如果要货的状态是“货站”或“库房提货”，那么更新大库的总览的数量，并且复制该份数据到交易表里
							if( BillConst.Addr_HuoYun.equals(reqPrmDO.getAddress()) || BillConst.Addr_TiHuo.equals(reqPrmDO.getAddress()) ) {
								List<OverviewDO> overviewDOList = new ArrayList<OverviewDO>();
								
								//循环更新大库的总览信息
								for( ReqDtlDO reqDtlDO : doList) {
									OverviewDO overviewDO = new OverviewDO();
									
									overviewDO.setAddress( BillConst.Addr_DaKu );
									overviewDO.setGrade(reqDtlDO.getGrade());
									overviewDO.setHeight(reqDtlDO.getHeight());
									overviewDO.setMaterial(reqDtlDO.getMaterial());
									overviewDO.setSize(reqDtlDO.getSize());
									overviewDO.setStock( 0-reqDtlDO.getNum() );
									
									overviewDOList.add(overviewDO);
								}
								overviewDAO.updateOverview(overviewDOList);
								
								//复制该份数据到交易表里
								CargoPrmDO cargoPrmDO = new CargoPrmDO();
								
								cargoPrmDO.setAddress(BillConst.Addr_DaKu);
								cargoPrmDO.setComment(reqPrmDO.getComment());
								cargoPrmDO.setIsDelete(BillConst.Trade_Record_Is_Delete_No);
								cargoPrmDO.setName(reqPrmDO.getName());
								cargoPrmDO.setOperator(operator);
								cargoPrmDO.setType(BillConst.Trade_Type_Sale);
								
								long id = cargoPrmDAO.insert(cargoPrmDO);
								
								List<CargoDtlDO> cargoDtlDOList = new ArrayList<CargoDtlDO>();
								
								for( ReqDtlDO reqDtlDO : doList ) {
									CargoDtlDO cargoDtlDO = new CargoDtlDO();
									
									cargoDtlDO.setPrmId(id);
									cargoDtlDO.setGrade(reqDtlDO.getGrade());
									cargoDtlDO.setHeight(reqDtlDO.getHeight());
									cargoDtlDO.setMaterial(reqDtlDO.getMaterial());
									cargoDtlDO.setSize(reqDtlDO.getSize());
									cargoDtlDO.setNum(reqDtlDO.getNum());
									cargoDtlDO.setPrice(reqDtlDO.getPrice());
									
									cargoDtlDOList.add(cargoDtlDO);
								}
								cargoDtlDAO.insert(cargoDtlDOList);
							} else if( BillConst.Addr_XiaoKu.equals(reqPrmDO.getAddress()) ) {
								//如果要货的状态是“小库”，那么更新大库和小库的总览的数量
								List<OverviewDO> overviewDOList = new ArrayList<OverviewDO>();
								
								//循环更新小库的总览信息
								for( ReqDtlDO reqDtlDO : doList) {
									OverviewDO overviewDO = new OverviewDO();
									
									overviewDO.setAddress( BillConst.Addr_XiaoKu );
									overviewDO.setGrade(reqDtlDO.getGrade());
									overviewDO.setHeight(reqDtlDO.getHeight());
									overviewDO.setMaterial(reqDtlDO.getMaterial());
									overviewDO.setSize(reqDtlDO.getSize());
									overviewDO.setStock(reqDtlDO.getNum());
									
									overviewDOList.add(overviewDO);
								}
								
								//循环更新大库的总览信息
								for( ReqDtlDO reqDtlDO : doList) {
									OverviewDO overviewDO = new OverviewDO();
									
									overviewDO.setAddress( BillConst.Addr_DaKu );
									overviewDO.setGrade(reqDtlDO.getGrade());
									overviewDO.setHeight(reqDtlDO.getHeight());
									overviewDO.setMaterial(reqDtlDO.getMaterial());
									overviewDO.setSize(reqDtlDO.getSize());
									overviewDO.setStock( 0-reqDtlDO.getNum() );
									
									overviewDOList.add(overviewDO);
								}
								
								overviewDAO.updateOverview(overviewDOList);
							}
						}
					}
				);
			} else {
				result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Param_Error);
				return result;
			}
			
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Success);
		} catch (Throwable t) {
			log.error("RequireAO.doUpReqState() error: ", t);
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
	public void setReqPrmDAO(ReqPrmDAO reqPrmDAO) {
		this.reqPrmDAO = reqPrmDAO;
	}
	public void setReqDtlDAO(ReqDtlDAO reqDtlDAO) {
		this.reqDtlDAO = reqDtlDAO;
	}
	public void setCargoPrmDAO(CargoPrmDAO cargoPrmDAO) {
		this.cargoPrmDAO = cargoPrmDAO;
	}
	public void setCargoDtlDAO(CargoDtlDAO cargoDtlDAO) {
		this.cargoDtlDAO = cargoDtlDAO;
	}
}
