/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.bill.vo;

import com.huangxt.common.lang.Money;

/**
 * ReqDtlVO.java 的作用：要货的每个品种的明细
 * @author huangxt - 2012-3-11 下午1:47:44
 */
public class ReqDtlVO {
	private String size;
	private String height;
	private String grade;
	private String material;
	private Integer num;
	private Money price;
	private String comment;
	
	public Long getSizeLong() {
		return Long.parseLong(size);
	}
	public Long getHeightLong() {
		return Long.parseLong(height);
	}
	public Long getGradeLong() {
		return Long.parseLong(grade);
	}
	public Long getMaterialLong() {
		return Long.parseLong(material);
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Money getPrice() {
		return price;
	}
	public void setPrice(Money price) {
		this.price = price;
	}
}
