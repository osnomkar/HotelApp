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

public class BanquetBook1 extends AppCompatActivity {

    EditText txtName, txtMobileNo, txtPurpose;
    Button btnDate, btnTime, btnSubmit, btnCancel;
    TextView txvShowDate, txvShowTime;

    Calendar calendar;

    String Name, MobileNo, Purpose, Time, Date, User;

    public static final String TAG = BanquetBook1.class.getCanonicalName(),
                                KEY_DATA = "keyData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banquet_book1);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //UserEmail
        assert bundle != null;
        User = bundle.getString("Key");

        txtName = findViewById(R.id.txtName_BanquetBook1);
        txtMobileNo = findViewById(R.id.txtMobileNo_BanquetBook1);
        txtPurpose = findViewById(R.id.txtPurpose_BanquetBook1);

        txvShowDate = findViewById(R.id.txvShowDate_BanquetBook1);
        txvShowTime = findViewById(R.id.txvShowTime_BanquetBook1);

        btnDate = findViewById(R.id.btnDate_BanquetBook1);
        btnTime = findViewById(R.id.btnTime_BanquetBook1);
        btnCancel = findViewById(R.id.btnCancel_BanquetBook1);
        btnSubmit = findViewById(R.id.btnSubmit_BanquetBook1);

        btnDate.setOnClickListener(this :: showDatePicker);
        btnTime.setOnClickListener(this :: showTimePicker);
        btnSubmit.setOnClickListener(this :: onSubmit);
        btnCancel.setOnClickListener(this :: onCancel);

    }

    private void onCancel(View view) {
        startActivity(new Intent(this,Home_customer.class));
    }

    private void onSubmit(View view) {

        Name = txtName.getText().toString();
        MobileNo = txtMobileNo.getText().toString();
        Purpose = txtPurpose.getText().toString();
        String d = txvShowDate.getText().toString();
        String t = txvShowTime.getText().toString();

        if(     Name.isEmpty()      ||
                MobileNo.isEmpty()  ||
                Purpose.isEmpty()   ||
                d.isEmpty()         ||
                t.isEmpty()
        )
            mt("All fields are mandatory");
        else {

            ArrayList<String> data = new ArrayList<>();
            data.add(Name);
            data.add(MobileNo);
            data.add(Date);
            data.add(Time);
            data.add(Purpose);
            data.add(User);

            Intent intent = new Intent(this, BanquetBook2.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(KEY_DATA, data);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    private void showTimePicker(View view) {

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

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Time = hourOfDay+":"+minute;

            if((hourOfDay >= 10 && hourOfDay <= 15) && (minute >= 0 && minute <= 59)){
                txvShowTime.setText(Time);
                mt("Thanks for Booking Day");
            }
            else if((hourOfDay >= 17 && hourOfDay <= 22) && (minute >= 0 && minute <= 59)){
                txvShowTime.setText(Time);
                mt("Thanks for Booking Evening");
            }
            else
                mt("Service unavailable for this time !!!");
        }
    };

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

            Date = dayOfMonth+"-"+(month+1)+"-"+year;

            if((dayOfMonth <= currentDay+4) && (month <= currentMonth+1) && (year <= currentYear+1) &&
                    (dayOfMonth >= currentDay) && (month >= currentMonth) && (year >= currentYear))
                txvShowDate.setText(Date);
            else
                mt("Service is available for next 4 days only");
        }
    };

    private void mt(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
