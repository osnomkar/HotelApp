package com.tourism.hotel.hotelapp;

public class database_BanquetBooking {

    String Name, MobileNo, Email, Date, Time, Purpose, RegId,UserEmail;


    public database_BanquetBooking() {
    }

    public database_BanquetBooking(String name, String mobileNo, String email, String date, String time, String purpose, String userEmail, String regId) {
        Name = name;
        MobileNo = mobileNo;
        Email = email;
        Date = date;
        Time = time;
        Purpose = purpose;
        RegId = regId;
        UserEmail = userEmail;
    }
}
