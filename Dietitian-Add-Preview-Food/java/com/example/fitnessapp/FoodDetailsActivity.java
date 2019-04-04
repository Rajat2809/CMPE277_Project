package com.application.project.android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FoodDetailsActivity extends AppCompatActivity {
    private boolean updated = false;
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
        final TextView carbohydratesTextView = (TextView) findViewById(R.id.carbohydratesTextView);
        final TextView proteinTextView = (TextView) findViewById(R.id.proteinTextView);
        final TextView fatTextView = (TextView) findViewById(R.id.fatTextView);

        nameTextView.setText(nameTextView.getText().toString() + name);

        while (res.moveToNext()) {

            FoodDetailsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    caloriesTextView.setText(caloriesTextView.getText().toString() + res.getString(0));
                    carbohydratesTextView.setText(carbohydratesTextView.getText().toString() + res.getString(1));
                    proteinTextView.setText(proteinTextView.getText().toString() + res.getString(2));
                    fatTextView.setText(fatTextView.getText().toString() + res.getString(3));

                }
            });
        }

        Button editButton = (Button) findViewById(R.id.editButton);

        editButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Context context = FoodDetailsActivity.this;
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
                new AlertDialog.Builder(FoodDetailsActivity.this)
                        .setTitle("Edit Food")
                        .setView(layout)
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final DatabaseHelper db = new DatabaseHelper(FoodDetailsActivity.this);

                                final String foodName = getIntent().getStringExtra("Food Name");



                              //  Button editFoodButton = (Button) findViewById(R.id.editFoodButton);



                                //editFoodButton.setOnClickListener( new View.OnClickListener() {

                                  //  @Override
                                   // public void onClick(View v) {
                                        // final Cursor res = db.retrieve(db.getWritableDatabase(),"username");
                                        String inputFood = nameEditText.getText().toString();
                                        String inputCalories = caloriesEditText.getText().toString();
                                        String inputCarbohydrates = carbohydratesEditText.getText().toString();
                                        String inputProtein = proteinEditText.getText().toString();
                                        String inputFat = fatEditText.getText().toString();

                                        db.update(db.getWritableDatabase(),foodName, inputFood,inputCalories,inputCarbohydrates,inputProtein,inputFat);
                                        Toast.makeText(FoodDetailsActivity.this, "Food Option Updated", Toast.LENGTH_LONG).show();

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
                                Toast.makeText(FoodDetailsActivity.this, "Canceled", Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
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
