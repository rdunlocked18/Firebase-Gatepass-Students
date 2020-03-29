package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RequestPass extends AppCompatActivity {
    TimePicker timePicker;
    Button request;
    TextView getName,getRoll,getUid,backsuper;
    EditText giveReason;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPreferences.Editor editor ;
    SharedPreferences prefs;
    long requestid,requestidPersonal;

    CardView mainCard;
    ImageView imageView;
    //data save twice for retrival of complete array and once for single child

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_pass);

        imageView = findViewById(R.id.imageView);
        backsuper = findViewById(R.id.backsuper);



        //timepicker
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        //other
        request = findViewById(R.id.requestSuper);
        getName = findViewById(R.id.getNameRequest);
        getRoll = findViewById(R.id.getRollRequest);
        getUid = findViewById(R.id.getCodeRequest);
        giveReason = findViewById(R.id.giveReason);
        mainCard = (CardView)findViewById(R.id.mainCard);

        //firebase init
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mfirebaseUser = firebaseAuth.getCurrentUser();
        prefs = getSharedPreferences("superPref", MODE_PRIVATE);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        editor = prefs.edit();

        //get Data for textViews for name roll and codeid
        String nameget = prefs.getString("name",null);
        String rollget = prefs.getString("roll",null);
        String codenameget = prefs.getString("codename",null);


        getName.setText(nameget);
        getRoll.setText(rollget);
        getUid.setText(codenameget);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    requestid = (dataSnapshot.getChildrenCount());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    request.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            requestidPersonal = requestidPersonal+1;
           insaneDataSend();
            Glide.with(getApplicationContext())
                    .load("https://media.giphy.com/media/MEF1VadKbQBdmd8LCn/giphy.gif")
                    .into(imageView);
            mainCard.setVisibility(View.GONE);
            /* from internet*/
            imageView.setVisibility(View.VISIBLE);
            backsuper.setVisibility(View.VISIBLE);
            backsuper.setText("Hmm ! Now Wait");




        }
    });


    }

    public void insaneDataSend() {



        String nameReq = getName.getText().toString();
        String rollReq = getRoll.getText().toString();
        String codeReq = getUid.getText().toString();
        String reasonReq = giveReason.getText().toString();
        String email = prefs.getString("email",null);
        String letcAss = "";
        int hour, minute;
        String am_pm;
        if (Build.VERSION.SDK_INT >= 23) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }
        if (hour > 12) {
            am_pm = "PM";
            hour = hour - 12;
        } else {
            am_pm = "AM";
        }
//        categories.add("CE17F");
//        categories.add("CE18D");
//        categories.add("CE18F");
//        categories.add("CE19D");
        if(codeReq.contains("CE17F")){
            letcAss = "MAHENDRA DHONI";
        }else if(codeReq.contains("CE18D")){
            letcAss = "YEWRAWE";
        }else if(codeReq.contains("CE18F")){
            letcAss = "SUCHIN TENDOLKAR";
        }else if(codeReq.contains("CE19D")){
            letcAss = "Veroit KODI";
        }else {

            Toast.makeText(getApplicationContext(),"Have You Selected Proper UID ?",Toast.LENGTH_SHORT).show();
        }
        String timeCombo = hour+" : "+minute+" : "+am_pm;

        DataRef dataRefforReq = new DataRef(nameReq,rollReq,codeReq,reasonReq,timeCombo,false,email);
       final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;

        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())

                    requestidPersonal   = (dataSnapshot.child(firebaseUser.getUid()).child("Request").getChildrenCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        databaseReference.child("Students").child(encodeUserEmail(Objects.requireNonNull(firebaseUser.getEmail()))).child("Request").setValue(dataRefforReq);


        databaseReference.child("Requests Super").child(encodeUserEmail(Objects.requireNonNull(firebaseUser.getEmail()))).setValue(dataRefforReq);





        Toast.makeText(getApplicationContext(),"Request Sent",Toast.LENGTH_LONG).show();

    }
    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    static String decodeUserEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }
}
