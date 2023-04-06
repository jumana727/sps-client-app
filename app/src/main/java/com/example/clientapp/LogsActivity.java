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

public class LogsActivity extends AppCompatActivity {

    ArrayList<LogsModel> arrNumber =new ArrayList<LogsModel>();
    ArrayList<LogsModel> userVehicleNumber =new ArrayList<LogsModel>();
    RecyclerView recyclerView;
    Spinner spinner;
    String numberPlate,valueFromSpinner;

    String inTime,outTime;
    //ArrayList<VehicleNumber> vehicleNumberArrayList=new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner=findViewById(R.id.selectCategoeryspinner);





     

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VehicleHistory");
        Query checkUserDatabase = reference.orderByChild("transactionId");

        checkUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren()) {
                    numberPlate = snapshot1.child("vehiclePlateNo").getValue(String.class);
                    inTime = snapshot1.child("inTime").getValue(String.class);
                    outTime = snapshot1.child("outTime").getValue(String.class);
                    Integer tmpFee = snapshot1.child("fee").getValue(Integer.class);
                //    fees=Integer.toString(tmpFee);
                    inTime=inTime.substring(0,10)+"\n"+inTime.substring(inTime.indexOf(" "),inTime.indexOf(" ")+6);
                    if(outTime.isEmpty()){
                        outTime="";
                        tmpFee=0;
                    }else {
                        outTime = outTime.substring(0, 10) + "\n" + outTime.substring(outTime.indexOf(" "), outTime.indexOf(" ") + 6);

                    }
                    arrNumber.add(new LogsModel(numberPlate, tmpFee, inTime, outTime));
                   // System.out.println(numberPlate + "\n" + fees + "\n" + inTime + "\n" + outTime);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LogsActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });




///Test code
//        current_id=global_username.getUserid();
//        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("vehicle");
//        Query checkVehicleDatabase1 = reference1.orderByChild("userId").equalTo(current_id);
//
//        checkVehicleDatabase1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                vehicleNumberArrayList.clear();
//
//                for(DataSnapshot dataSnapshot1:snapshot.getChildren())
//                {
//                    String vehilceNumber=dataSnapshot1.child("vehiclePlateNo").getValue(String.class);
//                    vehicleNumberArrayList.add(new VehicleNumber(vehilceNumber));
//                }
//                VehicleNumber.setVehicleNumbers(vehicleNumberArrayList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                System.out.println(error.getMessage());
//            }
//        });

//        ArrayList<LogsModel> model=new ArrayList<>();
//        for(LogsModel logs:arrNumber){
//            for(VehicleNumber number:vehicleNumberArrayList){
//                if(logs.numberPlate.equals(number.getNumber())){
//                    model.add(new LogsModel(logs.numberPlate, logs.fees, logs.inTime,logs.outTime));
//                    System.out.println(model);
//                }
//            }
//        }




     //   SystemClock.sleep(2000);
        SpinnerAdapter costomAdapter=new SpinnerAdapter(this,R.layout.costom_spinner_adapter,VehicleNumber.getVehicleNumbers());
        spinner.setAdapter(costomAdapter);

        spinner.setSelection(0);
//        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VehicleNumber vehicleNumber=(VehicleNumber) parent.getItemAtPosition(position);
                valueFromSpinner=vehicleNumber.getNumber();
                //Toast.makeText(LogsActivity.this, "hi"+valueFromSpinner, Toast.LENGTH_SHORT).show();

                LogsAdapter adapter=new LogsAdapter(getApplicationContext(), arrNumber,valueFromSpinner);

                recyclerView.setAdapter(adapter);
//                for(int i=0;i<arrNumber.size();i++){
//                    if(logsModel.getNumberPlate().equals(valueFromSpinner)){
//                        Toast.makeText(getApplicationContext(),"in"+arrNumber.get(0).toString(),Toast.LENGTH_SHORT).show();
//                    }
//                    Toast.makeText(getApplicationContext(),"out"+ arrNumber.get(0).toString(),Toast.LENGTH_SHORT).show();
//                }
             //      Toast.makeText(getContext(),valueFromSpinner,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true; }
}