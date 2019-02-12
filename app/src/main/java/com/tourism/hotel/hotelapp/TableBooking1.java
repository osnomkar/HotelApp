package com.tourism.hotel.hotelapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


/*
* Table Booking 1
* Get Customer Details of Required Service
*/
public class TableBooking1 extends AppCompatActivity {

    //Init UI
    EditText txtName, txtMobileNo, txtNoGuest;
    Button btnDate, btnTime, btnSubmit, btnCancel;
    TextView txvShowDate,txvShowTime;


    String Name, MobileNo, NoGuest, Time, Date, UserEmail;

    //Get Time and Date
    Calendar calendar;
    int currentHour,currentDay,currentMonth,currentYear;

    public static final String TAG = TableBooking1.class.getCanonicalName(),
                        KEY_DATA = "keyTB1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_booking1);

        //Get User Name from Previous activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        UserEmail = bundle.getString("Key");

        //Initialize UI
        txtName = findViewById(R.id.txtName_TableBooking1);
        txtMobileNo = findViewById(R.id.txtMobileNo_TableBooking1);
        txtNoGuest = findViewById(R.id.txtNoGuest_TableBooking1);

        btnDate = findViewById(R.id.btnDate_TableBooking1);
        btnTime = findViewById(R.id.btnTime_TableBooking1);
        btnSubmit = findViewById(R.id.btnSubmit_tableBooking1);
        btnCancel = findViewById(R.id.btnCancel_tableBooking1);

        txvShowDate = findViewById(R.id.txvShowDate_TableBooking1);
        txvShowTime = findViewById(R.id.txvShowTime_TableBooking1);

        btnTime.setOnClickListener(this :: showTimePicker);
        btnDate.setOnClickListener(this :: showDatePicker);
        btnSubmit.setOnClickListener(this :: onSubmit);
        btnCancel.setOnClickListener(this :: onCancel);

        //Init Calender for current time and date
        calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentHour = calendar.get(Calendar.HOUR);

    }

    private void onCancel(View view) {
        Intent intent = new Intent(this,Home_customer.class);
        startActivityForResult(intent,101);
    }

    //After customer entered all details
    private void onSubmit(View view) {

        //Get Customer details
        Name = txtName.getText().toString();
        MobileNo = txtMobileNo.getText().toString();
        NoGuest = txtNoGuest.getText().toString();
        String d = txvShowDate.getText().toString();
        String t = txvShowTime.getText().toString();

        //validate customer details
        if(     Name.isEmpty() ||
                MobileNo.isEmpty() ||
                NoGuest.isEmpty() ||
                d.isEmpty() ||
                t.isEmpty()
        )
            mt("All fields are mandatory");
        else {
            //Collect user details and send it to next activity
            ArrayList<String> data = new ArrayList<>();
            data.add(Name);
            data.add(MobileNo);
            data.add(Date);
            data.add(Time);
            data.add(NoGuest);
            data.add(UserEmail);

            Intent intent = new Intent(this,TableBooking2.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(KEY_DATA,data);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    //Pick date from Customer
    private void showDatePicker(View view) {

        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                dateSetListener,
                year,
                month,
                day);
        datePickerDialog.show();
    }

    //Pick time from customer
    public void showTimePicker(View view){

        calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                timeSetListener,
                currentHour,
                currentMinute,
                false);
        timePickerDialog.show();
    }

    //Check for valid time
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Time = hourOfDay+":"+minute;

            //if ()
            if(hourOfDay >= currentHour+1) {

                if ((hourOfDay >= 12 && hourOfDay <= 14) && (minute >= 0 && minute <= 59)) {
                    txvShowTime.setText(Time);
                    mt("Thanks for Booking Lunch");
                } else if ((hourOfDay >= 16 && hourOfDay <= 19) && (minute >= 0 && minute <= 59)) {
                    txvShowTime.setText(Time);
                    mt("Thanks for Booking Evening Breakfast");
                } else if ((hourOfDay >= 19 && hourOfDay <= 23) && (minute >= 0 && minute <= 59)) {
                    txvShowTime.setText(Time);
                    mt("Thanks for Booking Dinner");
                } else{
                    mt("Service unavailable for this time !!!");
                    clearTime();
                }
            }
            else {
                mt("Service unavailable for this time !!!");
                clearTime();
            }
        }
    };

    //Check for valid date
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            Date = dayOfMonth+"-"+(month+1)+"-"+year;

            if((dayOfMonth <= currentDay+4) && (month <= currentMonth+1) && (year <= currentYear) &&
                    (dayOfMonth >= currentDay) && (month >= currentMonth) && (year >= currentYear))
                txvShowDate.setText(Date);
            else {
                mt("Service is available for next 4 days only");
                clearDate();
            }

        }
    };

    //Toast dialog
    private void mt(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    private void clearTime(){
        txvShowTime.setText("");
    }
    private void clearDate(){
        txvShowDate.setText("");
    }

}
