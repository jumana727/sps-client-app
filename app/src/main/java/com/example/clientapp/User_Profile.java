package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class User_Profile extends AppCompatActivity {

    TextView edit_profile , change_password,set_id,  set_name , set_email, set_phone , set_balance, curent;
    String current_user = "";
    String current_id = "";
    ImageView home_screen, logout;

    String namefromdb = "";
    String walletfromdb = "";
    String phoneFromdb = "";
    String emailfromdb = "";

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String key_name = "username";
    String current_user_name = "";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        current_user = global_username.getUsername();
        current_id = global_username.getUserid();

        home_screen = findViewById(R.id.home_button);
        curent = findViewById(R.id.currentusername);

        set_id = findViewById(R.id.Set_id);
        edit_profile = findViewById(R.id.Edit_Profile);
        change_password = findViewById(R.id.Change_Password);
        logout = findViewById(R.id.logout);
        set_name = findViewById(R.id.Set_Name);
        set_email = findViewById(R.id.Set_Email);
        set_phone = findViewById(R.id.Set_phone);
        set_balance = findViewById(R.id.Set_Balance);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        Query checkUserDatabase = reference.orderByChild("userId").equalTo(current_id);

        checkUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {       namefromdb = dataSnapshot1.child("name").getValue(String.class);

                    emailfromdb = dataSnapshot1.child("email").getValue(String.class);
                    phoneFromdb = dataSnapshot1.child("phone").getValue(String.class);
                    walletfromdb = dataSnapshot1.child("wallet").getValue(String.class);
                }

                curent.setText("User Profile");
                set_id.setText(current_id);
                set_name.setText(current_user);
                set_email.setText(emailfromdb);
                set_phone.setText(phoneFromdb);
                set_balance.setText(walletfromdb);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(User_Profile.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(key_name).apply();
                editor.apply();
                Intent i = new Intent(getApplicationContext(), login.class);
                startActivity(i);
            }
        });

        home_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), update_profile.class);
                startActivity(i);
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), change_password.class);
                startActivity(i);
            }
        });
    }
}