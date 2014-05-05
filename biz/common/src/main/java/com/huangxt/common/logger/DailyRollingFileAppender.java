/**
 * 版权所有：aprain.com
 */
package com.huangxt.common.logger;

import java.io.File;
import java.io.IOException;

import com.huangxt.common.constance.VarConstance;

/**
 * DailyRollingFileAppender.java 的作用：设置日志的创建路径，并自动创建文件夹
 * @author huangxt - 2012-2-11 下午10:16:40
 */
public class DailyRollingFileAppender extends org.apache.log4j.DailyRollingFileAppender {

	@Override
	public synchronized void setFile(String arg0, boolean arg1, boolean arg2, int arg3) throws IOException {
		//如果应用已经启动，那么“super.fileName”就不再是配置文件里的“XXX.log”形式的，而是带有绝对路径的值
		if( super.fileName.contains("/") ) {
			arg0 = super.fileName;
		} else {
			arg0 = VarConstance.getLogPath() + "/" + super.fileName;
		}
		
		File file = new File(arg0);
		if( !file.exists() ) {
			file.getParentFile().mkdirs();
		}
		
		super.setFile(arg0, arg1, arg2, arg3);
	}
}
