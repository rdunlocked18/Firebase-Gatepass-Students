package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    EditText emailId,password;
    Button btnSignUp;
    TextView tvlogin;
    FirebaseAuth firebaseAuth;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String email,pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        emailId = (EditText)findViewById(R.id.Email);
        password = (EditText)findViewById(R.id.password);
        btnSignUp = (Button) findViewById(R.id.sign_up);
        tvlogin = (TextView) findViewById(R.id.login_here);
        prefs = getSharedPreferences("superPref", MODE_PRIVATE);
        editor = prefs.edit();
        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    email = emailId.getText().toString();
                 pwd = password.getText().toString();
                if (email.isEmpty()){
                    emailId.setError("Please enter the email Id");
                    emailId.requestFocus();

                }
                else if(pwd.isEmpty()){
                    password.setError("enter the password");
                    password.requestFocus();

                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(SignupActivity.this,"Fields are empty",Toast.LENGTH_SHORT).show();

                }
                else if(!(email.isEmpty() && pwd.isEmpty())){
                    firebaseAuth.createUserWithEmailAndPassword(email , pwd ).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(SignupActivity.this, "Signup Unsuccessful , please try again", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Sign up Successful You Will be logged in automatically",Toast.LENGTH_SHORT).show();
                                editor.putString("email",emailId.getText().toString());
                                Log.e("email",emailId.getText().toString());
                                editor.apply();
                                startActivity(new Intent(SignupActivity.this,ActivityCreate.class));


                            }
                        }
                    });
                }
            else {
                Toast.makeText(SignupActivity.this,"Error occured",Toast.LENGTH_SHORT).show();;
                }
            }
        });



        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this , LoginActivity.class);
                startActivity(i);
            }
        });

    }
}
