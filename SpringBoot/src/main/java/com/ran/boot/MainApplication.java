package com.ran.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ClassName: MainApplication
 * @Description: SpringBoot启动类
 * @Author: rwei
 * @Date: 2022/10/30 23:25
 */

//声明这是一个SpringBoot应用
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        //返回ioc容器
        ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);
        //查看容器里的组件
        String[] names = run.getBeanDefinitionNames();

//        Object tom = run.getBean("tom");
    }
}
