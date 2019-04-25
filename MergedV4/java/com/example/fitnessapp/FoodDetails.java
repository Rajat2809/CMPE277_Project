package com.example.fitnessapp;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class FoodDetails extends AppCompatActivity {
    private boolean updated = false;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        TabLayout tablayout = (TabLayout)findViewById(R.id.tabs);
        tablayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("PrintLog", tab.getText().toString());
                Context context = FoodDetails.this;
                Intent intent  = null;
                if(tab.getText().toString().equals("Preview Food")) {
                    intent = new Intent(context, PreviewFood.class);
                }
                else if(tab.getText().toString().equals("Add Food")){
                    intent = new Intent(context, AddFood.class);
                }
                else if(tab.getText().toString().equals("Logout"))
                {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(FoodDetails.this,MainActivity.class));
                }

                context.startActivity(intent);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("PrintLog", tab.toString());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("PrintLog", tab.toString());
            }
        });

        //final DatabaseHelper db = new DatabaseHelper(FoodDetails.this);
        final String name =  getIntent().getStringExtra("Food Name");
        //final Cursor res = db.retrieveDetails(db.getWritableDatabase(),name,"username");

        final TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        final TextView caloriesTextView = (TextView) findViewById(R.id.caloriesTextView);
        final TextView carbohydratesTextView = (TextView) findViewById(R.id.carbohydratesTextView);
        final TextView proteinTextView = (TextView) findViewById(R.id.proteinTextView);
        final TextView fatTextView = (TextView) findViewById(R.id.fatTextView);

        nameTextView.setText("Name: " + name);

        firebaseAuth = FirebaseAuth.getInstance();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("food");
        Log.i("PrintLog", "FD Created");

        database.orderByChild("username_food").equalTo(firebaseAuth.getCurrentUser().getEmail() + "_" + name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    final Food food =   data.getValue(Food.class);
                    caloriesTextView.setText("Calories: " + food.getcalories());
                    carbohydratesTextView.setText("Carbohydrates: " + food.getCarbohydrates());
                    proteinTextView.setText("Protein: " + food.getFat());
                    fatTextView.setText("Fat: "+ food.getFat());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/*
        while (res.moveToNext()) {

            FoodDetails.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    caloriesTextView.setText(caloriesTextView.getText().toString() + res.getString(0));
                    carbohydratesTextView.setText(carbohydratesTextView.getText().toString() + res.getString(1));
                    proteinTextView.setText(proteinTextView.getText().toString() + res.getString(2));
                    fatTextView.setText(fatTextView.getText().toString() + res.getString(3));

                }
            });
        }*/

        Button editButton = (Button) findViewById(R.id.editButton);

        editButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Context context = FoodDetails.this;
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final TextView dialogName = new TextView(context);
                dialogName.setText("Name");
                layout.addView(dialogName);
                final EditText nameEditText = new EditText(context);
                layout.addView(nameEditText);

                final TextView dialogCalories = new TextView(context);
                dialogCalories.setText("Calories");
                layout.addView(dialogCalories);
                final EditText caloriesEditText = new EditText(context);
                layout.addView(caloriesEditText);

                final TextView dialogCarbohydrates = new TextView(context);
                dialogCarbohydrates.setText("Carbohydrates");
                layout.addView(dialogCarbohydrates);
                final EditText carbohydratesEditText = new EditText(context);
                layout.addView(carbohydratesEditText);

                final TextView dialogProtein = new TextView(context);
                dialogProtein.setText("Protein");
                layout.addView(dialogProtein);
                final EditText proteinEditText = new EditText(context);
                layout.addView(proteinEditText);

                final TextView dialogFat = new TextView(context);
                dialogFat.setText("Fat");
                layout.addView(dialogFat);
                final EditText fatEditText = new EditText(context);
                layout.addView(fatEditText);

                // dialog.setView(layout); // Again this is a set method, not add
              /*
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                editLayout.setLayoutParams(lp);
*/
                new AlertDialog.Builder(FoodDetails.this)
                        .setTitle("Edit Food")
                        .setView(layout)
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                       // final DatabaseHelper db = new DatabaseHelper(FoodDetails.this);

                                        final String foodName = getIntent().getStringExtra("Food Name");


                                        //  Button editFoodButton = (Button) findViewById(R.id.editFoodButton);


                                        //editFoodButton.setOnClickListener( new View.OnClickListener() {

                                        //  @Override
                                        // public void onClick(View v) {
                                        // final Cursor res = db.retrieve(db.getWritableDatabase(),"username");
                                        final String inputFood = nameEditText.getText().toString();
                                        final String inputCalories = caloriesEditText.getText().toString();
                                        final String inputCarbohydrates = carbohydratesEditText.getText().toString();
                                        final String inputProtein = proteinEditText.getText().toString();
                                        final String inputFat = fatEditText.getText().toString();
                                        Log.i("PrintLog", "Username_Food" + firebaseAuth.getCurrentUser().getEmail() + "_" + name);
                                        //db.update(db.getWritableDatabase(),foodName, inputFood,inputCalories,inputCarbohydrates,inputProtein,inputFat);
                                        database.orderByChild("username_food").equalTo(firebaseAuth.getCurrentUser().getEmail() + "_" + name).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Log.i("PrintLog", "Found food with name " + name);
                                                Food food = new Food();
                                                if (!inputFood.equals("")) {
                                                    food.setname(inputFood.toString());
                                                    food.setUsername_food(firebaseAuth.getCurrentUser().getEmail() + "_" + inputFood);
                                                }
                                                if (!inputCalories.equals(""))
                                                    food.setcalories(inputCalories.toString());
                                                if(!inputCarbohydrates.equals(""))
                                                    food.setCarbohydrates(inputCarbohydrates);
                                                if(!inputProtein.equals(""))
                                                    food.setProtein(inputProtein);
                                                if(!inputFat.equals(""))
                                                    food.setFat(inputFat);

                                                food.setUsername(firebaseAuth.getCurrentUser().getEmail());
                                                food.setUserUid(firebaseAuth.getUid());

                                                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                                    String dsfood = ds.child("name").getValue().toString();
                                                    Log.i("PrintLog + Current name", dsfood);
                                                    ds.getRef().setValue(food);
                                                }

                                            }



                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });





                                        // finish();
