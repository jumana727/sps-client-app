 package com.example.clientapp;


 import androidx.activity.result.ActivityResultLauncher;
 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.appcompat.widget.Toolbar;
 import androidx.recyclerview.widget.DividerItemDecoration;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.LinearSnapHelper;
 import androidx.recyclerview.widget.RecyclerView;
 import androidx.recyclerview.widget.SnapHelper;


 import android.content.Intent;
 import android.os.Bundle;
 import android.os.SystemClock;
 import android.view.Menu;
 import android.view.MenuInflater;
 import android.view.MenuItem;
 import android.view.MotionEvent;
 import android.view.View;
 import android.view.animation.Animation;
 import android.view.animation.AnimationUtils;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.google.firebase.database.DataSnapshot;
 import com.google.firebase.database.DatabaseError;
 import com.google.firebase.database.DatabaseReference;
 import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.database.Query;
 import com.google.firebase.database.ValueEventListener;

 import java.util.ArrayList;

 import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

 public class Wallet_Upadate extends AppCompatActivity {


//Anils Code
//ArrayList<TransactionModel> arrNumber =new ArrayList<>();
//  RecyclerView recyclerView;
//  String numberPlate;
//  String inTime,outTime;
  //End

 ArrayList<TransactionModel> datas=new ArrayList<>();
  RecyclerView recyclerViewData;
  ArrayList<String> vehiclePlateNoList=new ArrayList<>();
 Toolbar toolbar;
 ArrayList<DataAdapter> newList;
 //text add money button
  TextView addMoney;
  TextView textViewBalance;
  Animation scaleUp,scaleDown;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_wallet_upadate);





 //Anil Code

//
//  recyclerView = findViewById(R.id.rvdatas);
//  recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//  DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VehicleHistory");
//  Query checkUserDatabase = reference.orderByChild("transactionId");
//  checkUserDatabase.addValueEventListener(new ValueEventListener() {
//   @Override
//   public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//     numberPlate = snapshot1.child("vehiclePlateNo").getValue(String.class);
//     inTime = snapshot1.child("inTime").getValue(String.class);
//     outTime = snapshot1.child("outTime").getValue(String.class);
//     Integer tmpFee = snapshot1.child("fee").getValue(Integer.class);
//     //    fees=Integer.toString(tmpFee);
//     arrNumber.add(new TransactionModel(numberPlate, tmpFee, inTime));
//     // System.out.println(numberPlate + "\n" + fees + "\n" + inTime + "\n" + outTime);
//    }
//   }
//
//   @Override
//   public void onCancelled(@NonNull DatabaseError error) {
//    //  Toast.makeText(LogsActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
//   }
//  });
//
//
//  SystemClock.sleep(2000);
//  TransactionAdapter adapter = new TransactionAdapter(getApplicationContext(), arrNumber);
//  recyclerView.setAdapter(adapter);
//
//
// }
 //ends here

  //^^^^^^Transaction Loading
  //recycler view
  // Lookup the recyclerview in activity layout
  recyclerViewData= findViewById(R.id.rvdatas);
  recyclerViewData.setLayoutManager(new LinearLayoutManager(this));

 //add money text view
  addMoney=findViewById(R.id.addMoney);
  scaleUp= AnimationUtils.loadAnimation(this,R.anim.scale_up);

  scaleDown=AnimationUtils.loadAnimation(this,R.anim.scale_down);

  textViewBalance=findViewById(R.id.textViewBalance);

  //set real database balance to the textViewBalance
  String cureent =global_username.getUserid();
  DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("User");
  Query checkUserDatabase1 = reference1.orderByChild("userId").equalTo(cureent);
  checkUserDatabase1.addValueEventListener(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            for(DataSnapshot snapshot1:snapshot.getChildren()) {
                                             String mWalletMoney=snapshot1.child("wallet").getValue(String.class);

                                             textViewBalance.setText(mWalletMoney);
                                            }
                                           }
                                           @Override
                                           public void onCancelled(@NonNull DatabaseError error) {
                                           }});


//firebase


