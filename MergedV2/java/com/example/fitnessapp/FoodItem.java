package com.example.fitnessapp;

public class FoodItem {

    String email;
    String mealType;
    String name;
    String carbs;
    String fiber;
    String sugars;
    String fat;
    String unsaturatedFat;
    String saturatedFat;
    String other;
    String cholestrol;
    String pottasium;

    public FoodItem(){

    }

    public FoodItem(String email, String mealType, String name, String carbs, String fiber, String sugars, String fat, String unsaturatedFat, String saturatedFat, String other, String cholestrol, String pottasium) {
        this.email = email;
        this.mealType = mealType;
        this.name = name;
        this.carbs = carbs;
        this.fiber = fiber;
        this.sugars = sugars;
        this.fat = fat;
        this.unsaturatedFat = unsaturatedFat;
        this.saturatedFat = saturatedFat;
        this.other = other;
        this.cholestrol = cholestrol;
        this.pottasium = pottasium;
    }

    public String getEmail() {
        return email;
    }

    public String getMealType() {
        return mealType;
    }

    public String getName() {
        return name;
    }

    public String getCarbs() {
        return carbs;
    }

    public String getFiber() {
        return fiber;
    }

    public String getSugars() {
        return sugars;
    }

    public String getFat() {
        return fat;
    }

    public String getUnsaturatedFat() {
        return unsaturatedFat;
    }

    public String getSaturatedFat() {
        return saturatedFat;
    }

    public String getOther() {
        return other;
    }

    public String getCholestrol() {
        return cholestrol;
    }

    public String getPottasium() {
        return pottasium;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public void setFiber(String fiber) {
        this.fiber = fiber;
    }

    public void setSugars(String sugars) {
        this.sugars = sugars;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public void setUnsaturatedFat(String unsaturatedFat) {
        this.unsaturatedFat = unsaturatedFat;
    }

    public void setSaturatedFat(String saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void setCholestrol(String cholestrol) {
        this.cholestrol = cholestrol;
    }

    public void setPottasium(String pottasium) {
        this.pottasium = pottasium;
    }
}
