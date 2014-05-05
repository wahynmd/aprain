#!/bin/sh

rm -rf /root/hxt/style/css
rm -rf /root/hxt/style/js
mv /root/temp/template/style/* /root/hxt/style/

rm -rf /root/temp/template/style

rm -rf /root/hxt/vm/template
mv /root/temp/template /root/hxt/vm

rm -rf /root/hxt/ace/*
mv /root/temp/aprain.war /root/hxt/ace

rm -rf /root/temp

echo "1. 替换 vars.properties 文件"
#echo "2. 修改style里js文件的端口"

