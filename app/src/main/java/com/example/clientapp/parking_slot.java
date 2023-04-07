package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class parking_slot extends AppCompatActivity {

    static  String[] gridViewValue = new String[4];

    int place;
    int status;
    GridView gridView;

    Hashtable<Integer, Integer> dict = new Hashtable<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_slot);



         gridView=findViewById(R.id.gridView);

        for(int i=1;i<=4;i++){
            gridViewValue[i-1]=i+"";
        }


        // databadse


        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.parking_adapter,gridViewValue);
      //  int place;
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ParkingSlots");
             Query checkUserDatabase = reference.orderByChild("ParkingSlotsId");
            // dict.clear();
            checkUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                       // System.out.println("inside" + dict.get(1));
                         place = dataSnapshot1.child("slot").getValue(Integer.class);
                         status = dataSnapshot1.child("status").getValue(Integer.class);


                        dict.put(place, status);

                    }
                    System.out.println(dict.get(2));
//                    GridView gridView=findViewById(R.id.gridView);
                 //   ArrayAdapter<String> adapter=new ArrayAdapter<>(parking_slot.this,R.layout.parking_adapter,gridViewValue);
//                  gridView.setAdapter(adapter);
               //    markColor(dict, gridView);

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
//        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.parking_adapter,gridViewValue);
        gridView.setAdapter(adapter);



//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

         //---------------------

        gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Enumeration<Integer> keys = dict.keys();

                while(keys.hasMoreElements()){
                    // System.out.println(keys.nextElement());
                    int key=keys.nextElement();
                    if(dict.get(key)==1){
                        gridView.getChildAt(key-1).setBackgroundColor(Color.rgb(107,123,239));

                    }else{

                        gridView.getChildAt(key-1).setBackgroundColor(Color.rgb(193,199,243));

                    }

                }
            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                System.out.println(position);
                System.out.println(id);
               // gridView.getChildAt(position).setBackgroundColor(Color.GRAY);
                Enumeration<Integer> keys = dict.keys();

                while(keys.hasMoreElements()){
                    // System.out.println(keys.nextElement());
                    int key=keys.nextElement();
                    if(dict.get(key)==1){
                        gridView.getChildAt(key-1).setBackgroundColor(Color.rgb(107,123,239));

                    }else{

                        gridView.getChildAt(key-1).setBackgroundColor(Color.rgb(193,199,243));

                    }

                }

            }
        });
        //gridView.on
        gridView.performClick();

    }
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true; }
    @Override
    public void onBackPressed(){
   // gridView.setAdapter();
    //gridView.setAdapter(null);
    finish();
    }

    @Override
    protected void onResume(){
        super.onResume();

//        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.parking_adapter,gridViewValue);
    //    gridView.setAdapter(new ArrayAdapter<>(this,R.layout.parking_adapter,gridViewValue));

    }
    void markColor(Hashtable<Integer,Integer> dict, GridView gridView){
        Enumeration<Integer> keys = dict.keys();
if(!dict.isEmpty()){
        while(keys.hasMoreElements()){
            // System.out.println(keys.nextElement());
            int key=keys.nextElement();
            if(dict.get(key)==1){
              //  if(gridView.getChildAt(key-1).getSolidColor()!=Color.rgb(107,123,239))
               gridView.getChildAt(key-1).setBackgroundColor(Color.GRAY);



                /* Toast.makeText(parking_slot.this, " " + dict.get(key), Toast.LENGTH_SHORT).show();*/
                System.out.println("inside 1"+gridView.getChildAt(key-1));
            }else if(dict.get(key)==0){
                /* Toast.makeText(parking_slot.this, " " + dict.get(key), Toast.LENGTH_SHORT).show();*/

//                if(gridView.getChildAt(key-1).getSolidColor()!=Color.rgb(193,199,243))
                gridView.getChildAt(key-1).setBackgroundColor(Color.BLUE);

                System.out.println("outside 0"+gridView.getChildAt(key-1));
            }

        }}

    }
}
