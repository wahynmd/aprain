/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.bill.screen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.huangxt.biz.bill.ao.RequireAO;
import com.huangxt.biz.bill.constant.BillConst;
import com.huangxt.common.lang.StringEscapeUtil;
import com.huangxt.web.bill.model.UserRoleModel;
import com.huangxt.web.bill.util.BillUserRoleUtil;
import com.huangxt.web.util.AuthUtil;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Showrequest.java 的作用：帐务系统的展示要货列表界面
 * @author huangxt - 2012-2-26 下午12:41:39
 */
public class Showreq extends ScreenModule {
	private RequireAO requireAO = (RequireAO)getLogicClassIns("requireAO");
	
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
		
		UserRoleModel model = BillUserRoleUtil.getUserRoleModel(AuthUtil.getUserName(rundata));
		if( !model.isCanShowReq() ) {
			context.put(BillConst.Result_Code_Key, BillConst.Result_Code_Role_Err);
			rundata.forward(BillConst.Err_Page);
			return ;
		}
		context.put("userRoleModel", model);
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("page", rundata.getIntParam("p", 1));
		Map<String, Object> result = requireAO.doShowHistoryList(param);
		
		if( !BillConst.Result_Code_Success.equals(result.get(BillConst.Result_Code_Key)) ) {
			context.put(BillConst.Result_Code_Key, result.get(BillConst.Result_Code_Key));
		}
		
		context.put("maxPage", result.get("maxPage"));
		context.put("reqMainVOList", result.get("reqMainVOList"));
		context.put("user", AuthUtil.getUserName(rundata));
		context.put("p", rundata.getIntParam("p", 1));
	}
}
