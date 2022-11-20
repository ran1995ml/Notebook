package com.ran.boot.config;

import com.ran.boot.bean.Car;
import com.ran.boot.bean.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @ClassName: MyConfig
 * @Description:
 * 1.配置类本身也是组件
 * 2.proxyBeanMethods = true表示代理对象调用方法，SpringBoot总会检查该组件是否在容器中
 *   如果该组件没有其他组件依赖，调成false速度会更快
 * @Author: rwei
 * @Date: 2022/10/31 23:33
 */

@Import({Pet.class})
@Configuration(proxyBeanMethods = true)
@ConditionalOnBean(name = "tom") //tom存在时，tom1才生效
@EnableConfigurationProperties(Car.class) //开启配置绑定功能，将组件注入容器
public class MyConfig {

    @Bean("tom") //默认是单实例，无论调用多少次都是注册到容器中的单实例
    public Pet tomcatPet(){
        return new Pet("tomcat");
    }

    @Bean("tom1") //默认是单实例，无论调用多少次都是注册到容器中的单实例
    public Pet tomcatPet1(){
        return new Pet("tomcat");
    }
}
