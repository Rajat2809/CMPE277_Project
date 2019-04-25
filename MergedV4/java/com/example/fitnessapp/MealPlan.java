package com.example.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MealPlan extends AppCompatActivity {

    ArrayList<String> itemList = new ArrayList<>();
    Firebase firebase;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        Intent intent = getIntent();
        final String mealPlan = intent.getStringExtra("mealPlan");
        //itemList = intent.getStringArrayListExtra("itemList");

        final ListView listView = (ListView)findViewById(R.id.mealPlan);
        final FoodItemAdapter adapter = new FoodItemAdapter(itemList,MealPlan.this);

        Firebase.setAndroidContext(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebase = new Firebase("https://healthapp-de69e.firebaseio.com/");
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

        firebase.child("foodItem").orderByChild("mealType").equalTo(mealPlan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (com.firebase.client.DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    final FoodItem foodItem = snapshot.getValue(FoodItem.class);
                    System.out.println("=====Value Found======="+foodItem.getName());
                    //itemList.add(foodItem.getName());
                    MealPlan.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("PrintLog",foodItem.getName());
                            adapter.addItem(foodItem.getName());
                            adapter.notifyDataSetChanged();

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
