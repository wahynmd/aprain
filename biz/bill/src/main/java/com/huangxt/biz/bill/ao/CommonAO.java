/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.bill.ao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.huangxt.biz.bill.constant.BillConst;
import com.huangxt.dal.daointerface.bill.ItemDAO;
import com.huangxt.dal.dataobject.bill.ItemDO;

/**
 * CommonAO.java 的作用：一些通用的AO逻辑方法
 * @author huangxt - 2012-3-18 下午2:28:21
 */
public class CommonAO {
	private static Logger log = Logger.getLogger(CommonAO.class);
	
	private ItemDAO itemDAO;

	/** 获取指定的item的集合，并返回分类的集合，如果没有传入指定的item的list，则返回所有的 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> doGetItems(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			List<String> itemTypeList = (List<String>)param.get("itemTypeList");
			
			if( (itemTypeList == null) || (itemTypeList.size() == 0) ) {
				itemTypeList = new ArrayList<String>();
				
				itemTypeList.add(BillConst.Item_Type_Grade);
				itemTypeList.add(BillConst.Item_Type_Size);
				itemTypeList.add(BillConst.Item_Type_Material);
				itemTypeList.add(BillConst.Item_Type_Height);
			}
			
			List<ItemDO> itemDOList = itemDAO.getItemListByTypes(itemTypeList);
			result.put("itemDOList", itemDOList);
		} catch(Throwable t) {
			log.error("CommonAO.doGetItems() error: ", t);
			result.put(BillConst.Result_Code_Key, BillConst.Result_Code_Sys_Error);
		}
		
		return result;
	}
	
	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}
}
