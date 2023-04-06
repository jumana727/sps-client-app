package com.example.clientapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<VehicleNumber> {

    LayoutInflater layoutInflater;
    public SpinnerAdapter(@NonNull Context context,int resource, @NonNull List<VehicleNumber> vehicleNumbers) {
        super(context, resource, vehicleNumbers);
        layoutInflater=LayoutInflater.from(context);

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView=layoutInflater.inflate(R.layout.costom_spinner_adapter,null,true);
        VehicleNumber vehicleNumber=getItem(position);
        TextView spinnerTxtView=rowView.findViewById(R.id.spinnerTextView);


        spinnerTxtView.setText(vehicleNumber.getNumber());
        return rowView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
            convertView=layoutInflater.inflate(R.layout.costom_spinner_adapter,parent,false);
        VehicleNumber vehicleNumber=getItem(position);
        TextView spinnerTxtView=convertView.findViewById(R.id.spinnerTextView);
        spinnerTxtView.setText(vehicleNumber.getNumber());

        return convertView;
    }
}
