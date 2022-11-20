#!/bin/bash

#表达式
expr 1 + 2
expr 5 \* 2

#运算符
echo $[5*2]
echo $((5*2))

a=$[6+8]
echo $a

#命令替换
a=$(expr 5 \* 2)
echo $a

a=`expr 4 + 5`
echo $a

s=$[(2+3)*4]
echo $s


sum=$[$1+$2]
echo the sum: $sum

a=27
[ $a -lt 20 ] && echo "$a < 20" || echo "$a >= 20"
