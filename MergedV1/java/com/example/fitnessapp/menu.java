package com.example.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class menu extends AppCompatActivity {

    private Button breakfast;
    private Button lunch;
    private Button dinner;
    private Button snack;

    private TextView txtBreakfastCal;
    private TextView txtLunchCal;
    private TextView txtDinnerCal;
    private TextView txtSnackCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setupUI();


        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent breakfastIntent = new Intent(menu.this, Search.class);
                breakfastIntent.putExtra("userIntent",getResources().getString(R.string.breakfast_menu_option));
                startActivity(breakfastIntent) ;
            }
        });

        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent breakfastIntent = new Intent(menu.this, Search.class);
                breakfastIntent.putExtra("userIntent",getResources().getString(R.string.lunch_menu_option));
                startActivity(breakfastIntent) ;
            }
        });

        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent breakfastIntent = new Intent(menu.this, Search.class);
                breakfastIntent.putExtra("userIntent",getResources().getString(R.string.dinner_menu_option));
                startActivity(breakfastIntent) ;
            }
        });

        snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent breakfastIntent = new Intent(menu.this, Search.class);
                breakfastIntent.putExtra("userIntent",getResources().getString(R.string.snack_menu_option));
                startActivity(breakfastIntent) ;
            }
        });

        verifyIntent();
    }

    private void setupUI(){

        breakfast = (Button)findViewById(R.id.buttonBreakfast);
        lunch = (Button)findViewById(R.id.buttonLunch);
        dinner = (Button)findViewById(R.id.buttonDinner);
        snack = (Button)findViewById(R.id.buttonSnack);

        txtBreakfastCal = (TextView) findViewById(R.id.textbrkfstCalRec);
        txtLunchCal = (TextView) findViewById(R.id.textlnchCalRec);
        txtSnackCal = (TextView) findViewById(R.id.textsnackCalRec);
        txtDinnerCal = (TextView) findViewById(R.id.textdinnerCalRec);
    }

    private void verifyIntent()
    {
        //Verify Intent
        Intent intent = getIntent();
        String calRecBrkfst = intent.getStringExtra("BreakfastCaloriesLeft");
        String calRecLnch = intent.getStringExtra("LunchCaloriesLeft");
        String calRecDinner = intent.getStringExtra("DinnerCaloriesLeft");
        String calRecSnack = intent.getStringExtra("SnackCaloriesLeft");

        if(calRecBrkfst!=null)
            txtBreakfastCal.setText(calRecBrkfst+" CALS UNDER");
        else if(calRecLnch!=null)
            txtLunchCal.setText(calRecLnch+" CALS UNDER");
        else if(calRecDinner!=null)
            txtDinnerCal.setText(calRecDinner+" CALS UNDER");
        else if(calRecSnack!=null)
            txtSnackCal.setText(calRecSnack+" CALS UNDER");
        else {
            txtLunchCal.setText(getResources().getString(R.string.lunch_recommendation));
            txtBreakfastCal.setText(getResources().getString(R.string.breakfast_recommendation));
            txtDinnerCal.setText(getResources().getString(R.string.dinner_recommendation));
            txtSnackCal.setText(getResources().getString(R.string.snack_recommendation));
        }


    }
}
