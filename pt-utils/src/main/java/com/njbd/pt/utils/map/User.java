package com.njbd.pt.utils.map;

/**
 * Created by 李建成
 *
 * @date 2016/12/2-16:21.on NJBD
 */
public class User {


    private String name;
    private String age;
    private String job;


    public User() {

    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String age, String job) {
        this.name = name;
        this.age = age;
        this.job = job;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String
    toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", job='" + job + '\'' +
                '}';
    }


}
