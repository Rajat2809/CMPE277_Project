package com.example.fitnessapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private EditText FullName,email,pass;
    private Button btnSignUp;
    private TextView tvLogin;
   private FirebaseAuth firebaseAuth;


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
                Boolean valid=false;
                if(fuName.isEmpty() || userEmail.isEmpty() || userPass.isEmpty()){
                    Toast.makeText(SignUp.this, "Fill Up all details.", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.createUserWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                sendUserData();
                                Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, com.example.fitnessapp.MainActivity.class));
                            }else{
                                Toast.makeText(SignUp.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

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
        FullName = (EditText)findViewById(R.id.etFullProfileName);
        email = (EditText)findViewById(R.id.etUserEmail);
        pass = (EditText)findViewById(R.id.etPass);
        btnSignUp = (Button)findViewById(R.id.btnUpdate);
        tvLogin = (TextView)findViewById(R.id.tvLogin);
    }
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        User user = new User(email.getText().toString().trim(),pass.getText().toString().trim(),FullName.getText().toString().trim());
        if(databaseReference.setValue(user).isSuccessful()){
            firebaseAuth.signOut();
            Toast.makeText(this, "Uploading User Data Successful", Toast.LENGTH_SHORT).show();
        }
    }
}
