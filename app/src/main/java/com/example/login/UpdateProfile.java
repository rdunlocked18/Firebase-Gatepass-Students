package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateProfile extends AppCompatActivity {

    Button submit;
    Spinner spinner;
    EditText rollCreate,nameCreate,realCode;
    String branchCode;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseApp firebaseApp;
    FirebaseAuth firebaseAuth;
    SharedPreferences.Editor editor ;
    String buffersu ;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mfirebaseUser = firebaseAuth.getCurrentUser();
        prefs = getSharedPreferences("superPref", MODE_PRIVATE);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        nameCreate = findViewById(R.id.nameCreate);
        rollCreate = findViewById(R.id.rollCreate);
        realCode = findViewById(R.id.realCode);
        submit = findViewById(R.id.submit);
        spinner = (Spinner) findViewById(R.id.spinner);
        editor = prefs.edit();




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(item.equals("Select UID")){

                    realCode.setError("Select UID ERROR");

                }else {


                    branchCode = item ;
                    Toast.makeText(parent.getContext(), "UID : " + branchCode, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> categories = new ArrayList<String>();
        categories.add("Select UID");
        categories.add("CE17F");
        categories.add("CE18D");
        categories.add("CE18F");
        categories.add("CE19D");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(dataAdapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                editor.putString("name",nameCreate.getText().toString());
                editor.putString("roll",rollCreate.getText().toString());
                editor.putString("codename",branchCode+realCode.getText().toString());
                editor.putBoolean("profile",true);
                editor.apply();
                getDataAndUpdate();




            }
        });
    }

    public void getDataAndUpdate(){
        final FirebaseUser mfirebaseUser = firebaseAuth.getCurrentUser();
        String nameget = nameCreate.getText().toString().trim();
        String rollget =  rollCreate.getText().toString().trim();
        String bbc = branchCode + realCode.getText().toString();
        String email = prefs.getString("email",null);

        DataRef dataRef = new DataRef(nameget ,rollget , bbc , encodeUserEmail(email));

        assert mfirebaseUser != null;
        databaseReference.child("Students").child(encodeUserEmail(Objects.requireNonNull(mfirebaseUser.getEmail()))).setValue(dataRef);
        Toast.makeText(getApplicationContext(), "User Updated Successfully :)", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(UpdateProfile.this,Dashboard.class);
        startActivity(intent);



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

                String trimmedcode = codeas.substring(5);



                nameCreate.setText(nameas);
                rollCreate.setText(rollas);
               realCode.setText(trimmedcode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
