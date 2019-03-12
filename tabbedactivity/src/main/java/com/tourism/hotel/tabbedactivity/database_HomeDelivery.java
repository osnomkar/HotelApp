package com.tourism.hotel.tabbedactivity;

public class database_HomeDelivery {

    String Name, MobileNo, Address,Email, Date, Time, Food, UserEmail,RegId;

    public database_HomeDelivery() {
    }

    /*public database_HomeDelivery(String name, String mobileNo, String email , String address, String date, String time, String food, String userEmail, String regId) {
        Name = name;
        MobileNo = mobileNo;
        Email = email;
        Address = address;
        Date = date;
        Time = time;
        Food = food;
        UserEmail = userEmail;
        RegId = regId;
    }*/

    public database_HomeDelivery(String name, String mobileNo, String address, String email, String date, String time, String food, String regId) {
        Name = name;
        MobileNo = mobileNo;
        Address = address;
        Email = email;
        Date = date;
        Time = time;
        Food = food;
        RegId = regId;
    }
}
