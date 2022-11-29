# 特性
容器化管理系统，让部署容器化应用更高效

# 架构
Master：主控节点
* apiserver：集群统一入口，以restful方式，交给etcd存储
* scheduler：节点调度，选择node节点应用部署
* controller-manager：处理集群中常规后台任务，一个资源对应一个控制器，管理特定类型的任务
* etcd：存储系统，保存集群相关数据
Node：工作节点
* kubelet：Master派到姐Node代表，管理本机容器
* kube-proxy：提供网络代理，负载均衡操作
Pod：最小部署单元，一组容器的集合，共享网络，生命周期短暂
Controller：确保预期的pod副本数量，确保所有node运行同一个pod，支持一次性任务和定时任务
Service：定义一组pod的访问规则

# 安装环境准备
```shell
#关闭防火墙
systemctl stop firewalld
systemctl disable firewalld

#关闭selinux
sed -i '/SELINUX/s/enforcing/disabled/' /etc/selinux/config

#关闭swap
sed -ri 's/.*swap*/#&/' /etc/fstab
```

#将桥接的IPv4流量传递到iptables的链
cat > /etc/sysctl.d/ks8.conf << EOF 
net.bridge.bridge-nf-call-ip6tables=1
net.bridge.bridge-nf-call-iptables=1
EOF
sysctl --system

#时间同步
yum install ntpdate -y
ntpdate time.windows.com


