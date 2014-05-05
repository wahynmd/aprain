/**
 * 版权所有：aprain.com
 */
package com.huangxt.common.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.huangxt.common.lang.ClassLoaderUtil;
import com.huangxt.common.lang.ClassUtil;
import com.huangxt.common.lang.StringUtil;

/**
 * ChineseMappingUtil.java 的作用：xml文件中文映射获取工具类
 * @author huangxt - 2012-5-5 下午3:32:18
 */
@SuppressWarnings("rawtypes")
public class ChineseMappingUtil {
	private static Logger log = Logger.getLogger(ChineseMappingUtil.class);
	
	private static Map<String, String> getItems(String resourceClasspath) {
		Map<String, String> msgs = new HashMap<String, String>();
		
		try {
			InputStream uriInputStream = ClassLoaderUtil.getResourceAsStream(resourceClasspath);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(uriInputStream);
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName("message");
			
			for (int i=0; i < items.getLength(); i++){
				Node item = items.item(i);
				msgs.put(item.getAttributes().getNamedItem("id").getNodeValue(), item.getFirstChild().getNodeValue());
			}
		} catch (Throwable t) {
			log.error("Parse chinese.xml error, resourceClasspath is " + resourceClasspath + ". ", t);
		}
		
		return msgs;
	}
	
	/** 根据类名加载同名同类路径的xml中文映射文件，并获取某个key的值 */
	public static String getItem(Class clazz, String key) {
		String resourceClassPath = StringUtil.replace(ClassUtil.getClassNameAsResource(clazz), ".class", ".xml");
		return getItems(resourceClassPath).get(key);
	}
	
	/** 根据类名加载同名同类路径的xml中文映射文件，并获取该配置文件所有的值 */
	public static Map<String, String> getItems(Class clazz) {
		String resourceClassPath = StringUtil.replace(ClassUtil.getClassNameAsResource(clazz), ".class", ".xml");
		return getItems(resourceClassPath);
	}
}
