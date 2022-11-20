#!/bin/bash
#定时对文件归档脚本

#判断输入参数个数是否为1
if [ $# -ne 1 ]
then
  echo "parameter number error"
  exit
fi

#从参数中获取目录
if [ -d $1 ]
then
  echo
else
  echo
  echo "dir not exists"
  echo
  exit
fi

DIR_NAME=$(basename $1)
DIR_PATH=$(cd $(dirname $1);pwd)

#获取当前日期
DATE=$(date +%y%m%d)

#定义生成的归档文件名称
FILE=archive_${DIR_NAME}_$DATE.tar.gz
DEST=./archive/$FILE

#归档文件
echo "tar starting...."
echo
#c归档、z压缩、f可视化
tar -czf $DEST $DIR_PATH/$DIR_NAME

if [ $? -eq 0 ]
then
  echo
  echo "tar success"
  echo "path: $DEST"
  echo
else
  echo "error"
  echo
fi