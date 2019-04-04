package com.application.project.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
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

        final DatabaseHelper db = new DatabaseHelper(AddFoodActivity.this);
        final EditText foodEditText = (EditText) findViewById(R.id.addEditText);
        final  EditText caloriesEditText = (EditText) findViewById(R.id.addCaloriesEditText);
        final  EditText carbohydratesEditText = (EditText) findViewById(R.id.addCarbohydratesEditText);
        final  EditText proteinEditText = (EditText) findViewById(R.id.addProteinEditText);
        final  EditText fatEditText = (EditText) findViewById(R.id.addFatEditText);
        Button addFoodButton = (Button) findViewById(R.id.addFoodButton);



        addFoodButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // final Cursor res = db.retrieve(db.getWritableDatabase(),"username");
                String inputName = foodEditText.getText().toString();
                String inputCalories = caloriesEditText.getText().toString();
                String inputCarbohydrates = carbohydratesEditText.getText().toString();
                String inputProtein = proteinEditText.getText().toString();
                String inputFat = fatEditText.getText().toString();

                db.insert(db.getWritableDatabase(),inputName,inputCalories,inputCarbohydrates,inputProtein,inputFat,"username");
                Toast.makeText(AddFoodActivity.this, "Food Option Added For Users", Toast.LENGTH_LONG).show();


            }
        });


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
        if(id == R.id.action_home)
        {
            Context context = AddFoodActivity.this;
            Intent intent = new Intent(context,MainActivity.class);

            context.startActivity(intent);

        }

        if(id==R.id.action_preview_food)
        {
            Context context = AddFoodActivity.this;
            Intent intent = new Intent(context,PreviewFoodActivity.class);

            context.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
