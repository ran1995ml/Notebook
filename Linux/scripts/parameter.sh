#!/bin/bash
#脚本传参
echo '==========$n============'
echo scrpit name: $(basename $0 .sh)
echo 1st parameter: $1
echo 2nd parameter: $2
echo parameter numbers: $#

echo '==========$&=========='
echo  $*
echo  $@

echo $?


#不被引号引起来没有区别，每个参数单独打出来
echo '------------$*------------'
for para in "$*"
do
  echo $para
done


echo '------------$@------------'
for para in "$@"
do
  echo $para
done
