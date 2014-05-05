/**
 * 版权所有：aprain.com
 */
package com.huangxt.biz.bill.vo;

/**
 * OverviewSearchParamVO.java 的作用：总览信息页面的搜索查询参数模型
 * @author huangxt - 2012-3-8 下午8:20:36
 */
public class OverviewSearchParamVO {
	private int page;
	private Long size;
	private Long height;
	private Long material;
	private Long grade;
	private String addr;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public Long getHeight() {
		return height;
	}
	public void setHeight(Long height) {
		this.height = height;
	}
	public Long getMaterial() {
		return material;
	}
	public void setMaterial(Long material) {
		this.material = material;
	}
	public Long getGrade() {
		return grade;
	}
	public void setGrade(Long grade) {
		this.grade = grade;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
}
