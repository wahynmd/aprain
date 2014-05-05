/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.daointerface.bill;

import java.util.List;
import com.huangxt.dal.dataobject.bill.ItemDO;

/**
 * ItemDAO.java 的作用：枚举值
 * @author huangxt - 2012-3-3 下午3:16:07
 */
public interface ItemDAO {
	/** 根据类型的list批量获取所有枚举值模型 */
	public List<ItemDO> getItemListByTypes(List<String> type);
	/** 根据主键list得到所有的枚举值模型 */
	public List<ItemDO> getItemListByIds(List<Long> ids);
}
