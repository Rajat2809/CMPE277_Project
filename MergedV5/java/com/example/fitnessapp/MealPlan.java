package com.example.fitnessapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MealPlan extends AppCompatActivity {

    ArrayList<String> itemList = new ArrayList<>();
    Firebase firebase;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private static final String TAG = "MealPlan";

    private Button mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        Intent intent = getIntent();
        final String mealPlan = intent.getStringExtra("mealPlan");
        //itemList = intent.getStringArrayListExtra("itemList");

        final ListView listView = (ListView)findViewById(R.id.mealPlan);
        final CustomAdapter adapter = new CustomAdapter(itemList,MealPlan.this);

        Firebase.setAndroidContext(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebase = new Firebase("https://healthapp-de69e.firebaseio.com/");
        firebaseUser = firebaseAuth.getCurrentUser();

        mDisplayDate = (Button) findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MealPlan.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
                final CustomAdapter adapter = new CustomAdapter(new ArrayList<String>(),MealPlan.this);
                listView.setAdapter(adapter);
                displayCurrentPlan(mealPlan,adapter,listView,date);
            }
        };



        TabLayout tablayout = (TabLayout)findViewById(R.id.usertabs);
        tablayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("PrintLog", tab.getText().toString());
                Context context = MealPlan.this;
                Intent intent  = null;
                if(tab.getText().toString().equals("Home")) {
                    intent = new Intent(context, menu.class);
                }
                if(tab.getText().toString().equals("Share")) {
                    //Log.d("PrintLog", adapter.getItem(0).toString());
                    String message = "My " + mealPlan + " meal plan: ";
                    for(int i = 0 ; i< adapter.getCount(); i++)
                    {
                        message += "\n" + adapter.getItem(i).toString();
                    }


                    Intent mealIntent = new Intent(Intent.ACTION_SEND);

                    mealIntent.putExtra("Meal Plan", message);
                    intent = Intent.createChooser(mealIntent, "Open using one of the following apps: ");
                 }

                else if(tab.getText().toString().equals("Logout"))
                {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    finish();
                    intent = new Intent(MealPlan.this,MainActivity.class);
                }

                context.startActivity(intent);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("PrintLog", tab.toString());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("PrintLog", tab.getText().toString());
                Context context = MealPlan.this;
                Intent intent  = null;
                if(tab.getText().toString().equals("Home")) {
                    intent = new Intent(context, menu.class);
                }
                if(tab.getText().toString().equals("Share")) {
                    String message = "My Meal Plan: ";
                    for (int i = 0; i < adapter.getCount(); i++) {
                        message += "\n" + adapter.getItem(i).toString();
                    }

                    Intent mealIntent = new Intent(Intent.ACTION_SEND);

                    mealIntent.putExtra("Meal Plan", message);

                    intent = Intent.createChooser(mealIntent, "Open using one of the following apps:");
                }

                else if(tab.getText().toString().equals("Logout"))
                {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    finish();
                    intent = new Intent(MealPlan.this,MainActivity.class);
                }

                context.startActivity(intent);
            }
        });

        //itemList.add("TEst");


    }


    public void displayCurrentPlan(final String mealPlan,final CustomAdapter adapter,final ListView listView, final String date)
    {
        firebase.child("foodItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (com.firebase.client.DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    final FoodItem foodItem = snapshot.getValue(FoodItem.class);
                    System.out.println("=====Value Found======="+foodItem.getFood().getName());
                    System.out.println("=====Meal Plan======="+foodItem.getFood().getCategory());
                    //itemList.add(foodItem.getName());
                    MealPlan.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mealPlan.toLowerCase().equalsIgnoreCase(foodItem.getFood().getCategory()) &&
                                    firebaseUser.getEmail().toLowerCase().equalsIgnoreCase(foodItem.getEmail().toLowerCase()) &&
                                    date.toLowerCase().equalsIgnoreCase(foodItem.getDate()))
                            {
                                Log.i("PrintLog", foodItem.getFood().getName());
                                System.out.println("=====Value Found After Condition======="+foodItem.getFood().getName());
                                adapter.addItem(foodItem.getFood().getName());
                                adapter.notifyDataSetChanged();
                            }

                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        listView.setAdapter(adapter);
    }
}
