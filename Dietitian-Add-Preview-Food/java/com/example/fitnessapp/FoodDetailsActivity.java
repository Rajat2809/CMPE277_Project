package com.application.project.android;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class FoodDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final DatabaseHelper db = new DatabaseHelper(FoodDetailsActivity.this);
        String name =  getIntent().getStringExtra("Food Name");
        final Cursor res = db.retrieveDetails(db.getWritableDatabase(),name,"username");

        final TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        final TextView caloriesTextView = (TextView) findViewById(R.id.caloriesTextView);
        final TextView nutrientsTextView = (TextView) findViewById(R.id.nutrientsTextView);

        nameTextView.setText(nameTextView.getText().toString() + name);

        while (res.moveToNext()) {

            FoodDetailsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    caloriesTextView.setText(caloriesTextView.getText().toString() + res.getString(0));
                    nutrientsTextView.setText(nutrientsTextView.getText().toString() + res.getString(1));

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Context context = FoodDetailsActivity.this;
            Intent intent = new Intent(context,MainActivity.class);

            context.startActivity(intent);
        }
        if(id == R.id.action_add_food)
        {
            Context context = FoodDetailsActivity.this;
            Intent intent = new Intent(context,AddFoodActivity.class);

            context.startActivity(intent);

        }

        if(id==R.id.action_preview_food)
        {
            Context context = FoodDetailsActivity.this;
            Intent intent = new Intent(context,PreviewFoodActivity.class);

            context.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
