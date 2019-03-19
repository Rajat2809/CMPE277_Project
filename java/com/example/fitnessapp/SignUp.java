package com.example.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapp.R;

public class SignUp extends AppCompatActivity {
    private EditText FullName,email,pass;
    private Button btnSignUp;
    private TextView tvLogin;
  //  DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        validate();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fuName = FullName.getText().toString().trim();
                String userEmail = email.getText().toString().trim();
                String userPass = pass.getText().toString().trim();
//                dbHandler =new DBHandler(SignUp.this,null,null,1);
//                Data_Signup signup = new Data_Signup(fuName,userEmail,userPass);
//                dbHandler.addSignUp(signup);
                if(fuName.isEmpty() || userEmail.isEmpty() || userPass.isEmpty()){
                    Toast.makeText(SignUp.this, "Fill Up all details.", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(SignUp.this, com.example.fitnessapp.MainActivity.class));
                }


            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(SignUp.this, com.example.fitnessapp.MainActivity.class));
            }
        });



    }
    private void validate(){
        FullName = (EditText)findViewById(R.id.etFullName);
        email = (EditText)findViewById(R.id.etUserEmail);
        pass = (EditText)findViewById(R.id.etPass);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        tvLogin = (TextView)findViewById(R.id.tvLogin);
    }
}
