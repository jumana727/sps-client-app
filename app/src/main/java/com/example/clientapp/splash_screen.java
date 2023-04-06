package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class splash_screen extends AppCompatActivity {

    Animation topAni , bottomAni;
    ImageView img;
    TextView name , slogan;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String key_name = "username";
    String current_user_name = "";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        topAni = AnimationUtils.loadAnimation(this,R.anim.top);
        bottomAni = AnimationUtils.loadAnimation(this,R.anim.bottom);

        img = findViewById(R.id.imageView);
        name = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        img.setAnimation(topAni);
        name.setAnimation(bottomAni);
        slogan.setAnimation(bottomAni);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(key_name, null);

        if(name != null){


            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
            Query checkUserDatabase = reference.orderByChild("userId").equalTo(name); //  child(userUsername);

            checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        for(DataSnapshot dataSnapshot1:snapshot.getChildren())
                        {
                            SystemClock.sleep(2000);
                            current_user_name = dataSnapshot1.child("name").getValue(String.class);
                            Toast.makeText(getApplicationContext(), " "+ current_user_name, Toast.LENGTH_SHORT).show();
                            global_username.setUserid(name);
                            global_username.setUsername(current_user_name);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }else{

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    Intent i = new Intent(getApplicationContext(), login.class);
                    startActivity(i);
                    finish();
                }
            }, 4000);
        }
    }
}