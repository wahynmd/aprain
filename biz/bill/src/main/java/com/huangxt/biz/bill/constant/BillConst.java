/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.bill.constant;

/**
 * BillConst.java 的作用：帐务系统的所有常量
 * @author huangxt - 2012-3-6 下午9:17:28
 */
public class BillConst {
	//数据库常量区
	
	/** 交易类型：售货 */
	public static final String Trade_Type_Sale = "s";
	/** 交易类型：进货 */
	public static final String Trade_Type_Buy = "b";
	
	/** 交易记录是否删除：是 */
	public static final String Trade_Record_Is_Delete_Yes = "y";
	/** 交易记录是否删除：否 */
	public static final String Trade_Record_Is_Delete_No = "n";
	
	/** 地点类型：小库 */
	public static final String Addr_XiaoKu = "xk";
	/** 地点类型：大库 */
	public static final String Addr_DaKu = "dk";
	/** 地点类型：货站 */
	public static final String Addr_HuoYun = "hy";
	/** 地点类型：库房提货 */
	public static final String Addr_TiHuo = "th";
	
	/** 要货-是否付款类型：已经付款 */
	public static final String Pay_Yes = "yes";
	/** 要货-是否付款类型：货运代收 */
	public static final String Pay_HuoYun = "no_hy";
	/** 要货-是否付款类型：库房收款 */
	public static final String Pay_KuFang = "no_kf";
	
	/** 要货记录的状态类型：已完成 */
	public static final String Req_State_Complete = "complete";
	/** 要货记录的状态类型：待完成 */
	public static final String Req_State_Wait_Complete = "wait";
	/** 要货记录的状态类型：取消 */
	public static final String Req_State_Cancel = "cancel";
	
	/** item表类型常量：尺寸 */
	public static final String Item_Type_Size = "size";
	/** item表类型常量：厚度 */
	public static final String Item_Type_Height = "height";
	/** item表类型常量：等级 */
	public static final String Item_Type_Grade = "grade";
	/** item表类型常量：材质 */
	public static final String Item_Type_Material = "material";
	
	/** 用户角色类型：管理员 */
	public static final String User_Role_Type_Admin = "admin";
	/** 用户角色类型：仓库总管 */
	public static final String User_Role_Type_Back_Manager = "back_manager";
	/** 用户角色类型：前台总管 */
	public static final String User_Role_Type_Front_Manager = "front_manager";
	/** 用户角色类型：前台售货员 */
	public static final String User_Role_Type_Front_Seller = "front_seller";
	/** 用户角色类型：guest */
	public static final String User_Role_Type_Guest = "guest";
	
	//AO和Web层往返的参数和key的常量
	
	/** AO返回给web层的状态码的key */
	public static final String Result_Code_Key = "code";
	
	/** AO执行的结果状态码：入参错误 */
	public static final String Result_Code_Param_Error = "param_error";
	
	/** AO执行的结果状态码：catch到的不明错误 */
	public static final String Result_Code_Sys_Error = "sys_error";
	
	/** AO执行的结果状态码：成功 */
	public static final String Result_Code_Success = "success";
	
	/** AO执行的结果状态码：货物不够，无法售出或完成要货 */
	public static final String Result_Code_Lack = "lack_error";
	
	/** AO执行的结果状态码：您暂无权限进行此操作 */
	public static final String Result_Code_Role_Err = "role_error";
	
	//页面常量
	/** bs系统的错误页面（内部重定向用） */
	public static final String Err_Page = "/bill/error.htm";
	
	//其他常量区
	
	/** 分页显示的页面的page size */
	public static final int Page_Size = 10;
}