/*
                String inputName = foodEditText.getText().toString();
                String inputCalories = caloriesEditText.getText().toString();
                String inputCarbohydrates = carbohydratesEditText.getText().toString();
                String inputProtein = proteinEditText.getText().toString();
                String inputFat = fatEditText.getText().toString();

                db.insert(db.getWritableDatabase(),inputName,inputCalories,inputCarbohydrates,inputProtein,inputFat,"username");
                Toast.makeText(AddFood.this, "Food Option Added For Users", Toast.LENGTH_LONG).show();

*/



                                Toast.makeText(FoodDetails.this, "Food Option Updated", Toast.LENGTH_LONG).show();

                                if(!inputFood.equals(""))
                                {
                                    nameTextView.setText("Name : " + inputFood);
                                }
                                if(!inputCalories.equals(""))
                                {
                                    caloriesTextView.setText("Calories : " + inputCalories);
                                }
                                if(!inputCarbohydrates.equals(""))
                                {
                                    carbohydratesTextView.setText("Carbohydrates : " + inputCarbohydrates);
                                }
                                if(!inputProtein.equals(""))
                                {
                                    proteinTextView.setText("Protein : " + inputProtein);
                                }
                                if(!inputFat.equals(""))
                                {
                                    fatTextView.setText("Fat : " + inputFat);
                                }
                                        /*
                                Intent intent = getIntent();
                                if(!inputFood.equals(""))
                                     intent.putExtra("Food Name", inputFood);

                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                    //}
                                //});

*/
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(FoodDetails.this, "Canceled", Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        if(id==R.id.action_home)
        {
            Context context = FoodDetails.this;
            Intent intent = new Intent(context,MainActivity.class);

            context.startActivity(intent);
        }
        if(id == R.id.action_add_food)
        {
            Context context = FoodDetails.this;
            Intent intent = new Intent(context,AddFood.class);

            context.startActivity(intent);

        }

        if(id==R.id.action_preview_food)
        {
            Context context = FoodDetails.this;
            Intent intent = new Intent(context,PreviewFood.class);

            context.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
