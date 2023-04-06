package com.example.clientapp;

import static com.example.clientapp.VehicleNumber.vehicleNumbers;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.SnapshotHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Test extends AppCompatActivity {

    ArrayList<TransactionModel> arrNumber = new ArrayList<>();
    RecyclerView recyclerView;
    String numberPlate;
    String inTime, outTime;


    public void myTest() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VehicleHistory");
        Query checkUserDatabase = reference.orderByChild("transactionId");
        checkUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    numberPlate = snapshot1.child("vehiclePlateNo").getValue(String.class);
                    inTime = snapshot1.child("inTime").getValue(String.class);
                    outTime = snapshot1.child("outTime").getValue(String.class);
                    Integer tmpFee = snapshot1.child("fee").getValue(Integer.class);
                    //    fees=Integer.toString(tmpFee);
                    arrNumber.add(new TransactionModel(numberPlate, tmpFee, inTime));
                    // System.out.println(numberPlate + "\n" + fees + "\n" + inTime + "\n" + outTime);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //  Toast.makeText(LogsActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });


        SystemClock.sleep(2000);
        TransactionAdapter adapter = new TransactionAdapter(getApplicationContext(), arrNumber);
        recyclerView.setAdapter(adapter);


    }

}