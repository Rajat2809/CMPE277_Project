package com.example.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    MaterialSearchView materialSearchView;
    private FirebaseAuth firebaseAuth;
    ListView lstView;
    List<String> lstFound = new ArrayList<>();
    private ArrayList<String> foodarray = new ArrayList<>();
    List<String> lstSource = new ArrayList<>();
    int lowCalorieLimit = 100, lowCarbLimit = 7, highProteinLimit = 7, lowFatLimit = 4;
   /* String[] lstSource = {
            "Brown Bread",
            "Wheat Bread",
            "Milk Bread",
            "Italian Bread",
            "Multi grain Bread",
            "Greek yogurt",
            "Full Fat yogurt"
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

       android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search Items");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        final CheckBox lowCalorieCheck = (CheckBox)findViewById(R.id.lowCalorieCheck);
        final CheckBox lowCarbsCheck = (CheckBox)findViewById(R.id.lowCarbsCheck);
        final CheckBox highProteinCheck = (CheckBox)findViewById(R.id.highProteinCheck);
        final CheckBox lowFatCheck = (CheckBox)findViewById(R.id.lowFatCheck);
        Button filterButton = (Button) findViewById(R.id.filterButton);
        loadUI();

        Intent intent = getIntent();
        final String userIntent = intent.getStringExtra("userIntent");
        final String email = intent.getStringExtra("email");
        final CustomAdapter ca = new CustomAdapter(foodarray,Search.this);
        firebaseAuth = FirebaseAuth.getInstance();

        filterButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                lstSource = new ArrayList<>();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("food");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // Result will be holded Here
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            Log.i("PrintLog", "Food item " + dsp.getValue());
                            final Food food =   dsp.getValue(Food.class);
                            //boolean calorie = true, fat= true, protein = true, carbs = true;
                            boolean add = true;
                            Log.i("PrintLog", "lowCalorieCheck" +lowCalorieCheck.isChecked());
                            if(lowCalorieCheck.isChecked() && !food.getcalories().equals("") && Integer.parseInt(food.getcalories())>lowCalorieLimit) {

                                //  calorie = false;
                                Log.i("PrintLog", "lowCalorieCheckLoop" +food.getcalories());
                                Log.i("PrintLog", "lowCalorieCheckLoop" +lowCalorieLimit);
                                add = false;
                            }
                            if(lowFatCheck.isChecked()&& !food.getCarbohydrates().equals("") && Double.parseDouble(food.getFat())>lowFatLimit) {

                                // fat = false;
                                add = false;
                            }
                            if(lowCarbsCheck.isChecked() && !food.getCarbohydrates().equals("")&& Double.parseDouble(food.getCarbohydrates())>lowCarbLimit) {

                                //   carbs = false;
                                add = false;
                            }
                            if(highProteinCheck.isChecked() && !food.getProtein().equals("")&& Double.parseDouble(food.getProtein())<highProteinLimit) {

                                //  protein = false;
                                add = false;
                            }
                            Log.i("PrintLog", "add" +add);
                            //if(calorie && fat && carbs && protein)
                            if(add)
                                lstSource.add(food.getname()); //add result into array list

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            });

        TabLayout tablayout = (TabLayout)findViewById(R.id.usertabs);
        tablayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("PrintLog", tab.getText().toString());
                Context context = Search.this;
                Intent intent  = null;
                if(tab.getText().toString().equals("Home")) {
                    intent = new Intent(context, menu.class);
                }

                else if(tab.getText().toString().equals("Logout"))
                {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(Search.this,MainActivity.class));
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
                Log.d("PrintLog", tab.getText().toString());
                Context context = Search.this;
                Intent intent  = null;
                if(tab.getText().toString().equals("Home")) {
                    intent = new Intent(context, menu.class);
                }

                else if(tab.getText().toString().equals("Logout"))
                {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(Search.this,MainActivity.class));
                }

                context.startActivity(intent);
            }
        });


        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
               // ArrayAdapter adapter = new ArrayAdapter(Search.this, android.R.layout.simple_list_item_1,lstSource);
                ArrayAdapter adapter = new ArrayAdapter(Search.this, android.R.layout.simple_list_item_1,lstSource);

                lstView.setAdapter(adapter);
            }
        });

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                System.out.println("=====================submit:::=============="+query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText != null && !newText.isEmpty()){
                    lstFound = new ArrayList<String>();
                    for(String item: lstSource){
                        if(item.toLowerCase().contains(newText.toLowerCase()))
                            lstFound.add(item);
                    }

                    ArrayAdapter adapter = new ArrayAdapter(Search.this, android.R.layout.simple_list_item_1,lstFound);
                    lstView.setAdapter(adapter);
                }
                else{
                    //If search text is null
                    //return source list
                 //   ArrayAdapter adapter = new ArrayAdapter(Search.this, android.R.layout.simple_list_item_1,lstSource);
                    ArrayAdapter adapter = new ArrayAdapter(Search.this, android.R.layout.simple_list_item_1,lstSource);

                    lstView.setAdapter(adapter);
                }
                return true;
            }
        });

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String foodItem = "";
                if(lstFound!=null && lstFound.size()>0) {
                    System.out.println("===========Search from Filtered List::===============" + lstFound.get(position));
                    foodItem = lstFound.get(position);
                }
                else {
                    System.out.println("===========Search from Original List::===============" + lstSource.get(position));
                    foodItem = lstSource.get(position);
                }


                Intent addItem = new Intent(Search.this, com.example.fitnessapp.addItem.class);
                addItem.putExtra("selectedItem",foodItem);
                addItem.putExtra("userIntent",userIntent);
                addItem.putExtra("email",email);
                Search.this.startActivity(addItem);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(item);
        return true;
    }


    private void loadUI()
    {
        materialSearchView = (MaterialSearchView)findViewById(R.id.search_view);
        lstView = (ListView)findViewById(R.id.lstView);
    }
}
