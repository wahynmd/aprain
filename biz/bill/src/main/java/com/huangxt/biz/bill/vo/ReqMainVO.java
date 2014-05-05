/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.bill.vo;

import java.util.Date;
import java.util.List;

/**
 * ReqMainVO.java 的作用：要货的模型
 * @author huangxt - 2012-3-11 下午1:42:10
 */
public class ReqMainVO {
	private Long id;			//主键
	private String name;		//名字
	private String phone;		//联系电话
	private String detailAddr;	//详细地址
	private Date gmtModify;		//要货发生时间
	private String addr;		//要货的目的地
	private String operator;	//操作人
	private String isPay;		//要货的付款状态
	private String expectTime;	//要货的期望完成时间
	private String comment;		//备注
	private List<ReqDtlVO> reqDtlVOList;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDetailAddr() {
		return detailAddr;
	}
	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getIsPay() {
		return isPay;
	}
	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}
	public String getExpectTime() {
		return expectTime;
	}
	public void setExpectTime(String expectTime) {
		this.expectTime = expectTime;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<ReqDtlVO> getReqDtlVOList() {
		return reqDtlVOList;
	}
	public void setReqDtlVOList(List<ReqDtlVO> reqDtlVOList) {
		this.reqDtlVOList = reqDtlVOList;
	}
}
