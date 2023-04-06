package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class add_balance extends AppCompatActivity {
    EditText editTextAddAmount;
    Button Continue;
    TextView Tbalance;
    Toolbar toolbar;

    String nameFromdb , emailfromdb , passwordfromdb , phoneFromdb ;
    String mWalletMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);


        editTextAddAmount=findViewById(R.id.editTextAddAmount);
        Tbalance=findViewById(R.id.Tbalance);
        Continue=findViewById(R.id.payBtn);

        //firebase
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("User");

        String cureent =global_username.getUserid();

        //set real database balance to the textViewBalance

        String[] WalletMoney = {""};
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("User");
        Query checkUserDatabase1 = reference1.orderByChild("userId").equalTo(cureent);
        checkUserDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren()) {
                    mWalletMoney=snapshot1.child("wallet").getValue(String.class);
                    nameFromdb = snapshot1.child("name").getValue(String.class);
                    emailfromdb = snapshot1.child("email").getValue(String.class);
                    phoneFromdb = snapshot1.child("phone").getValue(String.class);
                    passwordfromdb = snapshot1.child("password").getValue(String.class);
                    WalletMoney[0] =mWalletMoney;
                    Tbalance.setText(mWalletMoney);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }});

        // abhi code




        //
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String added_amoount=editTextAddAmount.getText().toString();
                if(added_amoount.isEmpty()){
                    Toast.makeText(add_balance.this, "Please Enter a Amount!!", Toast.LENGTH_SHORT).show();
                }else{
                    int editAmount=Integer.parseInt(added_amoount);
                    try{
                        int i = Integer.parseInt(WalletMoney[0]);
                        int ans=i+editAmount;

                        reference1.orderByChild("userId").equalTo(cureent).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                    String clubkey = childSnapshot.getKey();


                                    Map<String, Object> updates = new HashMap<>();

                                    updates.put("userId", cureent );
                                    updates.put("name", nameFromdb );
                                    updates.put("email", emailfromdb);
                                    updates.put("phone", phoneFromdb);
                                    updates.put("password", passwordfromdb);
                                    updates.put("wallet", Integer.toString(ans));



                                    //        reference.child("users").child(current_user).setValue(new_user_name);
                                    reference1.child(clubkey).setValue(updates);

                                    Toast.makeText(getApplicationContext(), "Balance Added. ", Toast.LENGTH_SHORT).show();
                                    finish();
                                }}

                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "Failed to read value.", Toast.LENGTH_SHORT).show();
                            }
                        });


                     //   FirebaseDatabase.getInstance().getReference("User").child("wallet").setValue(Integer.toString(ans));
                      //  Toast.makeText(add_balance.this, "Rs. "+added_amoount+" added", Toast.LENGTH_SHORT).show();
                      //  finish();

                    } catch(NumberFormatException ex){ // handle your exception
                        System.out.println("Error !!!!!!");
                    }
                }
            }
        });

//        toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //step -2
        //        if(getSupportActionBar()!=null){
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Add Money");
        //        }
    }


    //for read data into a firebase
//    private String setData(@NonNull DatabaseReference databaseReference){
//        final String[] str = {new String()};
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange( DataSnapshot snapshot) {
//                String value = snapshot.child("wallet").getValue(String.class);
//                str[0] =value;
//                System.out.println(value);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//        return str[0];
//    }





    //option menu

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        new MenuInflater(this).inflate(R.menu.menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int itemID=item.getItemId();
//
//        if(itemID==R.id.newbtn){
//            Toast.makeText(getApplicationContext(),"New File is Created",Toast.LENGTH_SHORT).show();
//        }
//        else if(itemID==R.id.edit){
//            Toast.makeText(getApplicationContext(),"File Edit",Toast.LENGTH_SHORT).show();
//
//        }
//        else if(itemID==R.id.save){
//            Toast.makeText(getApplicationContext(),"File Saved",Toast.LENGTH_SHORT).show();
//        }
//        else{
//            super.onBackPressed();
//        }
//
//        return super.onOptionsItemSelected(item);}
}