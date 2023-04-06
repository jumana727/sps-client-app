package com.example.clientapp;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AddVehicleFragment extends Fragment {

    public AddVehicleFragment() {
        // Required empty public constructor
    }


    String numberPattern = "[a-zA-Z]{2}+[0-9]{2}+[a-zA-Z]{2}+[0-9]{1,4}";
   // private Spinner spinner;
   // ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference reference;
    String fireVehilceNumber;
    TextInputEditText editText;
    String vehicleNumber,current_id;

    private Button addVechileBtn;
    int temp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_vehicle, container, false);


        editText=view.findViewById(R.id.idEdtVechile);
      //  progressBar=view.findViewById(R.id.idPBLoading);
        //spinner=view.findViewById(R.id.selectCategoeryspinner);
        addVechileBtn=view.findViewById(R.id.addVehicleButton);
//        ArrayList<VehicleNumber> category=new ArrayList<>();
//        category.add(new VehicleNumber("Car"));
//        category.add(new VehicleNumber("Bike"));

//        SpinnerAdapter spinnerAdapter=new SpinnerAdapter(getContext(),R.layout.costom_spinner_adapter,category);
//        spinner.setAdapter(spinnerAdapter);

        current_id=global_username.getUserid();

        temp=1;






        addVechileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  progressBar.setVisibility(View.VISIBLE);
                vehicleNumber=editText.getText().toString().trim();
                if(vehicleNumber.isEmpty()){
                    editText.setError("Please Enter Vehicle Number ");
             //       Toast.makeText(getContext(),"Please Enter Vehicle Number",LENGTH_SHORT).show();
                  //  progressBar.setVisibility(View.GONE);
                }else if(!vehicleNumber.matches(numberPattern)){
                    editText.setError("Please Enter Valid Vehicle Number ");
                //    Toast.makeText(getContext(),"Please Enter Valid Vehicle Number",LENGTH_SHORT).show();
                 //   progressBar.setVisibility(View.GONE);
                }
                else  {
//                    database = FirebaseDatabase.getInstance();
//                    reference = database.getReference("vehicle");
                 //   Toast.makeText(getContext(),"it is running",LENGTH_SHORT).show();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("vehicle");
                    Query checkUserDatabase = reference.orderByChild("userId").equalTo(current_id);
                  //  if(checkUserDatabase.equals(null)){

                    checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot snapshot1:snapshot.getChildren()) {
                                 fireVehilceNumber=snapshot1.child("vehiclePlateNo").getValue(String.class);
                                if(vehicleNumber.equals(fireVehilceNumber)&&!fireVehilceNumber.equals(null)){
                                    Toast.makeText(getContext(), fireVehilceNumber, LENGTH_SHORT).show();
                                    temp=0;
                                    break;
                                } else{

                                    temp=1;

                                }
                            }
                            if(temp==0){
                                Toast.makeText(getContext(),"Vehicle already registered",LENGTH_SHORT).show();
                            }else{

                                Map<String, Object> updates = new HashMap<>();
                                updates.put("userId", current_id);
                                updates.put("vehiclePlateNo", vehicleNumber);


                                String userId = reference.push().getKey();
                                reference.child(userId).setValue(updates);

                         //       progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(),"Vehicle  " + vehicleNumber+" added sucessfully",Toast.LENGTH_LONG).show();

                                Intent intent=new Intent(getContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                //}                    else{
//                        Toast.makeText(getContext(), "This is else", LENGTH_SHORT).show();
//                        Map<String, Object> updates = new HashMap<>();
//                        updates.put("userId", current_id);
//                        updates.put("vehiclePlateNo", vehicleNumber);
//
//
//                        String userId = reference.push().getKey();
//                        reference.child(userId).setValue(updates);
//
//                        //       progressBar.setVisibility(View.GONE);
//                        Toast.makeText(getContext(),"Vehicle  " + vehicleNumber+" added sucessfully",Toast.LENGTH_LONG).show();
//
//                        Intent intent=new Intent(getContext(),MainActivity.class);
//                        startActivity(intent);
                       // Toast.makeText(getContext(), "hello", LENGTH_SHORT).show();
                    }


                }
           // }
        });


        return view;
    }
}