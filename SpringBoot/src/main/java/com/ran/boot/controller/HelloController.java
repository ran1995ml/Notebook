package com.ran.boot.controller;


import com.ran.boot.bean.Car;
import com.ran.boot.bean.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: HelloController
 * @Description:
 * @Author: rwei
 * @Date: 2022/10/30 23:29
 */

@RestController
public class HelloController {


    @RequestMapping("/hello")
    public String handle01(){
        return "Hello,Spring Boot!";
    }

    @Autowired
    Car car;


    @RequestMapping("/car")
    public Car car(){
        return car;
    }

    @Autowired
    Person person;

    @RequestMapping("/person")
    public Person person(){
        return person;
    }
}
