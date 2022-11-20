#!/bin/bash

#拼接x，以防不传参数报错
if [ "$1"x = "hello"x ] ;then
  echo "welcome"
fi

if [ $2 -lt 18 ] ;then
  echo "0~18"
elif [ $2 -lt 35 ]; then
    echo "18~35"
else
  echo "35 more"
fi