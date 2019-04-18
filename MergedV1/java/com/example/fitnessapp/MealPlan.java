package com.example.fitnessapp;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MealPlan extends AppCompatActivity {

    ArrayList<String> itemList = new ArrayList<>();
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        Intent intent = getIntent();
        //itemList = intent.getStringArrayListExtra("itemList");

        final ListView listView = (ListView)findViewById(R.id.mealPlan);
        final FoodItemAdapter adapter = new FoodItemAdapter(itemList,MealPlan.this);

        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://healthapp-de69e.firebaseio.com/");

        //itemList.add("TEst");

        firebase.child("foodItem").orderByChild("mealType").equalTo("breakfast").addValueEventListener(new ValueEventListener() {
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
