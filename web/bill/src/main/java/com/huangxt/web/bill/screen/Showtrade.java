/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.bill.screen;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.huangxt.biz.bill.ao.TradeAO;
import com.huangxt.biz.bill.constant.BillConst;
import com.huangxt.biz.bill.vo.TradeSearchParamVO;
import com.huangxt.common.lang.StringEscapeUtil;
import com.huangxt.web.bill.model.UserRoleModel;
import com.huangxt.web.bill.util.BillUserRoleUtil;
import com.huangxt.web.util.AuthUtil;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * showlist.java 的作用：帐务系统的进货卖货详细列表界面
 * @author huangxt - 2012-2-26 下午12:43:44
 */
public class Showtrade extends ScreenModule {
	private TradeAO tradeAO = (TradeAO)getLogicClassIns("tradeAO");
	
	/** 日期格式化的格式 */
	private static final String Format = "yyyy-MM-dd HH:mm:ss";
	
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
		if( !model.isCanShowTrade() ) {
			context.put(BillConst.Result_Code_Key, BillConst.Result_Code_Role_Err);
			rundata.forward(BillConst.Err_Page);
			return ;
		}
		context.put("userRoleModel", model);
		
		TradeSearchParamVO tradeSearchParamVO = new TradeSearchParamVO();
		
		//设置查询入参
		int page = rundata.getIntParam("p", 1);
		if( (page < 1) || (page > 10000) ) {
			page = 1;
		}
		
		tradeSearchParamVO.setPage(page);
		tradeSearchParamVO.setAddress( rundata.getStringParam("addr", null) );
		tradeSearchParamVO.setOperator( rundata.getStringParam("operator", null) );
		tradeSearchParamVO.setTradeType( rundata.getStringParam("tradeType", null) );
		
		String begin = rundata.getStringParam("dateBegin", null);
		String end = rundata.getStringParam("dateEnd", null);
		
		try {
			if( begin != null ) {
				DateFormat format = new SimpleDateFormat(Format);
				Date d = format.parse(begin + " 00:00:01");
				tradeSearchParamVO.setDateBegin(d);
			}
		} catch(Throwable t) {
		}
		
		try {
			if( end != null ) {
				DateFormat format = new SimpleDateFormat(Format);
				Date d = format.parse(end + " 23:59:59");
				tradeSearchParamVO.setDateEnd(d);
			}
		} catch(Throwable t) {
		}
		
		//执行查询
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tradeSearchParamVO", tradeSearchParamVO);
		Map<String, Object> result = tradeAO.doShowHistoryList(param);
		context.put("maxPage", result.get("maxPage"));
		context.put("tradeMainVOList", result.get("tradeMainVOList"));
		
		//页面需要的其他东西
		context.put("tradeSearchParamVO", tradeSearchParamVO);
		context.put("user", AuthUtil.getUserName(rundata));
	}
}
