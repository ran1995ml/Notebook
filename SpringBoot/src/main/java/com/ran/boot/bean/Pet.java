package com.ran.boot.bean;

/**
 * @ClassName: Pet
 * @Description:
 * @Author: rwei
 * @Date: 2022/10/31 23:30
 */
public class Pet {
    private String name;

    public Pet(String name) {
        this.name = name;
    }

    public Pet() {
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
