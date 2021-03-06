package com.example.fitnessapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText email,pass;
    private Button btLogin;
    private TextView tvSignUp;
    private TextView tvForgotPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
   // DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("PrintLog", "MainActivity#onCreate");
        validate();

        firebaseAuth = FirebaseAuth.getInstance();
        Log.i("PrintLog", "firebaseAuth: " + firebaseAuth);
        progressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();
//        Log.i("PrintLog", "user: " + user.toString());
        if(user !=null) {
            Log.i("PrintLog", "user not null");
            finish();
            Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
            if(user.getDisplayName().equals("admin")) {
                Intent dataIntent = new Intent(MainActivity.this, DietitianHome.class);
                dataIntent.putExtra("email", email.getText().toString().trim());
                startActivity(dataIntent);
            }
            //Start user activity
            else
            {
                Intent dataIntent = new Intent(MainActivity.this, UserHome.class);
                dataIntent.putExtra("email", email.getText().toString().trim());
                startActivity(dataIntent);
            }

        }
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SignIn", "Button Clicked");
               // startActivity(new Intent(MainActivity.this,DietitianHome.class));
                validateUser(email.getText().toString().trim(),pass.getText().toString().trim());



            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PasswordReset.class);
                if(!email.getText().toString().trim().isEmpty()) {
                    intent.putExtra("email", email.getText().toString().trim());
                }
                startActivity(intent);
            }
        });

    }
    private void validate(){
        email = (EditText)findViewById(R.id.etEmail);
        pass = (EditText)findViewById(R.id.etPass);
        btLogin = (Button)findViewById(R.id.btnLogin);
        tvSignUp = (TextView)findViewById(R.id.tvSignUP);
        tvForgotPassword =(TextView)findViewById(R.id.tvForgotPassword);
    }

    private void validateUser(final String email, String password){
        Log.d("SignIn", "Validating");
        progressDialog.setMessage("You are about to enter the world of fitness.");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();

                    if(user.getDisplayName().equals("admin")) {
                        Intent dataIntent = new Intent(MainActivity.this, DietitianHome.class);
                        dataIntent.putExtra("email", email);
                        startActivity(dataIntent);
                    }
                    //Start user activity
                    else
                    {
                        Intent dataIntent = new Intent(MainActivity.this, UserHome.class);
                        dataIntent.putExtra("email", email);
                        startActivity(dataIntent);
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

    }

}
