package com.example.finaltest.model;

import java.io.Serializable;

public class FoodDaily implements Serializable {
    private int id;
    private int userId;
    private Food food;
    private int weight;
    private double totalKCal;
    private String date;
    private String meal;

    public FoodDaily(int id, int userId, Food food, int weight, double totalKCal, String date, String meal) {
        this.id = id;
        this.userId = userId;
        this.food = food;
        this.weight = weight;
        this.totalKCal = totalKCal;
        this.date = date;
        this.meal = meal;
    }

    public FoodDaily(int userId, Food food, int weight, String date, String meal) {
        this.userId = userId;
        this.food = food;
        this.weight = weight;
        this.date = date;
        this.meal = meal;
    }

    public FoodDaily() {
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

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getTotalKCal() {
        return this.getWeight() * this.getFood().getCalories();
    }

    public void setTotalKCal(double totalKCal) {
        this.totalKCal = totalKCal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }
}
