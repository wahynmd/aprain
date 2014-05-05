/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.bill.screen.operate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.huangxt.biz.bill.ao.RequireAO;
import com.huangxt.biz.bill.constant.BillConst;
import com.huangxt.common.lang.StringEscapeUtil;
import com.huangxt.web.bill.util.BillUserRoleUtil;
import com.huangxt.web.util.AuthUtil;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Optreq.java 的作用：操作一笔要货记录，比如完成、取消一笔要货的时候
 * @author huangxt - 2012-2-26 下午12:53:34
 */
public class Upreq extends ScreenModule {
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
		
		int type = rundata.getIntParam("type", -1);
		long id = rundata.getLongParam("id", -1L);
		
		//1：完成；2：取消。
		if( id < 1 || ((type != 1) && (type != 2)) ) {
			return ;
		}
		
		if( (type == 1) && (!BillUserRoleUtil.getUserRoleModel(AuthUtil.getUserName(rundata)).isCanComReq()) ) {
			context.put(BillConst.Result_Code_Key, BillConst.Result_Code_Role_Err);
			rundata.forward(BillConst.Err_Page);
			return ;
		} else if( (type == 2) && (!BillUserRoleUtil.getUserRoleModel(AuthUtil.getUserName(rundata)).isCanDelReq()) ) {
			context.put(BillConst.Result_Code_Key, BillConst.Result_Code_Role_Err);
			rundata.forward(BillConst.Err_Page);
			return ;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("reqMainId", id);
		param.put("operator", AuthUtil.getUserName(rundata));
		param.put("type", (type==1) ? BillConst.Req_State_Complete : BillConst.Req_State_Cancel );
		
		Map<String, Object> result = requireAO.doUpReqState(param);
		
		if( BillConst.Result_Code_Success.equals(result.get(BillConst.Result_Code_Key)) ) {
			try {
				rundata.sendRedirect(rundata.getUriBroker().get("bill_show_req_page"));
			} catch (IOException e) {
			}
		} else {
			context.put(BillConst.Result_Code_Key, result.get(BillConst.Result_Code_Key));
			context.put("user", AuthUtil.getUserName(rundata));
			rundata.forward(BillConst.Err_Page);
		}
	}
}
