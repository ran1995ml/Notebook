#!/bin/bash

#查看用户是否登陆 -i:忽略大小写 -m 1只取第一次登陆  取第一列数据
login_user=$(who | grep -i -m 1 $1 | awk '{print $1}')

#为空
if [ -z $login_user ]
then
  echo "$1 not online"
  echo "exit"
  exit
fi

is_allowed=$(who -T | grep -i -m 1 $1 | awk '{print $2}')

if [ $is_allowed != "+" ]
then
  echo "$1 not open message"
  echo "exit"
  exit
fi

#确认是否有消息发送
if [ -z $2 ]
then
  echo "no message"
  echo "exit"
  exit
fi

#获取要发送的消息
whole_msg=$(echo $* | cut -d " " -f 2-)

#获取用户登陆的终端
user_terminal=$(who | grep -i -m 1 $1 | awk 'print $2')

#写入要发送的消息
echo $whole_msg | write $login_user $user_terminal

if [ $? != 0 ]
then
  echo "send faild"
else
  echo "send success"
fi
exit