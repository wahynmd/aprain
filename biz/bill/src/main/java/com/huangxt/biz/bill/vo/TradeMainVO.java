/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.bill.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * TradeMainVO.java 的作用：一笔交易的主表记录模型
 * @author huangxt - 2012-3-6 下午8:44:15
 */
public class TradeMainVO {
	private Long id;			//主键
	private String name;		//名字
	private Date gmtCreate;		//交易发生时间
	private String addr;		//进货的存货地，或卖货地的存货地
	private String operator;	//操作人
	private String type;		//进货还是卖货
	private String comment;		//备注
	private List<TradeDtlVO> tradeDtlVOList;	//该笔交易的明细
	
	public String getCreateString() {
		if( gmtCreate != null )
			return new SimpleDateFormat("yyyy-MM-dd").format(gmtCreate);
		else
			return null;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<TradeDtlVO> getTradeDtlVOList() {
		return tradeDtlVOList;
	}
	public void setTradeDtlVOList(List<TradeDtlVO> tradeDtlVOList) {
		this.tradeDtlVOList = tradeDtlVOList;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
