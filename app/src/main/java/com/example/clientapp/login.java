package com.example.clientapp;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    TextView signup;
    EditText userid, userpassword;
    Button login;
    TextView skip;
    String passwordFromDB ;
    String nameFromDB ;
    String current_user_name = "";

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String key_name = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup = findViewById(R.id.signupText);
        userid = findViewById(R.id.login_username);
        userpassword = findViewById(R.id.login_password);
        login = findViewById(R.id.login_Button);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName=userid.getText().toString().trim();
                String userPassword=userpassword.getText().toString();
                if (userName.isEmpty()) {
                    userid.setError("Fileds cannot be empty");
                }
                if (userPassword.isEmpty()) {
                    userpassword.setError("Fileds cannot be empty");
                }
                if (!userid.getText().toString().isEmpty() && !userpassword.getText().toString().isEmpty()) {
                    checkUser(userName,userPassword);
                }
            }
        });
        // temp
//        skip = findViewById(R.id.skip);
//        skip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);
//            }
//        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Registration.class);
                startActivity(i);
            }
        });



    }

    public void checkUser (String userUsername,String userPassword) {



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        Query checkUserDatabase = reference.orderByChild("userId").equalTo(userUsername); //  child(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userid.setError(null);
                    for(DataSnapshot dataSnapshot1:snapshot.getChildren())
                    {

                        passwordFromDB = dataSnapshot1.child("password").getValue(String.class);
                        nameFromDB = dataSnapshot1.child("name").getValue(String.class);

                    }

                    if (passwordFromDB.equals(userPassword)) {
                        userid.setError(null);
                        global_username.setUsername(nameFromDB);
                        global_username.setUserid(userUsername);
                        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(key_name,userUsername);
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        Toast.makeText(getApplicationContext(),"Welcome Back " + nameFromDB,Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finish();
                    } else {
                        userpassword.setError("Invalid Credentials");
                        userpassword.requestFocus();
                    }
                } else {
                    userid.setError("User does not exist");
                    userid.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}