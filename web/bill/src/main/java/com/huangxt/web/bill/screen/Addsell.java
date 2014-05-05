/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.bill.screen;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huangxt.biz.bill.ao.CommonAO;
import com.huangxt.biz.bill.ao.TradeAO;
import com.huangxt.biz.bill.constant.BillConst;
import com.huangxt.biz.bill.vo.TradeDtlVO;
import com.huangxt.biz.bill.vo.TradeMainVO;
import com.huangxt.common.lang.StringEscapeUtil;
import com.huangxt.web.bill.model.UserRoleModel;
import com.huangxt.web.bill.util.BillUserRoleUtil;
import com.huangxt.web.util.AuthUtil;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Sell.java 的作用：帐务系统录入卖货信息
 * @author huangxt - 2012-2-25 下午2:37:33
 */
public class Addsell extends ScreenModule {
	private TradeAO tradeAO = (TradeAO)getLogicClassIns("tradeAO");
	private CommonAO commonAO = (CommonAO)getLogicClassIns("commonAO");
	
	public void execute(RunData rundata, Map<String, Object> context) {
		if( !AuthUtil.isLogin(rundata) ) {
			try {
				String target = StringEscapeUtil.escapeURL(rundata.getUrlWithoutQueryString());
				rundata.sendRedirect(rundata.getUriBroker().get("login_page") + "?target=" + target);
			} catch (IOException e) {
			}
			return ;
		}
		
		UserRoleModel model = BillUserRoleUtil.getUserRoleModel(AuthUtil.getUserName(rundata));
		if( !model.isCanAddSell() ) {
			context.put(BillConst.Result_Code_Key, BillConst.Result_Code_Role_Err);
			rundata.forward(BillConst.Err_Page);
			return ;
		}
		context.put("userRoleModel", model);
		context.put("user", AuthUtil.getUserName(rundata));
		
		//如果执行的是action的逻辑
		if( rundata.getStringParam("isAction", null) != null ) {
			List<TradeDtlVO> dtlVOList = Addbuy.getDtlVOFromJson( rundata.getStringParam("detailJson", null) );
			if( (dtlVOList == null) || (dtlVOList.size() == 0) ) {
				return ;
			}
			
			TradeMainVO mainVO = new TradeMainVO();
			
			mainVO.setTradeDtlVOList(dtlVOList);
			mainVO.setOperator(AuthUtil.getUserName(rundata));
			mainVO.setType(BillConst.Trade_Type_Sale);
			mainVO.setComment( rundata.getStringParam("comment", null) );
			mainVO.setAddr( rundata.getStringParam("addr", null) );
			mainVO.setName( rundata.getStringParam("name", null) );
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("tradeMainVO", mainVO);
			Map<String, Object> result = tradeAO.doAddTradeRecord(param);
			
			if( BillConst.Result_Code_Success.equals(result.get(BillConst.Result_Code_Key)) ) {
				try {
					rundata.sendRedirect(rundata.getUriBroker().get("bill_show_trade_page"));
				} catch (IOException e) {
				}
			} else {
				context.put(BillConst.Result_Code_Key, result.get(BillConst.Result_Code_Key));
				rundata.forward(BillConst.Err_Page);
			}
		} else {
			//取得item的对应关系，给页面展示
			Map<String, Object> itemResult = commonAO.doGetItems(new HashMap<String, Object>());
			context.put("itemDOList", itemResult.get("itemDOList"));
		}
	}
}
