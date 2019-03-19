package com.example.fitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapp.R;

public class MainActivity extends AppCompatActivity {
    private EditText email,pass;
    private Button btLogin;
    private TextView tvSignUp;
   // DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        validate();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString().trim();
                String userPass = pass.getText().toString().trim();
               // dbHandler =new DBHandler(MainActivity.this,null,null,1);
                String dbEmail = "";
                //dbEmail = dbHandler.databaseTOString(userEmail,userPass);

                if(userEmail.isEmpty() || userPass.isEmpty()){
                    // Toast.makeText(MainActivity.this, "Enter all details", Toast.LENGTH_SHORT).show();
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Enter all details");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
//                else if(dbEmail.isEmpty()){
//                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                    alertDialog.setTitle("Alert");
//                    alertDialog.setMessage("UserName or Password does not matches with records.");
//                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.show();
//                }
                   else{
                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent dataIntent = new Intent(MainActivity.this,Home.class);
                    dataIntent.putExtra("email",userEmail);
                    startActivity(dataIntent);

                }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });

    }
    private void validate(){
        email = (EditText)findViewById(R.id.etEmail);
        pass = (EditText)findViewById(R.id.etPass);
        btLogin = (Button)findViewById(R.id.btnLogin);
        tvSignUp = (TextView)findViewById(R.id.tvSignUP);
    }

}
