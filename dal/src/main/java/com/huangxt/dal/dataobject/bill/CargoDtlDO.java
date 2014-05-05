/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.dataobject.bill;

/**
 * CargoDtlDO.java 的作用：某笔交易的详细记录
 * @author huangxt - 2012-3-3 下午2:38:32
 */
public class CargoDtlDO {
	private Long id;
	private Long prmId;
	private Long size;
	private Long height;
	private Long grade;
	private Long material;
	private Integer num;
	private String price;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPrmId() {
		return prmId;
	}
	public void setPrmId(Long prmId) {
		this.prmId = prmId;
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
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
