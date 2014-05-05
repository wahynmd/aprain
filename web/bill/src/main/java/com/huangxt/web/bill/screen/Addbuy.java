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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huangxt.biz.bill.ao.CommonAO;
import com.huangxt.biz.bill.ao.TradeAO;
import com.huangxt.biz.bill.constant.BillConst;
import com.huangxt.biz.bill.vo.TradeDtlVO;
import com.huangxt.biz.bill.vo.TradeMainVO;
import com.huangxt.common.lang.Money;
import com.huangxt.common.lang.StringEscapeUtil;
import com.huangxt.web.bill.model.UserRoleModel;
import com.huangxt.web.bill.util.BillUserRoleUtil;
import com.huangxt.web.util.AuthUtil;
import com.huangxt.webh.module.ScreenModule;
import com.huangxt.webh.rundata.RunData;

/**
 * Buy.java 的作用：帐务信息录入进货信息页面
 * @author huangxt - 2012-2-25 下午2:38:23
 */
public class Addbuy extends ScreenModule {
	private static Logger log = Logger.getLogger(Addbuy.class);
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
		if( !model.isCanAddBuy() ) {
			context.put(BillConst.Result_Code_Key, BillConst.Result_Code_Role_Err);
			rundata.forward(BillConst.Err_Page);
			return ;
		}
		context.put("userRoleModel", model);
		context.put("user", AuthUtil.getUserName(rundata));
		
		//如果执行的是action的逻辑
		if( rundata.getStringParam("isAction", null) != null ) {
			List<TradeDtlVO> dtlVOList = getDtlVOFromJson( rundata.getStringParam("detailJson", null) );
			if( (dtlVOList == null) || (dtlVOList.size() == 0) ) {
				return ;
			}
			
			TradeMainVO mainVO = new TradeMainVO();
			
			mainVO.setTradeDtlVOList(dtlVOList);
			mainVO.setOperator(AuthUtil.getUserName(rundata));
			mainVO.setType(BillConst.Trade_Type_Buy);
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
	
	/**
	 * 解析json串，得到detailVO的list(该方法也会被Addsell类调用)
	 */
	static List<TradeDtlVO> getDtlVOFromJson(String json) {
		List<TradeDtlVO> tradeDtlVOList = new ArrayList<TradeDtlVO>();
		
		try{
			JSONArray data = JSON.parseObject(json).getJSONArray("data");
			
			for( Object obj : data ) {
				JSONObject jsonObj = (JSONObject)obj;
				TradeDtlVO vo = new TradeDtlVO();
				
				vo.setGrade( jsonObj.getString("grade") );
				vo.setHeight( jsonObj.getString("height") );
				vo.setMaterial( jsonObj.getString("material") );
				vo.setNum( jsonObj.getInteger("num") );
				vo.setPrice(new Money( jsonObj.getString("price") ));
				vo.setSize( jsonObj.getString("size") );
				
				tradeDtlVOList.add(vo);
			}
		} catch(Throwable t) {
			tradeDtlVOList.clear();
			log.error("Addbuy.getDtlVOFromJson() error, json string is" + json + ", stack-heap is ", t);
		}
		
		return tradeDtlVOList;
	}
}
