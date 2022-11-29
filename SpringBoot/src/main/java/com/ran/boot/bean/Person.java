package com.ran.boot.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @ClassName: Person
 * @Description:
 * @Author: rwei
 * @Date: 2022/11/3 22:18
 */

@ConfigurationProperties(prefix = "person")
@Component
@ToString
@Data
public class Person {
    private String userName;
    private Boolean boss;
    private Date birth;
    private Integer age;
    private Pet pet;
    private String[] interests;
    private List<String> animal;
    private Map<String, Object> score;
    private Set<Double> salarys;
    private Map<String,List<Pet>> allPets;
}
