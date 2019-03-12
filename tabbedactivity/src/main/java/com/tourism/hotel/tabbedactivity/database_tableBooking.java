package com.tourism.hotel.tabbedactivity;

public class database_tableBooking {


    String Name, MobileNo, Time, Date, NoGuest, RegId, UserEmail;

    public database_tableBooking() {
    }

    /*public database_tableBooking(String name, String mobileNo, String date, String time, String noGuest, String userEmail, String regId) {
        Name = name;
        MobileNo = mobileNo;
        Time = time;
        Date = date;
        NoGuest = noGuest;
        RegId = regId;
        UserEmail = userEmail;
    }*/

    public database_tableBooking(String name, String mobileNo, String time, String date, String noGuest, String regId) {
        Name = name;
        MobileNo = mobileNo;
        Time = time;
        Date = date;
        NoGuest = noGuest;
        RegId = regId;
    }
}
