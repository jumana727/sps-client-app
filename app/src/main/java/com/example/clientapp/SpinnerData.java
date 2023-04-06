package com.example.clientapp;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;

public class SpinnerData {
    ArrayList<VehicleNumber> vehicleNumberArrayList=new ArrayList<>();
    String current_id;
    public SpinnerData(){
        current_id=global_username.getUserid();
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference("vehicle");
        Query checkVehicleDatabase = reference.orderByChild("userId").equalTo(current_id);

        checkVehicleDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vehicleNumberArrayList.clear();

                for(DataSnapshot dataSnapshot1:snapshot.getChildren())
                {
                    String vehilceNumber=dataSnapshot1.child("vehiclePlateNo").getValue(String.class);
                    vehicleNumberArrayList.add(new VehicleNumber(vehilceNumber));
                }
                VehicleNumber.setVehicleNumbers(vehicleNumberArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }
        });
    }
}
