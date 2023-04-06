package com.example.clientapp;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Locale;

public class AddVehicle extends AppCompatActivity {

    private Spinner spinner;
    //ProgressBar progressBar;
    TextInputEditText editText;
    Button addTopNavBtn,removeTopNavBtn;

    Button addVechileBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        loadFragment(new AddVehicleFragment());
    /*    editText=findViewById(R.id.idEdtVechile);
        progressBar=findViewById(R.id.idPBLoading);
       spinner=findViewById(R.id.selectCategoeryspinner);
        addVechileBtn=(Button) findViewById(R.id.addVehicleButton);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<VehicleNumber> category=new ArrayList<>();
        category.add(new VehicleNumber(1,"Car"));
        category.add(new VehicleNumber(2,"Bike"));

        SpinnerAdapter spinnerAdapter=new SpinnerAdapter(this,R.layout.costom_spinner_adapter,category);
        spinner.setAdapter(spinnerAdapter);


        addVechileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(AddVehicle.this,"Adding vechile"+editText.getText().toString()+spinner.getSelectedItemId(),LENGTH_SHORT).show();
            }
        });

*/

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        addTopNavBtn=findViewById(R.id.addTopNavBtn);
        removeTopNavBtn=findViewById(R.id.removeTopNavBtn);

        addTopNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AddVehicleFragment());
            }
        });


        removeTopNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new RemoveVehicleFragment());
            }
        });
    }
    public void loadFragment(Fragment fragment){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.Container,fragment);
        ft.commit();
   }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true; }

}