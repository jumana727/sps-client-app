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


public class DataAdapter extends
        RecyclerView.Adapter<DataAdapter.ViewHolder> {

    ArrayList<data> mdatas=new ArrayList<>();
    String mVehiclePlateNo;
    String mDatetime;
    int mFee;

    public DataAdapter(ArrayList<data> datas) {
        mdatas=datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_contact, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        data contact = mdatas.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(contact.getVehiclePlateNo());
        TextView textView1 = holder.nameTextView1;
        textView1.setText(contact.getVehiclePlateNo());
        Button button = holder.messageButton;
        button.setText(contact.getFee());
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView nameTextView1;

        public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            nameTextView1 = (TextView) itemView.findViewById(R.id.contact_name1);
            messageButton = (Button) itemView.findViewById(R.id.fee_button);
        }
    }
}