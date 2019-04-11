package com.example.fitnessapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class DietitianHome extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietitian_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(DietitianHome.this,"Reached DH", Toast.LENGTH_LONG).show();
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
