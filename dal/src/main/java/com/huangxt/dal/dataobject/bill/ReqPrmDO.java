/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.dataobject.bill;

import java.util.Date;

/**
 * CargoPrmDO.java 的作用：每一笔要货记录
 * @author huangxt - 2012-3-3 下午2:34:45
 */
public class ReqPrmDO {
	private Long id;
	private Date gmtCreate;
	private Date gmtModify;
	private String name;
	private String phone;
	private String detailAddr;
	private String operator;
	private String address;
	private String isPay;
	private String expectTime;
	private String comment;
	private String state;
	
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
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
