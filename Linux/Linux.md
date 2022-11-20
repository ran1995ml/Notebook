# Linux

## Shell

### 脚本创建

shell是一个命令行解释器，接收应用程序命令，然后调用操作系统内核

开启子shell和不开启的区别是，环境变量的继承关系，在子shell中设置的当前变量，父shell是不可见的，子shell中修改全局变量，不会影响父shell

```shell
#给脚本添加可执行权限，输入文件名直接执行，不需要借助bash
chmod +x hello.sh

#执行脚本，source相比其他方式，不需要开启子shell，直接执行
./hello.sh
. hello.sh
source hello.sh
```

### 变量

shell的变量分为：系统预定义变量和用户自定义变量，每种变量又分为全局和局部。局部是只对当前shell有效，全局是对所有子shell也有效

```shell
#当成变量使用要加$
echo $USER

#查看所有全局变量
env

#查看所有变量，系统和用户定义的
set

#声明变量如果需要空格，用引号
var='hello world'
echo $var

#声明成全局变量
export var

#查看当前进程
ps -f

#变量默认类型是字符串，做运算需要加[]
a=$[1+5]

#设置只读变量
readonly b=5

#撤销变量
unset a
```

特殊变量

```shell
#n为数字，$0代表脚本名称，$1-$9代表第一到第九个参数，十以上需要用大括号，${10}
$n

#获取输入参数个数，用于循环
$#

#获取命令行所有参数，并把所有参数当作一个整体
$*
#获取命令行所有参数，每个参数区别对待
$@

#最后一次执行命令的返回状态，正面上次命令正确执行
$?
```

### 运算符

```shell
#表达式
expr 1 + 2
#运算符，可以放数学表达式，1<2，之外需要用it
echo $[5 * 2]
echo $((5 * 2))
```

### 条件判断

```shell
#判断执行是否成功，成功返回0
test $a=hello
#以上等价于，一定要有空格
[ $a = hello ]

#判断文件是否有对应权限
[ -r hello.sh ]

#判断文件是否存在
[ -e a.txt ]
#目录是否存在
[ -d /usr/local]

#多个条件判读那
[ $a -lt 20 ] && echo "$a < 20" || echo "$a >= 20"
```

### 函数

```shell
#日期函数
date
date %s

#获取路径后的文件名称
basename /root/data.txt

#获取文件路径
dirname /root/data.txt

#查看定时任务
crontab -l

#设置定时任务
crontab -e

#分 时 天 月 星期几
0 2 *** /root/daily.sh /root/a
```

### 正则表达式

```shell
^a  #以a开头
a$  #以a结尾
cat a.txt | grep -n ^$  #显示空行
.   #匹配任意字符
a*  #匹配上a0次或多次
^a.*b$  #a开头b结尾，中间任意
[1,2]   #匹配1或2
[0-9]   #匹配0-9
[a-c,f-g]   #匹配a-c或f-g
'\$'        #$转义
```

### 文本处理

```shell
cut -d " " -f 1 cut.txt  #以空格截取文本的第一列
cut -d " " -f 2,3 cut.txt  #以空格截取文本的第二三列
cut -d " " -f -4 cut.txt  #以空格截取文本的第四列以前
awk -F ":" '/^root/ {print $7}'  #以:截取/root开头的第七列
awk -F ":" '/^root/ {print $1","$7}'  #以:截取/root开头的第一列和第七列，并以,拼接
awk -F ":" '/^root/ {print $1","$7}'
cat /etc/passwd | awk -F ":" {print $3+1}  #第三列加1
cat /etc/passwd | awk -v i=2 -F ":" {print $3+i}  #第三列加1
cat /etc/passwd | awk -F ":" '{print "file: "FILENAME "row: "NR}'
```

