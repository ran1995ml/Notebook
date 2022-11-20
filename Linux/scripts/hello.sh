#!/bin/bash
echo "hello world"
#打印全局变量，测试子shell可访问到父shell声明的全局变量
echo $my_var
#测试局部变量子shell无法访问
echo $new_var


