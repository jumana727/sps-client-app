package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class update_profile extends AppCompatActivity {

    EditText new_name, new_email, new_phone;
    String current_user;
    String current_id;
    Button save_profile;
    ImageView pro_to_profile;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String nameFromdb = "";
    String phoneFromdb = "";
    String emailfromdb = "";
    String walletfromdb = "";

    String passwordfromdb = "";


    String new_user_name , new_user_email , new_user_phone ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        current_user = global_username.getUsername();
        current_id = global_username.getUserid();
        new_name = findViewById(R.id.New_Name);
        pro_to_profile = findViewById(R.id.profille_to_profile);
        new_email = findViewById(R.id.New_Email);
        new_phone = findViewById(R.id.New_Phone);
        save_profile = findViewById(R.id.Save_Update_profile);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        Query checkUserDatabase = reference.orderByChild("userId").equalTo(current_id);

        checkUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //   String userData = dataSnapshot.getValue(String.class);

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    nameFromdb  = dataSnapshot1.child("name").getValue(String.class);
                    emailfromdb = dataSnapshot1.child("email").getValue(String.class);
                    phoneFromdb = dataSnapshot1.child("phone").getValue(String.class);
                    passwordfromdb = dataSnapshot1.child("password").getValue(String.class);
                    walletfromdb = dataSnapshot1.child("wallet").getValue(String.class);

                }


                new_name.setText(nameFromdb);
                new_email.setText(emailfromdb);
                new_phone.setText(phoneFromdb);

                new_user_name = current_user;
                new_user_email = emailfromdb;
                new_user_phone = phoneFromdb;

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });

        pro_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), User_Profile.class);
                startActivity(intent);
            }
        });
        save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(new_name.length() <= 0  || new_email.length() <= 0 || new_phone.length() <= 0){

                    if(new_name.getText().toString().isEmpty()){
                        new_name.setError("Enter Username");
                    }else if(new_email.getText().toString().isEmpty()){
                        new_email.setError("Enter User Email");
                    }else if(new_phone.getText().toString().isEmpty()){
                        new_phone.setError("Enter Phone Number");
                    }
                }else if(! new_email.getText().toString().matches(emailPattern)){
                    new_email.setError("Enter User Valid Email");
                }else if( new_phone.getText().toString().length() != 10){
                    new_phone.setError("Enter User Valid Phone No");
                }
                else{

                    if( ! new_name.getText().toString().trim().isEmpty()){
                        new_user_name =new_name.getText().toString().trim();
                    }
                    if( ! new_email.getText().toString().trim().isEmpty()){
                        new_user_email =new_email.getText().toString().trim();
                    }
                    if( ! new_phone.getText().toString().trim().isEmpty()){
                        new_user_phone =new_phone.getText().toString().trim();
                    }

                    reference.orderByChild("userId").equalTo(current_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                String clubkey = childSnapshot.getKey();

                                Map<String, Object> updates = new HashMap<>();
                                updates.put("userId", current_id);
                                updates.put("name", new_user_name);
                                updates.put("email", new_user_email);
                                updates.put("phone", new_user_phone);
                                updates.put("password", passwordfromdb);
                                updates.put("wallet", walletfromdb);



                                //        reference.child("users").child(current_user).setValue(new_user_name);
                                reference.child(clubkey).setValue(updates);
                                global_username.setUsername(new_user_name);
                                current_user = new_user_name;

                                Toast.makeText(getApplicationContext(), "Information Updated ", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(),User_Profile.class);
                                startActivity(i);
                            }}

                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Failed to read value.", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }
}