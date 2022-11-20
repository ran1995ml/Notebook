#!/bin/bash

function add() {
    s=$[$1 + $2]
    echo $s
    #直接return只能捕获0～255
#    return $s
}
read  -p "input first number:" a
read  -p "input second number:" b

sum=$(add $a $b)
echo $sum