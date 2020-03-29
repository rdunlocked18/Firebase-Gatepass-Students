package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailId,password;
    Button loginButton;
    TextView signup;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        firebaseAuth = FirebaseAuth.getInstance();
        emailId = (EditText)findViewById(R.id.Email);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login);
        signup = (TextView) findViewById(R.id.signup_here);

         preferences = getApplicationContext().getSharedPreferences("superPref", 0); // 0 - for private mode










        mAuthStateListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mfirebaseUser = firebaseAuth.getCurrentUser();
                if(mfirebaseUser != null ){
                    Toast.makeText(getApplicationContext(),"You Are Logged in",Toast.LENGTH_SHORT).show();
                            if (!preferences.getBoolean("profile", false)) {
                            Intent login = new Intent(LoginActivity.this, ActivityCreate.class);
                            startActivity(login);
                        }
                        else {
                            Intent login = new Intent(LoginActivity.this, Dashboard.class);
                            startActivity(login);

                        }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        firebaseAuth.signInWithEmailAndPassword(emailId.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    if (!preferences.getBoolean("profile", false)) {
                                        Intent login = new Intent(LoginActivity.this, ActivityCreate.class);
                                        startActivity(login);
                                    }
                                    else {
                                        Intent login = new Intent(LoginActivity.this, Dashboard.class);
                                        startActivity(login);

                                    }



                                }

                            }
                        });

            }
        });



    }

    @Override
    public void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
