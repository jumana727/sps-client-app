package com.example.clientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransatictionViewHolder> {
    Context context;
    ArrayList<TransactionModel> arrModel;


    boolean flag;
    String valueFromSpinner;
    public TransactionAdapter(Context context, ArrayList<TransactionModel> arrModel){
        this.context=context;
        this.arrModel=arrModel;
    }
    @NonNull
    @Override
    public TransatictionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.test_recycle, parent, false);
        TransatictionViewHolder viewHolder = new TransatictionViewHolder(view);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull TransatictionViewHolder holder, int position) {

            holder.textView1.setText(arrModel.get(position).mVehicleNumberPlate);
            holder.textView2.setText(arrModel.get(position).minTime);
            holder.fee.setText(""+arrModel.get(position).mFees);


    }

    @Override
    public int getItemCount() {
        return arrModel.size();
    }

    public class TransatictionViewHolder extends RecyclerView.ViewHolder{
        TextView textView1;
        TextView textView2;
        Button fee;

        public TransatictionViewHolder(View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.contact_name);
            textView2=itemView.findViewById(R.id.contact_name1);
            fee=itemView.findViewById(R.id.fee_button);

        }
    }

}