//  getVehiclePlatenNo
  //i need to store this data into array and print into a transaction id section
  DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("vehicle");
  Query checkUserDatabase3 = reference3.orderByChild("userId").equalTo(cureent);
  checkUserDatabase3.addValueEventListener(new ValueEventListener() {
   @Override
   public void onDataChange(@NonNull DataSnapshot snapshot) {
    for(DataSnapshot snapshot1:snapshot.getChildren()) {
     String mvehicalePlateNo=snapshot1.child("vehiclePlateNo").getValue(String.class);
     vehiclePlateNoList.add(mvehicalePlateNo);
     System.out.println(mvehicalePlateNo);
    }
    for(String str:vehiclePlateNoList){
     System.out.println(str);
     DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VehicleHistory");
     Query checkUserDatabase = reference.orderByChild("vehiclePlateNo").equalTo(str);

     checkUserDatabase.addValueEventListener(new ValueEventListener() {

      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
       //    Toast.makeText(Wallet_Upadate.this, "it's here bc", Toast.LENGTH_SHORT).show();
       for(DataSnapshot snapshot1:snapshot.getChildren()) {
        String mVehiclePlateNo = snapshot1.child("vehiclePlateNo").getValue(String.class);
        String mDatetime = snapshot1.child("inTime").getValue(String.class);
        Integer mFee = snapshot1.child("fee").getValue(Integer.class);
        datas.add(new TransactionModel(mVehiclePlateNo, mFee,mDatetime));
        System.out.println(datas.size());

       }
      // SystemClock.sleep(2000);
       TransactionAdapter adapter=new TransactionAdapter(getApplicationContext(), datas);
       recyclerViewData.setAdapter(adapter);
      }
      @Override
      public void onCancelled(@NonNull DatabaseError error) {
      }
     });
    }


   }

   @Override
   public void onCancelled(@NonNull DatabaseError error) {

   }
  });

  for (String str :
          vehiclePlateNoList) {
   System.out.println(str);

  }



  addMoney.setOnTouchListener(new View.OnTouchListener() {
   @Override
   public boolean onTouch(View view, MotionEvent motionEvent) {
    if(motionEvent.getAction()==MotionEvent.ACTION_UP){
     addMoney.startAnimation(scaleUp);

    } else if (motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
     addMoney.startAnimation(scaleDown);
    }
    return false;
   }
  });

  addMoney.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View view) {
    Intent intent=new Intent(getApplicationContext(),WalletAddAmnt.class);
    startActivity(intent);
   }
  });


 // Initialize data

//  for(String str:vehiclePlateNoList){
//   System.out.println(str);
//  DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VehicleHistory");
//  Query checkUserDatabase = reference.orderByChild("vehiclePlateNo").equalTo(str);
//
//   checkUserDatabase.addValueEventListener(new ValueEventListener() {
//
//    @Override
//    public void onDataChange(@NonNull DataSnapshot snapshot) {
//     //    Toast.makeText(Wallet_Upadate.this, "it's here bc", Toast.LENGTH_SHORT).show();
//     for(DataSnapshot snapshot1:snapshot.getChildren()) {
//      String mVehiclePlateNo = snapshot1.child("vehiclePlateNo").getValue(String.class);
//      String mDatetime = snapshot1.child("inTime").getValue(String.class);
//      Integer mFee = snapshot1.child("fee").getValue(Integer.class);
//      datas.add(new data(mVehiclePlateNo, mDatetime, "300",mFee));
//      System.out.println(datas.size());
//
//     }
//    }
//    @Override
//    public void onCancelled(@NonNull DatabaseError error) {
//    }
//   });
//  }




  System.out.println(datas.size());
// // Create adapter passing in the sample user data
// DataAdapter adapter = new DataAdapter(datas);
// // Attach the adapter to the recyclerview to populate items
// recyclerViewData.setAdapter(adapter);



  // // Set layout manager to position the items




 RecyclerView.ItemDecoration itemDecoration = new
 DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
 recyclerViewData.addItemDecoration(itemDecoration);

 recyclerViewData.setItemAnimator(new SlideInUpAnimator());

 //Handling Touch Events
 recyclerViewData.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

 @Override
 public boolean onInterceptTouchEvent(RecyclerView recycler, MotionEvent event) {
 return false;
 }

 @Override
 public void onTouchEvent(RecyclerView recycler, MotionEvent event) {
 Toast.makeText(getApplicationContext(),event.getDeviceId()+"touched",Toast.LENGTH_SHORT).show();
 }

 @Override
 public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

 }
 });

 SnapHelper snapHelper = new LinearSnapHelper();
 snapHelper.attachToRecyclerView(recyclerViewData);

 //toolbar
 //step -1
// toolbar=findViewById(R.id.toolbar);
// setSupportActionBar(toolbar);

 //step -2
 //        if(getSupportActionBar()!=null){
// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
// getSupportActionBar().setTitle("WalletPage");
 //        }
 }


 //option menu

// @Override
// public boolean onCreateOptionsMenu(Menu menu) {
// new MenuInflater(this).inflate(R.menu.menu,menu);
// return super.onCreateOptionsMenu(menu);
// }

// @Override
// public boolean onOptionsItemSelected(@NonNull MenuItem item) {
// int itemID=item.getItemId();
//
// if(itemID==R.id.newbtn){
// Toast.makeText(getApplicationContext(),"New File is Created",Toast.LENGTH_SHORT).show();
// }
// else if(itemID==R.id.edit){
// Toast.makeText(getApplicationContext(),"File Edit",Toast.LENGTH_SHORT).show();
//
// }
// else if(itemID==R.id.save){
// Toast.makeText(getApplicationContext(),"File Saved",Toast.LENGTH_SHORT).show();
// }
// else{
// super.onBackPressed();
// }
//
// return super.onOptionsItemSelected(item);
// }
 }
