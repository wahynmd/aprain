/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.bill.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.huangxt.biz.bill.ao.CommonAO;
import com.huangxt.biz.bill.ao.RequireAO;
import com.huangxt.biz.bill.constant.BillConst;
import com.huangxt.biz.bill.vo.ReqDtlVO;
import com.huangxt.biz.bill.vo.ReqMainVO;
import com.huangxt.common.lang.Money;
import com.huangxt.common.lang.StringEscapeUtil;
import com.huangxt.web.bill.model.UserRoleModel;
import com.huangxt.web.bill.util.BillUserRoleUtil;
import com.huangxt.web.util.AuthUtil;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Request.java 的作用：帐务系统的添加要货页面
 * @author huangxt - 2012-2-25 下午2:35:03
 */
public class Addreq extends ScreenModule {
	private static Logger log = Logger.getLogger(Addreq.class);
	private RequireAO requireAO = (RequireAO)getLogicClassIns("requireAO");
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
		if( !model.isCanAddReq() ) {
			context.put(BillConst.Result_Code_Key, BillConst.Result_Code_Role_Err);
			rundata.forward(BillConst.Err_Page);
			return ;
		}
		context.put("userRoleModel", model);
		context.put("user", AuthUtil.getUserName(rundata));
		
		//如果执行的是action的逻辑
		if( rundata.getStringParam("isAction", null) != null ) {
			List<ReqDtlVO> dtlVOList = getDtlVOFromJson( rundata.getStringParam("detailJson", null) );
			if( (dtlVOList == null) || (dtlVOList.size() == 0) ) {
				return ;
			}
			
			ReqMainVO mainVO = new ReqMainVO();
			
			mainVO.setReqDtlVOList(dtlVOList);
			mainVO.setOperator(AuthUtil.getUserName(rundata));
			mainVO.setComment( rundata.getStringParam("comment", null) );
			mainVO.setAddr( rundata.getStringParam("addr", null) );
			mainVO.setName( rundata.getStringParam("name", null) );
			mainVO.setExpectTime( rundata.getStringParam("expectTime", null) );
			mainVO.setIsPay( rundata.getStringParam("isPay", null) );
			mainVO.setDetailAddr( rundata.getStringParam("detailAddr", null) );
			mainVO.setPhone( rundata.getStringParam("phone", null) );
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("reqMainVO", mainVO);
			Map<String, Object> result = requireAO.doAddReqRecord(param);
			
			if( BillConst.Result_Code_Success.equals(result.get(BillConst.Result_Code_Key)) ) {
				try {
					rundata.sendRedirect(rundata.getUriBroker().get("bill_show_req_page"));
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
	
	/**
	 * 解析json串，得到detailVO的list
	 */
	static List<ReqDtlVO> getDtlVOFromJson(String json) {
		List<ReqDtlVO> reqDtlVOList = new ArrayList<ReqDtlVO>();
		
		try{
			JSONArray data = JSON.parseObject(json).getJSONArray("data");
			
			for( Object obj : data ) {
				JSONObject jsonObj = (JSONObject)obj;
				ReqDtlVO vo = new ReqDtlVO();
				
				vo.setGrade( jsonObj.getString("grade") );
				vo.setHeight( jsonObj.getString("height") );
				vo.setMaterial( jsonObj.getString("material") );
				vo.setNum( jsonObj.getInteger("num") );
				vo.setPrice(new Money( jsonObj.getString("price") ));
				vo.setSize( jsonObj.getString("size") );
				vo.setComment( jsonObj.getString("detailComment") );
				
				reqDtlVOList.add(vo);
			}
		} catch(Throwable t) {
			reqDtlVOList.clear();
			log.error("Addreq.getDtlVOFromJson() error, json string is" + json + ", stack-heap is ", t);
		}
		
		return reqDtlVOList;
	}
}
