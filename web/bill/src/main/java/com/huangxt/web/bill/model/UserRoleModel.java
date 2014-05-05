/**
 * 版权所有：aprain.com
 */
package com.huangxt.web.bill.model;

/**
 * UserRoleModel.java 的作用：用户权限角色模型
 * @author huangxt - 2012-5-6 下午4:03:00
 */
public class UserRoleModel {
	private String userName = "";
	private String roleType = "";
	
	private boolean canAddReq = false;		//是否可以添加要货
	private boolean canShowReq = false;		//是否可以查看要货
	private boolean canComReq = false;		//是否可以完成要货
	private boolean canDelReq = false;		//是否可以删除要货
	private boolean canOverview = false;	//是否可以查看库存
	private boolean canAddSell = false;		//是否可以添加售货记录
	private boolean canAddBuy = false;		//是否可以添加进货记录
	private boolean canShowTrade = false;	//是否可以查看历史明细
	private boolean canDelTrade = false; 	//是否可以删除历史明细
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public boolean isCanAddReq() {
		return canAddReq;
	}
	public void setCanAddReq(boolean canAddReq) {
		this.canAddReq = canAddReq;
	}
	public boolean isCanShowReq() {
		return canShowReq;
	}
	public void setCanShowReq(boolean canShowReq) {
		this.canShowReq = canShowReq;
	}
	public boolean isCanComReq() {
		return canComReq;
	}
	public void setCanComReq(boolean canComReq) {
		this.canComReq = canComReq;
	}
	public boolean isCanDelReq() {
		return canDelReq;
	}
	public void setCanDelReq(boolean canDelReq) {
		this.canDelReq = canDelReq;
	}
	public boolean isCanOverview() {
		return canOverview;
	}
	public void setCanOverview(boolean canOverview) {
		this.canOverview = canOverview;
	}
	public boolean isCanAddSell() {
		return canAddSell;
	}
	public void setCanAddSell(boolean canAddSell) {
		this.canAddSell = canAddSell;
	}
	public boolean isCanAddBuy() {
		return canAddBuy;
	}
	public void setCanAddBuy(boolean canAddBuy) {
		this.canAddBuy = canAddBuy;
	}
	public boolean isCanShowTrade() {
		return canShowTrade;
	}
	public void setCanShowTrade(boolean canShowTrade) {
		this.canShowTrade = canShowTrade;
	}
	public boolean isCanDelTrade() {
		return canDelTrade;
	}
	public void setCanDelTrade(boolean canDelTrade) {
		this.canDelTrade = canDelTrade;
	}
}
