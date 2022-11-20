#!/bin/bash

#使用命令用$()修饰
filename="$1"_log_$(date +%s)
echo $filename

basename /Users/rwei/Downloads/apache-druid-24.0.0-bin.tar.gz
basename /Users/rwei/Downloads/apache-druid-24.0.0-bin.tar.gz .tar.gz
dirname /Users/rwei/Downloads/apache-druid-24.0.0-bin.tar.gz

echo script path:$(cd $(dirname $0); pwd)
