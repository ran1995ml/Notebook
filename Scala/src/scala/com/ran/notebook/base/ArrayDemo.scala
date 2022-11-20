package com.ran.notebook.base

import java.util
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer
import scala.collection.JavaConversions.bufferAsJavaList
import scala.collection.JavaConversions.asScalaBuffer


/**
 * @ClassName: ArrayDemo
 * @Description:
 * @Author: rwei
 * @Date: 2022/11/16 10:52
 */
object ArrayDemo {
  def main(args: Array[String]): Unit = {
    val s = Array("Hello","World")

    val b = ArrayBuffer[Int]()
    b += 1
    b += (1,2,3,5)
    println(b)
    b ++= Array(8,13,21)
    b.trimEnd(5)
    b.insert(2,7,8,9,10,11,12,15)
    b.remove(2,3)
    //array和buffer转换
    val array = b.toArray
    val buffer = array.toBuffer

    for(i<-0 until array.length)
      println(i+": "+array(i))

    //间隔两个遍历
    for(i<-(0 until (array.length,2)))
      println(i+": "+array(i))

    //从尾端遍历
    for(i<-(0 until array.length).reverse)
      println(i+": "+array(i))

    for(elem<-array)
      println(elem)

    //产生新数组
    val a = Array(-2,-3,5,7,9,10,11)
    val result = for(elem<-a) 2 * elem
    val result1 = for(elem<-a if elem%2==0) yield 2 * elem
    val result2 = a.filter(_%2==0).map(2*_)
    println(result2.mkString(","))

    //移除第一个负数之外的所有负数
    var first = true
    //筛选出要保留元素的下标
    val indexes = for(i<-0 until a.length if first || a(i)>=0) yield {
      if(a(i)<0) first = false
      i
    }
    //要保留的元素移到前面，截去末尾
    for(j<-0 until indexes.length) a(j) = a(indexes(j))
    a.toBuffer.trimEnd(a.length-indexes.length)
    println(indexes.mkString(","))

    //排序
    val sum = Array(1, 7, 2, 9).sum
    val c = ArrayBuffer(1,7,2,9)
    val ints = c.sorted.reverse
    val ints1 = c.sortWith(_ > _)
    println(ints.mkString(","))

    //创建多维数组
    val matrix = Array.ofDim[Double](3, 4)
    matrix(0)(0) = 42;
    //创建不规则的数组
    val triangle = new Array[Array[Int]](10)
    for(i<-0 until triangle.length)
      triangle(i) = new Array[Int](i+1)

    //与Java互操作
    val command = ArrayBuffer("ls", "aa")
    val builder = new ProcessBuilder(command)
    val strings: util.List[String] = builder.command() //scala->java
    val cmd:Buffer[String] = builder.command() //java-scala

  }

}
