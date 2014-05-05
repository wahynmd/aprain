/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.bill.screen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.huangxt.biz.bill.ao.CommonAO;
import com.huangxt.biz.bill.ao.OverviewAO;
import com.huangxt.biz.bill.constant.BillConst;
import com.huangxt.biz.bill.vo.OverviewSearchParamVO;
import com.huangxt.common.lang.StringEscapeUtil;
import com.huangxt.web.bill.model.UserRoleModel;
import com.huangxt.web.bill.util.BillUserRoleUtil;
import com.huangxt.web.util.AuthUtil;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Store.java 的作用：帐务系统查看库存页面
 * @author huangxt - 2012-2-25 下午2:36:54
 */
public class Overview extends ScreenModule {
	private OverviewAO overviewAO = (OverviewAO)getLogicClassIns("overviewAO");
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
		if( !model.isCanOverview() ) {
			context.put(BillConst.Result_Code_Key, BillConst.Result_Code_Role_Err);
			rundata.forward(BillConst.Err_Page);
			return ;
		}
		context.put("userRoleModel", model);
		
		OverviewSearchParamVO overviewSearchParamVO = new OverviewSearchParamVO();
		
		//设置查询入参
		int page = rundata.getIntParam("page", 1);
		if( (page < 1) || (page > 10000) ) {
			page = 1;
		}
		
		overviewSearchParamVO.setPage(page);
		overviewSearchParamVO.setGrade( rundata.getLongParam("grade", null) );
		overviewSearchParamVO.setMaterial( rundata.getLongParam("material", null) );
		overviewSearchParamVO.setHeight( rundata.getLongParam("height", null) );
		overviewSearchParamVO.setSize( rundata.getLongParam("size", null) );
		overviewSearchParamVO.setAddr( rundata.getStringParam("addr", null) );
		
		//取得item的对应关系，给页面展示
		Map<String, Object> itemResult = commonAO.doGetItems(new HashMap<String, Object>());
		context.put("itemDOList", itemResult.get("itemDOList"));
		
		//执行查询
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("overviewSearchParamVO", overviewSearchParamVO);
		Map<String, Object> result = overviewAO.doShowOverview(param);
		context.put("maxPage", result.get("maxPage"));
		context.put("overviewVOList", result.get("overviewVOList"));
		
		//页面需要的其他东西
		context.put("overviewSearchParamVO", overviewSearchParamVO);
		context.put("user", AuthUtil.getUserName(rundata));
	}
}
