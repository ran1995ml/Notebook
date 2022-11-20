package com.ran.notebook.base

import java.util
import scala.collection.mutable
import scala.collection.JavaConversions.mapAsScalaMap
import scala.collection.JavaConversions.propertiesAsScalaMap

/**
 * @ClassName: MapDemo
 * @Description:
 * @Author: rwei
 * @Date: 2022/11/16 16:00
 */
object MapDemo {
  def main(args: Array[String]): Unit = {
    //不可变map
    val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)
    //可变map
    val scores1 = scala.collection.mutable.Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)
    val scores2 = new mutable.HashMap[String, Int]
    val bobScore = scores("Bob")
    scores.contains("Bob")
    //返回一个Option对象
    scores.getOrElse("Bob",0)

    //更新操作
    scores1("Bob") = 10
    scores1 += ("a"->1,"b"->2)
    scores1 -= "Alice"
    var newScores = scores + ("c"->3,"d"->4)

    //迭代
    for((k,v)<-scores) println(k+" "+v)
    for(v<-scores.values) println(v)
    for((k,v)<-scores) yield (v,k)

    //与java互操作
    val scores3:scala.collection.mutable.Map[String,Int] =
      new util.TreeMap[String,Int]
    val props:scala.collection.Map[String,String] = System.getProperties()

    //元组主要用于函数需要返回不止一个值的情况
    //元组下标从1开始
    val tuple = (1,3.14,"Fred")
    val value = tuple._1
    //使用模式匹配获取元组组元，不需要的位置使用_
    val (first,second,_) = tuple
    //拉链操作
    val symbols = Array("<","-",">")
    val counts = Array(2,10,2)
    val pairs = symbols.zip(counts)
    println(pairs.mkString(","))
    //转换成集合
    pairs.toMap
  }
}
