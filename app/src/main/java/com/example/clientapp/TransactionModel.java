package com.example.clientapp;

public class TransactionModel {
    String mVehicleNumberPlate;
    int mFees;
    String minTime;

    public TransactionModel(String numberPlate, int fees, String inTime) {
        this.mVehicleNumberPlate = numberPlate;
        this.mFees = fees;
        this.minTime = inTime;
    }


}
