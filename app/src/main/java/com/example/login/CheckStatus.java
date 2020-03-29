package com.example.login;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;

import java.util.Objects;


public class CheckStatus extends AppCompatActivity {

    RecyclerView mainIterate;
    DatabaseReference databaseReference;
    TextView namefetch, rollFetch, reasonFetch,codeFetch,timeFetch,emailFetch,acceptanceA;
    FloatingActionButton b1;
    TextView hintText;
    FirebaseDatabase mfirebaseDatabase;
    FirebaseApp firebaseApp;
    FirebaseAuth firebaseAuth;
    SharedPreferences.Editor editor ;
    SharedPreferences prefs;
    ProgressDialog progressDoalog;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_status);



        namefetch = findViewById(R.id.nameFetch);
        rollFetch = findViewById(R.id.rollFetch);
        reasonFetch = findViewById(R.id.reasonFetch);
        codeFetch = findViewById(R.id.codeFetch);
        timeFetch = findViewById(R.id.timeFetch);
        emailFetch = findViewById(R.id.emailFetch);
        acceptanceA = findViewById(R.id.acceptanceA);
        hintText = findViewById(R.id.hintText);
        b1 = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView2);

        firebaseAuth = FirebaseAuth.getInstance();
        prefs = getSharedPreferences("superPref", MODE_PRIVATE);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        editor = prefs.edit();

        progressDoalog = new ProgressDialog(CheckStatus.this);
        progressDoalog.setMessage("Wait Excitement might get down ! :( ....");
        progressDoalog.setTitle("Please Wait Till The Status Appears !");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fetchAgain();

            }
        });

    }

    public void fetchAgain(){
        progressDoalog.show();
        namefetch.setText("");
        rollFetch.setText("");
        codeFetch.setText("");
        reasonFetch.setText("");
        timeFetch.setText("");
        emailFetch.setText("");

        final FirebaseUser mfirebaseUser = firebaseAuth.getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assert mfirebaseUser != null;
                String nameas = dataSnapshot.child("Students").child(encodeUserEmail(Objects.requireNonNull(mfirebaseUser.getEmail()))).child("name").getValue().toString();
                String rollas = dataSnapshot.child("Students").child(encodeUserEmail(mfirebaseUser.getEmail())).child("roll").getValue().toString();
                String codeas = dataSnapshot.child("Students").child(encodeUserEmail(mfirebaseUser.getEmail())).child("codename").getValue().toString();
                String reason = dataSnapshot.child("Students").child(encodeUserEmail(mfirebaseUser.getEmail())).child("Request").child("reason").getValue().toString();

                String time = dataSnapshot.child("Students").child(encodeUserEmail(mfirebaseUser.getEmail())).child("Request").child("time").getValue().toString();
                String email = dataSnapshot.child("Students").child(encodeUserEmail(mfirebaseUser.getEmail())).child("Request").child("email").getValue().toString();
                String emailDec = decodeUserEmail(email);
                Boolean acceptance = (Boolean) dataSnapshot.child("Students").child(encodeUserEmail(mfirebaseUser.getEmail())).child("Request").child("acceptance").getValue();

                if  (!acceptance){

                    Glide.with(CheckStatus.this)
                            .load("https://media.giphy.com/media/QhCAwDXZ0BltK/giphy.gif")
                            .into(imageView);
                    /* from internet*/
                    imageView.setVisibility(View.VISIBLE);

                    acceptanceA.setText("Not Accepted You Cannot Go Home");


                }else {

                    Glide.with(CheckStatus.this)
                            .load("https://media.giphy.com/media/10UeedrT5MIfPG/giphy.gif")
                            .into(imageView);
                    /* from internet*/
                    imageView.setVisibility(View.VISIBLE);
                    acceptanceA.setText("Accepted ! You Can Go Home ! Enjoy Your Day");



                }


                namefetch.setText(nameas);
                rollFetch.setText(rollas);
                codeFetch.setText(codeas);
                reasonFetch.setText(reason);
                timeFetch.setText(time);
                emailFetch.setText(emailDec);
                progressDoalog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Cannot Fetch data",Toast.LENGTH_SHORT).show();

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();


        final FirebaseUser mfirebaseUser = firebaseAuth.getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assert mfirebaseUser != null;
                String nameas = dataSnapshot.child("Students").child(encodeUserEmail(Objects.requireNonNull(mfirebaseUser.getEmail()))).child("name").getValue().toString();
                String rollas = dataSnapshot.child("Students").child(encodeUserEmail(mfirebaseUser.getEmail())).child("roll").getValue().toString();
                String codeas = dataSnapshot.child("Students").child(encodeUserEmail(mfirebaseUser.getEmail())).child("codename").getValue().toString();
                String reason = dataSnapshot.child("Students").child(encodeUserEmail(mfirebaseUser.getEmail())).child("Request").child("reason").getValue().toString();

                String time = dataSnapshot.child("Students").child(encodeUserEmail(mfirebaseUser.getEmail())).child("Request").child("time").getValue().toString();
                String email = dataSnapshot.child("Students").child(encodeUserEmail(mfirebaseUser.getEmail())).child("Request").child("email").getValue().toString();
                String emailDec = decodeUserEmail(email);
                Boolean acceptance = (Boolean) dataSnapshot.child("Students").child(encodeUserEmail(mfirebaseUser.getEmail())).child("Request").child("acceptance").getValue();

                if  (!acceptance){

                    Glide.with(getApplicationContext())
                            .load("https://media.giphy.com/media/QhCAwDXZ0BltK/giphy.gif")
                            .into(imageView);
                    /* from internet*/
                    imageView.setVisibility(View.VISIBLE);

                    acceptanceA.setText("Not Accepted You Cannot Go Home");
                    acceptanceA.setTextColor(Color.RED);
                    hintText.setText("Sad ! Request Declined .. Your Status Shows Below");


                }else {

                    Glide.with(getApplicationContext())
                            .load("https://media.giphy.com/media/10UeedrT5MIfPG/giphy.gif")
                            .into(imageView);
                    /* from internet*/
                    imageView.setVisibility(View.VISIBLE);
                    acceptanceA.setTextColor(getResources().getColor(R.color.colorAccent));
                    acceptanceA.setText("Accepted ! You Can Go Home ! Enjoy Your Day");
                    hintText.setText("Done ! Accepted ! Your Status Shows Below");



                }


                    namefetch.setText(nameas);
                    rollFetch.setText(rollas);
                    codeFetch.setText(codeas);
                    reasonFetch.setText(reason);
                    timeFetch.setText(time);
                    emailFetch.setText(emailDec);
                progressDoalog.dismiss();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Cannot Fetch data",Toast.LENGTH_SHORT).show();

            }
        });

    }
    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    static String decodeUserEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }

}

