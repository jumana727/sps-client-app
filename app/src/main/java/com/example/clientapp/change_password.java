package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
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

import kotlinx.coroutines.InactiveNodeList;

public class change_password extends AppCompatActivity {

    EditText old_password, new_password, reenter_password;
    Button save_password;
    ImageView goto_profile;


    String current_user = "";
    String current_id = "";
    String nameFromdb = "";
    String phoneFromdb = "";
    String emailfromdb = "";
    String walletfromdb = "";
    String passwordfromdb = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        current_user = global_username.getUsername();
        current_id = global_username.getUserid();
        goto_profile = findViewById(R.id.backtoprofile);
        old_password = findViewById(R.id.Old_Password);
        new_password = findViewById(R.id.New_Password);
        reenter_password = findViewById(R.id.ReEnter_password);
        save_password = findViewById(R.id.Save_Password);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        Query checkUserDatabase = reference.orderByChild("userId").equalTo(current_id);

        checkUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {

                    nameFromdb = dataSnapshot1.child("name").getValue(String.class);
                    emailfromdb = dataSnapshot1.child("email").getValue(String.class);
                    phoneFromdb = dataSnapshot1.child("phone").getValue(String.class);
                    passwordfromdb = dataSnapshot1.child("password").getValue(String.class);
                    walletfromdb = dataSnapshot1.child("wallet").getValue(String.class);
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });

        goto_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), User_Profile.class);
                startActivity(intent);
            }
        });
        save_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oldpassword = old_password.getText().toString().trim();
                String newpassword = new_password.getText().toString().trim();
                String newcompassword = reenter_password.getText().toString().trim();

                if(oldpassword.length() <= 0 || newpassword.length() <= 0   || newcompassword.length() <= 0 ){
                    if(oldpassword.isEmpty()){
                        old_password.setError("Enter Old Password");
                    }else if(newpassword.isEmpty()){
                        new_password.setError("Enter New Password");
                    }else if(newcompassword.isEmpty()){
                        reenter_password.setError("Enter Comform Password");
                    }
                }else{
                    if(! newpassword.equals(newcompassword)){
                        reenter_password.setError("Plz Enter Same Password");
                    }else if(newpassword.length() <= 6){
                        new_password.setError("Password Is Too Small");
                    }
                    else{

                        if(oldpassword.equals(passwordfromdb)){
                            reference.orderByChild("userId").equalTo(current_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                        String clubkey = childSnapshot.getKey();


                                        Map<String, Object> updates = new HashMap<>();

                                        updates.put("userId", current_id );
                                        updates.put("name", nameFromdb );
                                        updates.put("email", emailfromdb);
                                        updates.put("phone", phoneFromdb);
                                        updates.put("password", newpassword);
                                        updates.put("wallet", walletfromdb);



                                        //        reference.child("users").child(current_user).setValue(new_user_name);
                                        reference.child(clubkey).setValue(updates);

                                        Toast.makeText(getApplicationContext(), "Password Changed. ", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(),User_Profile.class);
                                        startActivity(i);
                                    }}

                                public void onCancelled(DatabaseError error) {
                                    Toast.makeText(getApplicationContext(), "Failed to read value.", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{
                            old_password.setError("Wrong Password");
                        }
                    }
                }
            }
        });
    }
}