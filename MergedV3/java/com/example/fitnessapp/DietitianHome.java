package com.example.fitnessapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class DietitianHome extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    PieChartView pieChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietitian_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(DietitianHome.this,"Welcome", Toast.LENGTH_LONG).show();

     /*   pieChartView = findViewById(R.id.chart);

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(15, Color.CYAN).setLabel("Q1: $10"));
        pieData.add(new SliceValue(25, Color.GRAY).setLabel("Q2: $4"));
        pieData.add(new SliceValue(10, Color.RED).setLabel("Q3: $18"));
        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Q4: $28"));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("Sales in million").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);*/
        TabLayout tablayout = (TabLayout)findViewById(R.id.tabs);
        tablayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("PrintLog", tab.getText().toString());
                Context context = DietitianHome.this;
                Intent intent  = null;
                if(tab.getText().toString().equals("View")) {
                    intent = new Intent(context, PreviewFood.class);
                }

                else if(tab.getText().toString().equals("Add")){
                    intent = new Intent(context, AddFood.class);
                }
                else if(tab.getText().toString().equals("Logout"))
                {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    finish();
                   // startActivity(new Intent(DietitianHome.this,MainActivity.class));
                    intent = new Intent(DietitianHome.this,MainActivity.class);
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

      //  firebaseAuth = FirebaseAuth.getInstance();
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
        if(id == R.id.action_add_food)
        {
           Context context = DietitianHome.this;
            Intent intent = new Intent(context,AddFood.class);

            context.startActivity(intent);

        }

        if(id==R.id.action_preview_food)
        {
            Context context = DietitianHome.this;
            Intent intent = new Intent(context,PreviewFood.class);

            context.startActivity(intent);
        }
        if(id==R.id.logOutmenu)
        {
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(DietitianHome.this,MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
