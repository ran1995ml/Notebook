# SpringBoot

## 自动配置

默认的包结构

- 主程序所在包及下面的所有子包里面的组件都会被默认扫描出来
- 无需包扫描配置
- 若要改变扫描路径

```java
@SpringBootApplication(scanBasePackages="")
```

- 各种配置都有默认值，默认配置最终映射到MultipartProperties，配置文件的值最终会绑定每个类上，这个类会在容器中创建对象
- 自动配置功能都在spring-boot-autoconfigure包里


## 注解

```java
@Configuration  //告诉SpringBoot这是个配置类
@Bean  //方法级别的注解，给容器中添加组件，以方法名作为组件id，返回类型是组件类型，返回值是组件在容器中的实例
@Import(A.class,B.class) //给容器自动创建出组件，默认名字是全类名
@ConditionalOnBean(name="tom")  //容器中有tom组件时，描述项下的注入组件才生效
@ImportResource(“classpath:**xml) //注入xml配置的组件
@Component @Controller @Service @Repository  //类级别的注解，给容器添加组件

//配置绑定
@ConfigurationProperties+@Component  //将properties中的内容封装到JavaBean，必须加载到容器中
@EnableConfigurationProperties  //开启配置绑定功能，同时将组件注入容器，用于配置类中
```

## 单元测试

断言，检查返回数据是否合理，所有测试结束后，会有详细的测试报告。

前面的断言失败，后面的断言不会执行。

