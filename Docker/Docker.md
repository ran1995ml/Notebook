# Docker

系统平滑移植，容器化技术，仅包含进程需要的运行时环境。和虚拟机相比，容器不是模拟一个完整的操作系统，而是对进程隔离，将运行需要的所有资源打包到一个隔离的容器中。

Docker实质是在已运行的Linux下制造的隔离文件环境，必须部署在Linux内核的系统上。

Docker三要素：镜像、容器、仓库。容器类似一个虚拟化的运行环境，是用镜像创建的运行实例。镜像和容器类似类和对象的关系。仓库是集中存放镜像文件的场所。

## 安装

```shell
//安装软件包
sudo yum install -y yum-utils
//设置stable镜像仓库
sudo yum-config-manager \
    --add-repo \
    https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
//更新yum软件包索引，加快下载速度
yum makecache fast
//安装docker引擎
sudo yum install docker-ce docker-ce-cli containerd.io docker-compose-plugin
//启动
systemctl start docker
//启动hello-world镜像库
docker run hello-world

//配置镜像加速器
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
	"registry-mirrors":["https://aa25jngu.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```

## 命令

### 基本命令

```dockerfile
//列出本机镜像
docker images
//查某个镜像
docker search
//拉取镜像到本地
docker pull 镜像名
//查看镜像/容器/数据卷所占空间
docker system df
//删除镜像
docker rmi 镜像名id
```

镜像执行：

1. 判断本机是否有该镜像，若有则以该镜像为生产容器实例运行；
2. 否则在Docker Hub查找该镜像，下载到本地运行、

### 容器命令

```shell
#交互式启动容器
docker run -it --name=my centos /bin/bash
#列出正在运行的容器
docker ps
#启动已停止容器
docker start 容器ID/名称
#重启容器
docker restart 容器ID/名称
#停止容器
docker stop 容器ID/名称
#查看日志
docker logs 容器ID/名称
#查看容器内部细节
docker inspect 容器ID/名称
#重新进入实例
docker exec -it 容器ID/名称 /bin/bash
docker attch -it 容器ID/名称 /bin/bash
#拷贝文件到主机
docker cp 容器ID:容器内路径 主机路径
#备份容器
docker export 容器ID > a.tar
#将tar文件导出为镜像
cat a.tar | docker import - 镜像用户/镜像名:镜像版本号
```

以后台模式启动容器，必须有一个前台进程，容器运行命令如果不是一直挂起的命令会自动退出。

attch直接进入终端，exit会退出进程；exec开启新的终端，exit不会退出进程。

## 镜像

镜像是一种轻量级、可执行的独立软件包，包含运行某个软件所需的所有内容，将应用程序和配置依赖打包好形成一个可交付的运行环境。

UnionFS是一种分层、轻量级且高性能的文件系统，支持对文件系统的修改作为一次提交来一层层叠加，可将不同的目录挂载到同一个虚拟文件系统下，是Dockers镜像的基础。镜像可通过分层继承，基于基础镜像可制作各种具体的应用镜像。

分层的目的是为了更方便共享资源。

容器启动后，新的可写层加载到镜像顶部称作容器层，容器层之下是镜像层，只有容器层是可写的。

```shell
#提交容器副本成为新的镜像
docker commit
```

## 数据卷

```shell
#-d:后台运行 端口映射 添加自定义容器卷:宿主机路径/容器内路径 镜像名
docker run -d -p 5000:5000 -v /temp1:/temp2 --privileged=true registry
```

目的：容器数据卷完成数据持久化。

卷是目录或文件，由docker挂在到容器不属于UnionFS，可绕过UnionFS提供持续存储，完全独立于容器的生命周期，不会在删除容器时删除其挂载的数据卷。

读写规则：rw读写，ro只读

```shell
# 数据卷继承
docker run -it --privileged=true --volumnes-from u1 --name u2 centos
```

### mysql安装

```shell
# mysql挂载数据卷
docker run -d -p 3306:3306 --privileged=true
-v /ss/mysql/log:/var/log/mysql
-v /ss/mysql/data:/var/lib/mysql
-v /ss/mysql/conf:/etc/mysql/conf.d
-e MYSQL_ROOT_PASSWORD=123456
--name mysql mysql:5.7
```

#### 主从复制

```shell
# 创建master容器
docker run -d -p 3307:3306 --privileged=true
-v /data/mysql-master/log:/var/log/mysql
-v /data/mysql-master/data:/var/lib/mysql
-v /data/mysql-master/conf:/etc/mysql/conf.d
-e MYSQL_ROOT_PASSWORD=123456
--name mysql-master mysql:5.7

#创建用户授权
create user 'slave'@'%' identified by '123456';
grant replication slave,replication client on *.* to 'slave'@'%';

#创建slave容器
#创建master容器
docker run -d -p 3308:3306 --privileged=true
-v /data/mysql-slave/log:/var/log/mysql
-v /data/mysql-slave/data:/var/lib/mysql
-v /data/mysql-slave/conf:/etc/mysql/conf.d
-e MYSQL_ROOT_PASSWORD=123456
--name mysql-slave mysql:5.7

#master中查看主从复制状态
show master status;

#slave配置主从复制
change master to master_host='192.168.47.106', master_user='slave',master_password='123456',master_port=3307,master_log_file='mall-mysql-bin.000001',master_log_pos=617,master_connect_retry=30;

#slave中查看主从复制状态
show slave status \G;

#slave开启主从复制
start slave
```

### redis安装

```shell
docker run -d -p 6379:6379 redis:6.0.8
```

#### 分布式存储

