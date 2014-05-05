#!/bin/bash
#Write by huangxt at 2012-5-12
#部署到服务器时使用

TODAY=`date +%Y%m%d`
export TODAY

rm release-*
mvn clean package
mkdir ./temp
cp ./deploy/target/aprain.war ./temp/aprain.war
cp -r ./deploy/src/main/template ./temp/template
tar -zcvf ./release-$TODAY.tar.gz temp
rm -rf ./temp

