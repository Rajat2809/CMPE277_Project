package com.example.fitnessapp;

public class FoodItem {

    String email;
    Food food;
    String date;

    public FoodItem(){

    }

    public FoodItem(String email, Food food, String date) {
        this.email = email;
        this.food = food;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
