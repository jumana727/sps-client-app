package com.example.clientapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.ProfileViewHolder> {
    Context context;
    ArrayList<LogsModel> arrModel;
    ArrayList<LogsModel> arrModels;

    boolean flag;
    String valueFromSpinner;
    public LogsAdapter(Context context, ArrayList<LogsModel> arrModel,String valueFromSpinner){
        this.context=context;
        this.valueFromSpinner=valueFromSpinner;
        this.arrModel=arrModel;
    }
    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.logs_row, parent, false);
            ProfileViewHolder viewHolder = new ProfileViewHolder(view);
            return viewHolder;
        }


//    public void setValueFrom(){
//        for (LogsModel log:arrModel
//             ) {
//            if (arrModel.get(1).equals(valueFromSpinner)) {
//                arrModels.add(log);
//
//            }
//        }
//    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
       if(arrModel.get(position).numberPlate.equals(valueFromSpinner)) {
            flag=true;
            holder.numberPlate.setText(arrModel.get(position).numberPlate);
            holder.outTime.setText(arrModel.get(position).outTime);
            holder.inTime.setText(arrModel.get(position).inTime);
            holder.fees.setText("" + arrModel.get(position).fees);
       }else{
            flag=false;
          // holder.
           holder.numberPlate.setVisibility(View.GONE);
           holder.outTime.setVisibility(View.GONE);
          holder.inTime.setVisibility(View.GONE);
            holder.fees.setVisibility(View.GONE);
//
//           //Toast.makeText(context.getApplicationContext(),"No Data found"+arrModel.indexOf(valueFromSpinner),Toast.LENGTH_SHORT).show();
      }

    }

    @Override
    public int getItemCount() {
        //setValueFrom();
        return arrModel.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder{
        TextView numberPlate;
        TextView fees;
        TextView inTime;
        TextView outTime;
        Spinner spinner;
        public ProfileViewHolder(View itemView){
            super(itemView);
            numberPlate=itemView.findViewById(R.id.numberPlateTxtVew);
            inTime=itemView.findViewById(R.id.inTime);
            outTime=itemView.findViewById(R.id.outTime);
            fees=itemView.findViewById(R.id.parkingFee);
            spinner=itemView.findViewById(R.id.selectCategoeryspinner);
//            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    VehicleNumber vehicleNumber=(VehicleNumber) parent.getItemAtPosition(position);
//                    valueFromSpinner=vehicleNumber.getNumber();
//                    if (arrModel.get(0).equals(valueFromSpinner)) {
//                        //Toast.makeText(context.getApplicationContext(), valueFromSpinner,Toast.LENGTH_SHORT).show();
//                        System.out.println(valueFromSpinner);
//                    }
//                           //Toast.makeText(getContext(),valueFromSpinner,Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                }
//            });

        }
    }

}
