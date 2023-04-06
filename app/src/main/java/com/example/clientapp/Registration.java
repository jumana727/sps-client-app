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

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    TextView signin;


    EditText user_name , user_email, user_phone , user_password, user_con_password , user_id;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button registation;

    FirebaseDatabase database;
    DatabaseReference reference;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String key_name = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        user_id = findViewById(R.id.reg_userid);
        signin = findViewById(R.id.signinText);
        user_name = findViewById(R.id.reg_username);
        user_email = findViewById(R.id.reg_email);
        user_phone = findViewById(R.id.reg_phone);
        user_password = findViewById(R.id.reg_password);
        user_con_password = findViewById(R.id.reg_conform_password);
        registation = findViewById(R.id.reg_Button);




        registation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userid = user_id.getText().toString().trim();
                String email = user_email.getText().toString().trim();
                String username = user_name.getText().toString().trim();
                String password = user_password.getText().toString().trim();
                String con_password = user_con_password.getText().toString().trim();
                String phone = user_phone.getText().toString().trim();


                if(userid.length() <= 0  || username.length() <= 0  || email.length() <= 0 || phone.length() <= 0 || password.length() <= 0 || con_password.length() <= 0){

                    if(userid.isEmpty()){
                        user_id.setError("Enter UserId");
                    }else if(username.isEmpty()){
                        user_name.setError("Enter Username");
                    }else if(email.isEmpty()){
                        user_email.setError("Enter User Email");
                    }else if(phone.isEmpty()){
                        user_phone.setError("Enter Phone Number");
                    }else if(password.isEmpty()){
                        user_password.setError("Enter Password");
                    }else if(con_password.isEmpty()){
                        user_con_password.setError("Enter Comform Password");
                    }
                }else{
                    if(userid.length() < 6 || ! email.matches(emailPattern) || phone.length() < 10 || phone.length() > 10 ||  password.length() < 6 ){
                        if( userid.length() < 6 ){
                            user_id.setError("Enter Long UserName");
                        }

                        if(! email.matches(emailPattern) ){
                            user_email.setError("Enter Valid Email");
                        }

                        if( phone.length() > 10 && phone.length() < 10 ){
                            user_phone.setError("Enter Valid Phone Number");
                        }

                        if(password.length() <= 6){
                            user_password.setError("Enter Long Password");
                            Toast.makeText(getApplicationContext(),"Plz Enter Long Password",Toast.LENGTH_LONG).show();
                        }
                    }
                    else if(! password.equals(user_con_password.getText().toString())){
                        user_con_password.setError("Enter Same Password");
                        Toast.makeText(getApplicationContext(),"Both Password Are Not Same",Toast.LENGTH_LONG).show();
                    }
                    else if(! email.isEmpty() || ! username.isEmpty() || ! password.isEmpty() || ! phone.isEmpty()){
                        database = FirebaseDatabase.getInstance();
                        reference = database.getReference("users");


                        //    HelperClass helperClass = new HelperClass( email, username, password);
                        //   reference.child(username).setValue(helperClass);


                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
                        Query checkUserDatabase = reference.orderByChild("userId").equalTo(userid);

                        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    user_id.setError("User Name Already Exits");


                                } else {

                                    Map<String, Object> updates = new HashMap<>();
                                    updates.put("userId", userid);
                                    updates.put("name", username);
                                    updates.put("email", email);
                                    updates.put("phone", phone);
                                    updates.put("password", password);
                                    updates.put("wallet", "0");

                                    String userId = reference.push().getKey();
                                    reference.child(userId).setValue(updates);
                                    global_username.setUsername(username);
                                    global_username.setUserid(userid);

                                    sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(key_name,userid);
                                    editor.apply();

                                    Toast.makeText(getApplicationContext(),"Welcome " + username,Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            }
        });



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), login.class);
                startActivity(i);
            }
        });

    }
}