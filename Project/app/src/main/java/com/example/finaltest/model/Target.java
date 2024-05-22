package com.example.finaltest.model;

import java.io.Serializable;

public class Target implements Serializable {
    private int id;
    private int userId;
    private double height;
    private double weight;
    private double weightTarget;
    private int age;
    private double r;
    private int sex;
    private String date;
    public Target() {
    }

    public Target(int id, int userId, double height, double weight, double weightTarget, int age, double r, int sex, String date) {
        this.id = id;
        this.userId = userId;
        this.height = height;
        this.weight = weight;
        this.weightTarget = weightTarget;
        this.age = age;
        this.r = r;
        this.sex = sex;
        this.date = date;
    }

    public Target(int userId, double height, double weight, double weightTarget, int age, double r, int sex, String date) {
        this.userId = userId;
        this.height = height;
        this.weight = weight;
        this.weightTarget = weightTarget;
        this.age = age;
        this.r = r;
        this.sex = sex;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeightTarget() {
        return weightTarget;
    }

    public void setWeightTarget(double weightTarget) {
        this.weightTarget = weightTarget;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Target{" +
                "id=" + id +
                ", userId=" + userId +
                ", height=" + height +
                ", weight=" + weight +
                ", weightTarget=" + weightTarget +
                ", age=" + age +
                ", r=" + r +
                ", sex=" + sex +
                ", date='" + date + '\'' +
                '}';
    }
}
