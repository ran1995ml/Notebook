#!/bin/bash
#声明的变量默认是字符串类型
a=1+5
echo $a
#做运算需要声明类型
b=$((1+5))
echo $b
c=$[2+6]
echo $c
