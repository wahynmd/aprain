/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.bill.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TradeSearchParamVO.java 的作用：交易历史明细页面查询参数模型
 * @author huangxt - 2012-3-6 下午9:47:56
 */
public class TradeSearchParamVO {
	private String operator;
	private String address;
	private String tradeType;
	private Date dateBegin;
	private Date dateEnd;
	private int page;
	
	public String getBeginString() {
		if( dateBegin != null )
			return new SimpleDateFormat("yyyy-MM-dd").format(dateBegin);
		else
			return null;
	}
	public String getEndString() {
		if( dateEnd != null )
			return new SimpleDateFormat("yyyy-MM-dd").format(dateEnd);
		else
			return null;
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
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public Date getDateBegin() {
		return dateBegin;
	}
	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}
	public Date getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
}
