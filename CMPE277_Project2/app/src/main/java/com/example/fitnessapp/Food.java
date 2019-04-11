package com.example.fitnessapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Food {

    public String name;
    public String calories;
    public String carbohydrates;
    public String protein;
    public  String fat;
    public String username;
    public String userUid;
    public String username_food;

    public Food() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Food(String name, String calories, String carbohydrates, String protein, String fat, String username, String Uid ) {
        this.name = name;
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.fat = fat;
        this.username = username;
        this.userUid = Uid;
    }

    public String getname()
    {
        return this.name;
    }

    public void setname(String name)
    {
        this.name = name;
    }
    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getUserUid()
    {
        return this.userUid;
    }

    public void setUserUid(String Uid)
    {
        this.userUid = Uid;
    }
    public void setcalories(String calories)
    {
        this.calories = calories;
    }

    public String getcalories()
    {
        return this.calories;
    }

    public void setCarbohydrates(String carbohydrates)
    {
        this.carbohydrates = carbohydrates;
    }

    public String getCarbohydrates()
    {
        return this.carbohydrates;
    }
    public String getProtein()
    {
      return  this.protein;
    }

    public void setProtein(String protein)
    {
       this.protein = protein;
    }
    public void setFat(String fat)
    {
        this.fat = fat;
    }

    public String getFat()
    {
        return this.fat;
    }

    public String getUsername_food()
    {
        return this.username_food;
    }

    public void setUsername_food(String username_food)
    {
        this.username_food = username_food;
    }


}