哈希取余算法：扩容缩容容易出问题

一致性哈希算法：当服务器个数发生变动，尽可能减少影响客户端到服务端的映射关系。将整个哈希值空间组织成一个虚拟的圆环，对2^32取模，计算服务器IP地址的hash值确定在环中的位置，key值经过hash取余确定在环中的位置，沿环顺时针行走找到要放的服务器位置。提高了容错性和扩展性，但容易造成数据倾斜问题。

哈希槽算法：哈希槽实质是个数组[0,2^14-1]。解决均匀分配问题，在数据和节点间加入一层称为哈希槽，用于管理节点和数据间的关系。相当于节点上放的是槽，槽里放的是数据。槽位不宜过多，太多需要发送过多心跳。槽的分配策略没有要求，可指定哪些编号的槽分配给哪个节点。对key求哈希值，对2^14-1取余，落入对应的槽里。

#### 主从Redis搭建

```shell
#创建容器
docker run -d --name redis-node-1 --net host --privileged=true -v /data/redis/share/redis-node-1:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6381
docker run -d --name redis-node-2 --net host --privileged=true -v /data/redis/share/redis-node-2:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6382
docker run -d --name redis-node-3 --net host --privileged=true -v /data/redis/share/redis-node-3:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6383
docker run -d --name redis-node-4 --net host --privileged=true -v /data/redis/share/redis-node-4:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6384
docker run -d --name redis-node-5 --net host --privileged=true -v /data/redis/share/redis-node-5:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6385
docker run -d --name redis-node-6 --net host --privileged=true -v /data/redis/share/redis-node-6:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6386

#设置主从，将集群两两配对
redis-cli --cluster create 192.168.47.106:6381 192.168.47.106:6382 192.168.47.106:6383 192.168.47.106:6384 192.168.47.106:6385 192.168.47.106:6386 --cluster-replicas 1

#查看节点情况
cluster nodes

#优化路由
redis-cli -p 6381 -c

#检查集群信息
redis-cli --cluster check 192.168.47.106:6381
```

#### 扩容

```shell
#节点数扩充
docker run -d --name redis-node-7 --net host --privileged=true -v /data/redis/share/redis-node-7:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6387
docker run -d --name redis-node-8 --net host --privileged=true -v /data/redis/share/redis-node-8:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6388

#加入集群
redis-cli --cluster add-node 192.168.47.106:6387 192.168.47.106:6381

#重新分配槽
redis-cli --cluster reshard 192.168.47.106:6381    分配16384/4

#检查集群信息
redis-cli --cluster check 192.168.47.106:6381

#分配方式是其他节点从已有的槽中分出部分给新节点

#添加从机
redis-cli --cluster add-node 192.168.47.106:6388 192.168.47.106:6387 --cluster-slave --cluster-master-id d331727d14bda29c9c45bb7b5dbe95d9fd59c97e
```

#### 缩容

```shell
#删除slave
redis-cli --cluster del-node 192.168.47.106:6388 a79dfe5fa6f545bc3d069a8eb555f86357ba43c5

#清空master槽
redis-cli --cluster reshard 192.168.47.106:6381

#删除master
redis-cli --cluster del-node 192.168.47.106:6387 d331727d14bda29c9c45bb7b5dbe95d9fd59c97e
```

## Dockerfile

用来构建Docker镜像的文本文件，由一条条构建镜像所需的指令和参数构成，每条指令都会创建一个新的镜像层并提交。

Docker执行Dockerfile流程：

1. 从基础镜像运行一个容器；
2. 执行一条指令对容器修改；
3. 执行commit操作提交一个新的镜像层；
4. 基于刚提交的镜像运行一个新容器；
5. 执行下一条指令。

### 指令

FROM：指定一个已存在的镜像为模板

MAINTAINER：镜像维护者姓名和邮箱

RUN：容器构建时运行的命令，在docker build时运行

EXPOSE：当前容器对外暴露的端口

WORKDIR：指定创建容器后，终端默认登录进来的目录

ENV：构建镜像过程中设置环境变量

VOLUME：容器数据卷，用于数据保存和持久化

ADD：将宿主机目录下的文件拷贝进镜像

CMD：指定容器启动后要做的事情，在docker run时运行

ENTRYPOINT：类似CMD，但不会被docker run后的命令覆盖

### 构建Java8镜像

编写Dockerfile：

```dockerfile
FROM centos:centos7
MAINTAINER ran<ran1995ml@163.com>
 
ENV MYPATH /usr/local
WORKDIR $MYPATH
 
#安装vim编辑器
RUN yum -y install vim
#安装ifconfig命令查看网络IP
RUN yum -y install net-tools
#安装java8及lib库
RUN yum -y install glibc.i686
RUN mkdir /usr/local/java
#ADD 是相对路径jar,把jdk-8u311-linux-x64.tar.gz添加到容器中,安装包必须要和Dockerfile文件在同一位置
ADD jdk-8u311-linux-x64.tar.gz /usr/local/java/
#配置java环境变量
ENV JAVA_HOME /usr/local/java/jdk1.8.0_311
ENV JRE_HOME $JAVA_HOME/jre
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$JRE_HOME/lib:$CLASSPATH
ENV PATH $JAVA_HOME/bin:$PATH
 
EXPOSE 80
 
CMD echo $MYPATH
CMD echo "success--------------ok"
CMD /bin/bash
```

构建镜像：

```shell
docker build -t centosjava8:1.1 .
```

### 虚悬镜像

虚悬镜像：仓库名、标签都是<none>的镜像。

```shell
#查看本机虚悬镜像
docker image ls -f dangling=true
```

