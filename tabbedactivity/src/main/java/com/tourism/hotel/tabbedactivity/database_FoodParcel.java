package com.tourism.hotel.tabbedactivity;

public class database_FoodParcel {

    /*
     * 0.Name
     * 1.MobileNo
     * 2.Date
     * 3.Time
     * 4.Food
     * 5.User
     * 6.RegID
     */

    String Name, MobileNo, Date, Time, Food, User, RegId;

    public database_FoodParcel() {
    }

    public database_FoodParcel(String name, String mobileNo, String date, String time, String food, String user, String regId) {
        Name = name;
        MobileNo = mobileNo;
        Date = date;
        Time = time;
        Food = food;
        User = user;
        RegId = regId;
    }

    public database_FoodParcel(String name, String mobileNo, String date, String time, String food, String regId) {
        Name = name;
        MobileNo = mobileNo;
        Date = date;
        Time = time;
        Food = food;
        RegId = regId;
    }
}
