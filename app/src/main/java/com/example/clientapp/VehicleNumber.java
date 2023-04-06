package com.example.clientapp;

import java.util.ArrayList;

public class VehicleNumber {

    String Number;

    static ArrayList<VehicleNumber> vehicleNumbers=new ArrayList<>();

    public VehicleNumber(String number) {

        Number = number;
    }




    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

//    public static void initVehicleNumbers(){
//        vehicleNumbers.add(new VehicleNumber(1,"GJ34H3425"));
//        vehicleNumbers.add(new VehicleNumber(2,"BA93PA1948"));
//        vehicleNumbers.add(new VehicleNumber(3,"MH34H3425"));
//        vehicleNumbers.add(new VehicleNumber(4,"BR34H3425"));
//    }

    public static void setVehicleNumbers(ArrayList<VehicleNumber> arrayList){vehicleNumbers=arrayList;}


    public static ArrayList<VehicleNumber> getVehicleNumbers() {
        return vehicleNumbers;
    }
}
