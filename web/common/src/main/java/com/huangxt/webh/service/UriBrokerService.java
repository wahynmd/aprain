/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.huangxt.common.constance.VarConstance;
import com.huangxt.common.lang.ClassLoaderUtil;
import com.huangxt.common.lang.StringUtil;
import com.huangxt.webh.model.UriModel;

/**
 * UriBrokerService.java 的作用：装载uri.xml中所有uri的服务
 * @author huangxt - 2012-4-22 下午4:00:53
 */
public class UriBrokerService implements Service {
	private static Logger log = Logger.getLogger(UriBrokerService.class);
	public static final String Service_Name = "UriBrokerService";
	private Map<String, String> uriMap = null;
	
	@Override
	public void init() {
		if( uriMap != null ) {
			return ;
		}
		
		uriMap = Collections.synchronizedMap(new HashMap<String, String>());
		createFinalUriMap(getUriListFromXmlFile());
	}

	@Override
	public void destroy() {
		uriMap = null;
	}
	
	public Map<String, String> getUriMap() {
		return uriMap;
	}
	
	/** 先将xml文件解析成我们的模型 */
	private List<UriModel> getUriListFromXmlFile() {
		List<UriModel> uriModels = new ArrayList<UriModel>();
		
		try {
			InputStream uriInputStream = ClassLoaderUtil.getResourceAsStream("prop/uri.xml");
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(uriInputStream);
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName("uri");
			
			for (int i=0; i < items.getLength(); i++){
				Node item = items.item(i);
				UriModel uriModel = new UriModel();
				
				uriModel.setValue(item.getFirstChild().getNodeValue());
				uriModel.setName(item.getAttributes().getNamedItem("name").getNodeValue());
				uriModel.setExtendsName(item.getAttributes().getNamedItem("extends").getNodeValue());
				
				uriModels.add(uriModel);
			}
		} catch (Throwable t) {
			log.error("Parse uri.xml error: ", t);
		}
		
		return uriModels;
	}
	
	/** 然后将解析得的模型list转换成我们想要的最终版本 */
	private void createFinalUriMap(List<UriModel> modelList) {
		Map<String, UriModel> modelMap = new HashMap<String, UriModel>();
		
		//先将${}的占位符换成具体的实际值
		for( UriModel model : modelList ) {
			if( StringUtil.contains(model.getValue(), "${") ) {
				model.setValue(replaceUrlValue(model.getValue()));
			}
			modelMap.put(model.getName(), model);
		}
		
		//递归根据继承关系更新具体的值并放入最终的map里
		for( UriModel model : modelList ) {
			model.setValue(getExtendsValue(modelMap, model.getName()));
			model.setExtendsName(null);
			uriMap.put(model.getName(), model.getValue());
		}
	}
	
	/** 将形如${...}的值替换成properties的具体实际值 */
	private static String replaceUrlValue(String value) {
		Pattern p = Pattern.compile("\\$\\{(.*?)\\}", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(value);
		while(matcher.find()){
			value = StringUtil.replace(value, matcher.group(0), VarConstance.getVarValueByKey(matcher.group(1)));
		}
		
		return value;
	}
	
	/** 递归取得父节点的值 */
	private String getExtendsValue(Map<String, UriModel> modelMap, String name) {
		String extendName = modelMap.get(name).getExtendsName();
		if( StringUtil.isNotBlank(extendName) ) {
			return getExtendsValue(modelMap, extendName) + modelMap.get(name).getValue();
		} else {
			return modelMap.get(name).getValue();
		}
	}
}
