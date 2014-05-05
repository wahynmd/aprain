/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh.constant;

/**
 * CommonConst.java 的作用：基本常用的webh常量
 * @author huangxt - 2011-11-27 上午10:44:53
 */
public class CommonConst {
	/** web层无screen的时候，超类的类路经 */
	public static final String Web_Layer_NoScreen_Package_Prefix = "com.huangxt.webh.module.ScreenModule";
	
	/** web层包路径的前缀 */
	public static final String Web_Layer_Package_Prefix = "com.huangxt.web";
	
	/** web层的类是否使用cache */
	public static final Boolean Is_Web_Layer_Class_Use_Cache = true;
	
	/** screen模块的type常量 */
	public static final String Screen_Module_Type = "screen";
	
	/** action模块的type常量 */
	public static final String Action_Module_Type = "action";
	
	/** VM模板的默认编码 */
	public static final String VM_Template_Encode = "utf-8";
	
	/** 应用的默认编码 */
	public static final String CHARSET_Encode = VM_Template_Encode;
	
	/** spring bean 总配置文件的classpath路径 */
	public static final String Spring_Bean_File_Classpath = "bean/beanFactory.xml";
	
	/** 默认的主页 */
	public static final String Default_Index_Page = "/common/index.htm";
	
	/** 每个包下面默认的主页 */
	public static final String Default_Package_Index_Page = "index.htm";
}
