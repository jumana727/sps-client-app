package com.example.clientapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class RemoveVehicleFragment extends Fragment {


    public RemoveVehicleFragment() {
        // Required empty public constructor
    }


     Button removeBtn;
    Spinner removeSpinner;


    String current_id,valueFromSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_remove_vehicle, container, false);
        removeBtn=view.findViewById(R.id.removeVehicleButton);
        removeSpinner=view.findViewById(R.id.selectVehivle);

        current_id=global_username.getUserid();


        SpinnerAdapter adapter=new SpinnerAdapter(getContext(),R.layout.costom_spinner_adapter,VehicleNumber.getVehicleNumbers());
        int cnt=adapter.getCount();

        removeSpinner.setAdapter(adapter);
        removeSpinner.setSelection(0);


        removeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VehicleNumber vehicleNumber=(VehicleNumber) parent.getItemAtPosition(position);
                valueFromSpinner=vehicleNumber.getNumber();
            //    Toast.makeText(getContext(), "adapter"+cnt, Toast.LENGTH_SHORT).show();
             //   Toast.makeText(getContext(), valueFromSpinner, Toast.LENGTH_SHORT).show();
                //   Toast.makeText(getContext(),valueFromSpinner,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("vehicle");
        Query applesQuery = ref.orderByChild("userId").equalTo(current_id);
        if(cnt<=1){
            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Cannot reomve single data", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                // Toast.makeText(getContext(),"HI"+ valueFromSpinner, Toast.LENGTH_SHORT).show();
                                if ((snapshot1.child("vehiclePlateNo").getValue(String.class)).equals(valueFromSpinner)) {
                                    snapshot1.getRef().removeValue();
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getContext(), "Removed " + valueFromSpinner, Toast.LENGTH_SHORT).show();
                                }
                                //snapshot1.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, "onCancelled", databaseError.toException());
                        }
                    });


//

//                Toast.makeText(getContext(),"Removed Vehicle "+(removeSpinner.getSelectedItem().toString()),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(),removeSpinner.getItemAtPosition(removeSpinner.getSelectedItemPosition()).toString(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(),""+removeSpinner.getSelectedItem().toString().substring(35),Toast.LENGTH_SHORT).show();

                }
            });
        }


        return view;
    }



}