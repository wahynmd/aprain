/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.bill.screen.operate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.huangxt.biz.bill.ao.TradeAO;
import com.huangxt.biz.bill.constant.BillConst;
import com.huangxt.common.lang.StringEscapeUtil;
import com.huangxt.web.bill.util.BillUserRoleUtil;
import com.huangxt.web.util.AuthUtil;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Delete.java 的作用：帐务系统删除一条进货、卖货记录
 * @author huangxt - 2012-2-26 下午12:52:10
 */
public class Uptrade extends ScreenModule {
	private TradeAO tradeAO = (TradeAO)getLogicClassIns("tradeAO");
	
	@Override
	public void execute(RunData rundata, Map<String, Object> context) {
		if( !AuthUtil.isLogin(rundata) ) {
			try {
				String target = StringEscapeUtil.escapeURL(rundata.getUrlWithoutQueryString());
				rundata.sendRedirect(rundata.getUriBroker().get("login_page") + "?target=" + target);
			} catch (IOException e) {
			}
			return ;
		}
		
		if( !BillUserRoleUtil.getUserRoleModel(AuthUtil.getUserName(rundata)).isCanDelTrade() ) {
			context.put(BillConst.Result_Code_Key, BillConst.Result_Code_Role_Err);
			rundata.forward(BillConst.Err_Page);
			return ;
		}
		
		long id = rundata.getLongParam("id", -1L);
		
		if( id < 1 ) {
			return ;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tradeMainId", id);
		param.put("operator", AuthUtil.getUserName(rundata));
		
		Map<String, Object> result = tradeAO.doDelTradeRecord(param);
		
		if( BillConst.Result_Code_Success.equals(result.get(BillConst.Result_Code_Key)) ) {
			try {
				rundata.sendRedirect(rundata.getUriBroker().get("bill_show_trade_page"));
			} catch (IOException e) {
			}
		} else {
			context.put(BillConst.Result_Code_Key, result.get(BillConst.Result_Code_Key));
			rundata.forward(BillConst.Err_Page);
		}
	}
}
