package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class walletActivity extends AppCompatActivity {



    ArrayList<data> datas;




    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        //recycler view
        // Lookup the recyclerview in activity layout
        RecyclerView recyclerViewData=(RecyclerView) findViewById(R.id.rvdatas) ;

        // Initialize data
//        datas = data.createdatasList(20);

        // Create adapter passing in the sample user data
        DataAdapter adapter = new DataAdapter(datas);
        // Attach the adapter to the recyclerview to populate items
        recyclerViewData.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerViewData.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

//        datas.addAll(data.createdatasList(5));

        // Add a new contact
//        datas.add(0, new data("Barney", true));
// Notify the adapter that an item was inserted at position 0
        adapter.notifyItemInserted(0);


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



        //make toast





        //toolbar
        //step -1
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //step -2
//        if(getSupportActionBar()!=null){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("WalletPage");
//        }
    }

    //option menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID=item.getItemId();

        if(itemID==R.id.newbtn){
            Toast.makeText(getApplicationContext(),"New File is Created",Toast.LENGTH_SHORT).show();
        }
        else if(itemID==R.id.edit){
            Toast.makeText(getApplicationContext(),"File Edit",Toast.LENGTH_SHORT).show();

        }
        else if(itemID==R.id.save){
            Toast.makeText(getApplicationContext(),"File Saved",Toast.LENGTH_SHORT).show();
        }
        else{
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}