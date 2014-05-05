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

import com.huangxt.biz.bill.constant.BillConst;
import com.huangxt.biz.bill.vo.OverviewSearchParamVO;
import com.huangxt.biz.bill.vo.OverviewVO;
import com.huangxt.dal.daointerface.bill.ItemDAO;
import com.huangxt.dal.daointerface.bill.OverviewDAO;
import com.huangxt.dal.dataobject.bill.ItemDO;
import com.huangxt.dal.dataobject.bill.OverviewDO;

/**
 * OverviewAO.java 的作用：库存货物总览相关的业务逻辑
 * @author huangxt - 2012-3-4 下午1:17:37
 */
public class OverviewAO {
	private static Logger log = Logger.getLogger(OverviewAO.class.getName());
	
	private ItemDAO itemDAO;
	private OverviewDAO overviewDAO;
	
	/**
	 * 库存货物总览列表的展示
	 */
	public Map<String, Object> doShowOverview(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//简单的合法校验和取得入参
		if( (param == null) || (param.get("overviewSearchParamVO") == null) ) {
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Param_Error);
			return result;
		}
		
		OverviewSearchParamVO paramVO = (OverviewSearchParamVO)param.get("overviewSearchParamVO");
		
		try {
			//先取得总共的条数，用于分页
			int count = overviewDAO.getOverviewSizeByCon(paramVO.getSize(), paramVO.getHeight(), paramVO.getGrade(), paramVO.getMaterial(), paramVO.getAddr());
			
			//如果取不到，则直接返回
			if( count == 0 ) {
				result.put("maxPage", 0);
				result.put("overviewVOList", null);
				result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Success);
				return result;
			}
			
			//计算最大页数，如果入参的页码超出最大范围，则重置
			int maxPage = (int)Math.ceil((double)count/BillConst.Page_Size);
			
			if( (paramVO.getPage()-1)*BillConst.Page_Size >= count ) {
				paramVO.setPage(maxPage);
			}
			
			//分页取得DO的list
			List<OverviewDO> doList = overviewDAO.getOverviewByCon(paramVO.getSize(), paramVO.getHeight(), paramVO.getGrade(), paramVO.getMaterial(), paramVO.getAddr(), (paramVO.getPage()-1)*BillConst.Page_Size, BillConst.Page_Size);
			
			//取得所有的item的id
			Set<Long> idsSet = new HashSet<Long>();
			for( OverviewDO overviewDO : doList ) {
				idsSet.add(overviewDO.getGrade());
				idsSet.add(overviewDO.getHeight());
				idsSet.add(overviewDO.getMaterial());
				idsSet.add(overviewDO.getSize());
			}
			
			//查找出item的id对应的文案
			List<ItemDO> itemDOsList = itemDAO.getItemListByIds(new ArrayList<Long>(idsSet));
			Map<Long,String> itemMap = new HashMap<Long,String>();
			for( ItemDO itemDO : itemDOsList ) {
				itemMap.put(itemDO.getId(), itemDO.getValue());
			}
			
			//由DO封装出VO
			List<OverviewVO> voList = new ArrayList<OverviewVO>();
			
			for( OverviewDO overviewDO : doList ) {
				OverviewVO vo = new OverviewVO();
				
				vo.setGrade(itemMap.get(overviewDO.getGrade()));
				vo.setHeight(itemMap.get(overviewDO.getHeight()));
				vo.setMaterial(itemMap.get(overviewDO.getMaterial()));
				vo.setSize(itemMap.get(overviewDO.getSize()));
				vo.setNum(overviewDO.getStock());
				vo.setAddr(overviewDO.getAddress());
				
				voList.add(vo);
			}
			
			result.put("maxPage", maxPage);
			result.put("overviewVOList", voList);
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Success);
		} catch(Throwable t) {
			log.error("OverviewAO.doShowOverview() error: ", t);
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Sys_Error);
		}
		
		return result;
	}
	
	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}
	public void setOverviewDAO(OverviewDAO overviewDAO) {
		this.overviewDAO = overviewDAO;
	}
}
