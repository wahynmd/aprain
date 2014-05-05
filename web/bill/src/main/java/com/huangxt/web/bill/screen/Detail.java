/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.bill.screen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.huangxt.biz.bill.ao.RequireAO;
import com.huangxt.biz.bill.ao.TradeAO;
import com.huangxt.biz.bill.constant.BillConst;
import com.huangxt.common.lang.StringEscapeUtil;
import com.huangxt.web.util.AuthUtil;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Detail.java 的作用：帐务系统的每笔交易或要货的详情页面
 * @author huangxt - 2012-2-26 下午12:46:21
 */
public class Detail extends ScreenModule {
	private RequireAO requireAO = (RequireAO)getLogicClassIns("requireAO");
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
		
		int type = rundata.getIntParam("type", -1);
		long id = rundata.getLongParam("id", -1L);
		
		//类型1：要货明细；类型2：交易明细。
		if( (id == -1) || ((type != 1) && (type != 2)) ) {
			return ;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> result;
		if( type == 1 ) {
			param.put("reqMainId", id);
			result = requireAO.doShowHistoryDtl(param);
			context.put("reqDtlVOList", result.get("reqDtlVOList"));
			context.put("reqPrmVO", result.get("reqPrmVO"));
		} else {
			param.put("tradeMainId", id);
			result = tradeAO.doShowHistoryDtl(param);
			context.put("tradeDtlVOList", result.get("tradeDtlVOList"));
			context.put("tradePrmVO", result.get("tradePrmVO"));
		}
		
		if( !BillConst.Result_Code_Success.equals(result.get(BillConst.Result_Code_Key)) ) {
			context.put(BillConst.Result_Code_Key, result.get(BillConst.Result_Code_Key));
		}
		
		context.put("type", type);
		context.put("user", AuthUtil.getUserName(rundata));
	}
}
