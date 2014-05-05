/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.bill.util;

import com.huangxt.biz.bill.constant.BillConst;
import com.huangxt.web.bill.model.UserRoleModel;

/**
 * BillUserRoleUtil.java 的作用：Bill业务模块的用户权限工具类
 * @author huangxt - 2012-5-6 下午3:56:53
 */
public class BillUserRoleUtil {
	/** 根据用户的登录名，获取该用户的权限模型 */
	//TODO 权限暂时先写死，以后可以从数据库里读取
	public static UserRoleModel getUserRoleModel(String userName) {
		String roleType = getUserRoleType(userName);
		UserRoleModel model = new UserRoleModel();
		model.setRoleType(roleType);
		model.setUserName(userName);
		
		if( BillConst.User_Role_Type_Admin.equals(roleType) ) {
			model.setCanAddBuy(true);
			model.setCanAddReq(true);
			model.setCanAddSell(true);
			model.setCanComReq(true);
			model.setCanDelReq(true);
			model.setCanDelTrade(true);
			model.setCanOverview(true);
			model.setCanShowReq(true);
			model.setCanShowTrade(true);
		} else if( BillConst.User_Role_Type_Back_Manager.equals(roleType) ) {
			model.setCanAddBuy(true);
			model.setCanAddReq(false);
			model.setCanAddSell(true);
			model.setCanComReq(true);
			model.setCanDelReq(false);
			model.setCanDelTrade(true);
			model.setCanOverview(true);
			model.setCanShowReq(true);
			model.setCanShowTrade(true);
		} else if( BillConst.User_Role_Type_Front_Manager.equals(roleType) ) {
			model.setCanAddBuy(false);
			model.setCanAddReq(true);
			model.setCanAddSell(true);
			model.setCanComReq(false);
			model.setCanDelReq(true);
			model.setCanDelTrade(true);
			model.setCanOverview(true);
			model.setCanShowReq(true);
			model.setCanShowTrade(true);
		} else if( BillConst.User_Role_Type_Front_Seller.equals(roleType) ) {
			model.setCanAddBuy(false);
			model.setCanAddReq(true);
			model.setCanAddSell(false);
			model.setCanComReq(false);
			model.setCanDelReq(false);
			model.setCanDelTrade(false);
			model.setCanOverview(true);
			model.setCanShowReq(true);
			model.setCanShowTrade(false);
		} else if( BillConst.User_Role_Type_Guest.equals(roleType) ) {
			model.setCanAddBuy(false);
			model.setCanAddReq(false);
			model.setCanAddSell(false);
			model.setCanComReq(false);
			model.setCanDelReq(false);
			model.setCanDelTrade(false);
			model.setCanOverview(true);
			model.setCanShowReq(false);
			model.setCanShowTrade(false);
		}
		
		return model;
	}
	
	/** 根据用户名获取该用户的角色类型 */
	private static String getUserRoleType(String username) {
		if( "hxt".equals(username) ) {
			return BillConst.User_Role_Type_Admin;
		} else if( "hzh".equals(username) ) {
			return BillConst.User_Role_Type_Back_Manager;
		} else if( "lj".equals(username) ) {
			return BillConst.User_Role_Type_Front_Manager;
		} else if( "seller".equals(username) ) {
			return BillConst.User_Role_Type_Front_Seller;
		} else if( "guest".equals(username) ) {
			return BillConst.User_Role_Type_Guest;
		} else {
			return "";
		}
	}
}
