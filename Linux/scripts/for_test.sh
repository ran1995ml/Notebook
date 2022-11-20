#!/bin/bash

for (( i=1; i <= $1; i++))
do
  sum=$[ $sum + $i ]
done
echo $sum

for os in linux window macos
do
  echo $os
done

for i in {1..100}
do
  sum=$[$sum+$i]
done
echo $sum


echo ========while============
a=1
while [ $a -le $1 ]
do
#  sum2=$[$sum2+$a]
#  a=$[$a+1]
  let sum2+=a
  let a++
done
echo $sum2