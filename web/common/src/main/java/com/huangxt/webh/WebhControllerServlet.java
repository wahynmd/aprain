/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.huangxt.common.lang.StringUtil;
import com.huangxt.webh.constant.CommonConst;
import com.huangxt.webh.constant.ContextKeyConst;
import com.huangxt.webh.valve.PerformScreenValve;
import com.huangxt.webh.valve.Valve;

/**
 * WebhControllerServlet.java 的作用：web层统一入口，统一处理所有请求
 * @author huangxt - 2011-11-13 下午3:13:14
 */
public class WebhControllerServlet extends HttpServlet {
	private static final long serialVersionUID = -2408966695016226445L;
	private static Logger log = Logger.getLogger(WebhControllerServlet.class.getName());
	private WebhController controller;
	
	@Override
	public void init() throws ServletException {
		if( controller == null ) {
			controller = new WebhController( getServletContext() );
			controller.config();
		}
	}
	
	private void invokePipeline(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		//TODO PerformActionValve
		if( StringUtil.contains(req.getPathInfo(), ".htm") ) {
			Valve screenValve = new PerformScreenValve();
			screenValve.init( getServletContext() );
			
			try {
				screenValve.invoke(req, resp);
			} catch(Throwable t) {
				log.error("invokePipeline error: ", t);
				String target = (String)req.getAttribute(ContextKeyConst.Redirect_Target_Context_Key);
				if( StringUtil.isNotBlank(target) ) {
					req.removeAttribute(ContextKeyConst.Redirect_Target_Context_Key);
				}
				req.getRequestDispatcher("error.htm").forward(req, resp);
			}
			
			String target = (String)req.getAttribute(ContextKeyConst.Redirect_Target_Context_Key);
			if( StringUtil.isNotBlank(target) ) {
				req.removeAttribute(ContextKeyConst.Redirect_Target_Context_Key);
				req.getRequestDispatcher(target).forward(req, resp);
			}
		}
	}
	
	private void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setCharacterEncoding(CommonConst.CHARSET_Encode);
			req.setAttribute(ContextKeyConst.Servlet_Context_Key, getServletContext());
			
			//如果请求路径不包含“.”，我们要内部重定向到默认的主页
			if( !StringUtil.contains(req.getPathInfo(), ".") ) {
				resp.setContentType("text/html; charset=" + CommonConst.VM_Template_Encode);
				
				if( StringUtil.isBlank(req.getPathInfo()) || StringUtil.equals("/", req.getPathInfo()) ) {
					req.getRequestDispatcher(CommonConst.Default_Index_Page).forward(req, resp);
				} else {
					if( req.getPathInfo().endsWith("/") ) {
						req.getRequestDispatcher(req.getPathInfo() + CommonConst.Default_Package_Index_Page).forward(req, resp);
					} else {
						req.getRequestDispatcher(req.getPathInfo() + "/" + CommonConst.Default_Package_Index_Page).forward(req, resp);
					}
				}
			}
			
			invokePipeline(req, resp);
		} catch (Throwable t) {
			log.error("webh.handleRequest() error: ", t);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		handleRequest(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
