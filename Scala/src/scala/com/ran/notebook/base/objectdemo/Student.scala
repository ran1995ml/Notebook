package com.ran.notebook.base.objectdemo

/**
 * @ClassName: Student
 * @Description:
 * @Author: rwei
 * @Date: 2022/11/26 19:19
 */

class Student(name:String,age:Int){
  def printInfo():Unit={
    println(name+" "+age+" "+Student.school)
  }
}

object Student {
  val school:String = "nuaa"
  def main(args: Array[String]): Unit = {
    val s1 = new Student("s1",20)
    s1.printInfo()
  }
}
