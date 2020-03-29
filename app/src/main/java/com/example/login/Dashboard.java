package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.MODE_PRIVATE;

public class Dashboard extends AppCompatActivity {
    CardView dashcard1,dashcard3,dashcard2,dashcard4;
    TextView welcomes;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    FirebaseAuth firebaseAuth;
    Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dashcard1 = (CardView)findViewById(R.id.dashcard1);
        dashcard3 = (CardView)findViewById(R.id.dashcard3);
        dashcard2 = (CardView)findViewById(R.id.dashcard2);
        dashcard4 = (CardView)findViewById(R.id.dashcard4);
        welcomes = (TextView)findViewById(R.id.welcomes);
        signout = findViewById(R.id.logout);



        prefs = getSharedPreferences("superPref", MODE_PRIVATE);
        editor = prefs.edit();
        String nameget = prefs.getString("name",null);
        String rollget = prefs.getString("roll",null);
        welcomes.setText("Welcome , " +nameget.toUpperCase());

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                editor.clear();
                editor.apply();
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    vibe.vibrate(100);

                startActivity(new Intent(Dashboard.this, SignupActivity.class));
                Toast.makeText(getApplicationContext(),"You have been Logged out Successfully!",Toast.LENGTH_SHORT).show();

            }
        });

        dashcard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this,RequestPass.class);
                startActivity(i);
            }
        });

        dashcard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create same activty as Activity Cara  and UPdate Refrences + logics
                Intent i = new Intent(Dashboard.this,UpdateProfile.class);
                startActivity(i);
            }
        });

        dashcard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this,CheckStatus.class);
                startActivity(i);
            }
        });
        dashcard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"Feature Yet To Implement",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
