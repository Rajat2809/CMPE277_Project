package com.example.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addItem extends AppCompatActivity {

    private TextView itemSelected;
    private TextView textProteinValue;
    private TextView textCarbValue;
    private TextView textFiberValue;
    private TextView textSugarValue;
    private TextView textFatValue;
    private TextView textSatFatValue;
    private TextView textUnSatFatValue;
    private TextView textOthersValue;
    private TextView textCholestrolValue;
    private TextView textPottasiumValue;

    Firebase firebase;

    private Button addToDiary;
    private String selectedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        firebase = new Firebase("https://healthapp-de69e.firebaseio.com");
        setupUI();

        Intent intent = getIntent();
        selectedItem = intent.getStringExtra("selectedItem");
        itemSelected.setText(selectedItem);


        addToDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDiary();
            }
        });
    }


    private void setupUI(){
        itemSelected = (TextView)findViewById(R.id.itemSelected);
        textCarbValue = (TextView)findViewById(R.id.textCarbValue);
        textProteinValue = (TextView)findViewById(R.id.textProteinValue);
        textFiberValue = (TextView)findViewById(R.id.textFiberValue);
        textSugarValue = (TextView)findViewById(R.id.textSugarValue);
        textFatValue = (TextView)findViewById(R.id.textFatValue);
        textSatFatValue = (TextView)findViewById(R.id.textSatFatValue);
        textUnSatFatValue = (TextView)findViewById(R.id.textUnSatFatValue);
        textOthersValue = (TextView)findViewById(R.id.textOthersValue);
        textCholestrolValue = (TextView)findViewById(R.id.textCholestrolValue);
        textPottasiumValue = (TextView)findViewById(R.id.textPottasiumValue);

        addToDiary = findViewById(R.id.buttonDiary);
    }

    private void addToDiary(){

        Intent intent = getIntent();
        String userIntent = intent.getStringExtra("userIntent");
        String email = intent.getStringExtra("email");
        addFoodItems(userIntent,email);

        Intent menuIntent = new Intent(addItem.this,menu.class);

        if(userIntent!=null)
        {
            switch (userIntent)
            {
                case "breakfast":
                    menuIntent.putExtra("BreakfastCaloriesLeft","123");
                    break;
                case "lunch":
                    menuIntent.putExtra("LunchCaloriesLeft","123");
                    break;
                case "dinner":
                    menuIntent.putExtra("DinnerCaloriesLeft","123");
                    break;
                case "snack":
                    menuIntent.putExtra("SnackCaloriesLeft","123");
                    break;
                default:
                    break;
            }
        }
        addItem.this.startActivity(menuIntent);
    }

    private void addFoodItems(String mealType, String email)
    {
        Firebase myChild = firebase.child("foodItem");

        FoodItem foodItem = new FoodItem(email,mealType,selectedItem,"71.4","1.2","1","0.8","0.6","0.2","200","155","45");
        myChild.child(firebase.push().getKey()).setValue(foodItem);
        Toast.makeText(this,"FoodItem Added",Toast.LENGTH_LONG).show();
    }

}
