#!/bin/bash

#  write by huangxt at 2012-2-19
#  本地开发调试使用

#Tomcat的conf/server.xml 改动如下：

#<Host name="ace.aprain.com"  appBase="webapps/aprain" unpackWARs="true" autoDeploy="true">
#	<Context path="" docBase="aprain"  reloadable="true"></Context>
#</Host>
#访问：http://ace.aprain.com:9091/common/login.htm  即可。

#<Host name="style.aprain.com"  appBase="/personal/workspace_3.7/aprain/deploy/src/main/template" unpackWARs="false" autoDeploy="true">
#	<Context path="" docBase="style"  reloadable="true"></Context>
#</Host>
#访问：http://style.aprain.com:9091/js/test1.js

TOMCAT_HOME="/personal/soft/tomcat7"
export TOMCAT_HOME
TOMCAT_WEBAPP="$TOMCAT_HOME/webapps"
export TOMCAT_WEBAPP

prepare() {
	# 删除log文件，启动时会重新生成
	rm "/personal/workspace_3.7/logs/aprain.log"
	rm "/personal/workspace_3.7/logs/ip.log"
	rm "/personal/workspace_3.7/logs/logic.log"
	rm "/personal/workspace_3.7/logs/velocity.log"

	# 删除webapp下的aprain目录
	if [ -d "$TOMCAT_WEBAPP/aprain" ] ; then
   		rm -rf  "$TOMCAT_WEBAPP/aprain"
		echo "delete aprain dir..."
	fi
	
	# 重新生成aprain目录
	mkdir "$TOMCAT_WEBAPP/aprain"
	if [ -d "$TOMCAT_WEBAPP/aprain" ] ; then
		echo "make aprain dir..."
	fi

	## 拷贝新的war包到webapps目录
	cp ./deploy/target/aprain.war $TOMCAT_WEBAPP/aprain/aprain.war
	if [ -e "$TOMCAT_WEBAPP/aprain/aprain.war" ] ; then
		echo "aprain war file copy success..."
	fi
}

start() {
	prepare
	sh $TOMCAT_HOME/bin/catalina.sh run
}

start

