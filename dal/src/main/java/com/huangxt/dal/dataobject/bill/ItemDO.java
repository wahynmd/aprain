/**
 * 版权所有：aprain.com
 */
package com.huangxt.dal.dataobject.bill;

/**
 * ItemDO.java 的作用：枚举值的DO模型
 * @author huangxt - 2012-3-3 下午2:24:05
 */
public class ItemDO {
	private Long id;		//主键
	private String type;	//该项属于哪个控件
	private String value;	//控件某项展示的文案
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
