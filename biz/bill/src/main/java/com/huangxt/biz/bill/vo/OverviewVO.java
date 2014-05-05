/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.bill.vo;

/**
 * OverviewVO.java 的作用：总览信息页面结果区域的模型
 * @author huangxt - 2012-3-8 下午8:23:51
 */
public class OverviewVO {
	private String size;
	private String height;
	private String material;
	private String grade;
	private String addr;
	private Integer num;
	
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
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
}
