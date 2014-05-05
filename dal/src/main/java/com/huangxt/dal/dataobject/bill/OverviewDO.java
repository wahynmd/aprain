/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.dataobject.bill;

/**
 * OverviewDO.java 的作用：每个品种货物的总览
 * @author huangxt - 2012-3-3 下午2:27:46
 */
public class OverviewDO {
	private Long id;
	private Long size;
	private Long height;
	private Long grade;
	private Long material;
	private String address;
	private Integer stock;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getGrade() {
		return grade;
	}
	public void setGrade(Long grade) {
		this.grade = grade;
	}
	public Long getMaterial() {
		return material;
	}
	public void setMaterial(Long material) {
		this.material = material;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
}
