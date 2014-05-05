/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.bill.vo;

import com.huangxt.common.lang.Money;

/**
 * TradeDtlVO.java 的作用：一笔交易的详情记录模型
 * @author huangxt - 2012-3-6 下午8:45:19
 */
public class TradeDtlVO {
	private String size;
	private String height;
	private String grade;
	private String material;
	private Integer num;
	private Money price;
	
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
	@Override
	public String toString() {
		return getSizeLong().toString() + ", " + getHeightLong().toString() + ", " + getGradeLong().toString() + ", " + getMaterialLong().toString();
	}
}
