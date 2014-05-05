/**
 * 版权所有：aprain.com
 */
package com.huangxt.webh.exception;

/**
 * WebhException.java 的作用：webh通用异常
 * @author huangxt - 2011-11-26 下午3:04:57
 */
public class WebhException extends Exception {

	private static final long serialVersionUID = 7998890516225561248L;

	public WebhException() {
		super();
	}
	
	public WebhException(String errorMsg) {
		super(errorMsg);
	}
	
	public WebhException(Throwable cause) {
		super(cause);
	}
	
	public WebhException(String errorMsg, Throwable cause) {
		super(errorMsg, cause);
	}
}
