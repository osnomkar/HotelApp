package com.tourism.hotel.hotelapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class FoodParcel1 extends AppCompatActivity {

    //Init UI
    TextView txvShowDate, txvShowTime, txvShowFood;
    Button btnDate, btnTime, btnFood, btnSubmit, btnCancel;
    EditText txtName, txtMobileNo;

    //For getting Date and Time
    Calendar calendar;

    //Getting User Inputs
    String Name, MobileNo, Time, Date, User;

    //Dialog for picking Date, Time, Food
    Dialog dialog;

    //Food List
    final String[] items = {" Food 1", " Food 2", " Food 3", " Food 4", " Food 5"};

    //Selected Food
    ArrayList<String> itemsSelected;

    public static final String TAG = FoodParcel1.class.getCanonicalName(),
                                KEY_DATA = "keyFP1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_parcel1);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //UserEmail
        assert bundle != null;
        User = bundle.getString("Key");

        txvShowDate = findViewById(R.id.txvShowDate_FoodParcel1);
        txvShowTime = findViewById(R.id.txvShowTime_FoodParcel1);
        txvShowFood = findViewById(R.id.txvShowFood_FoodParcel1);

        btnDate = findViewById(R.id.btnDate_FoodParcel1);
        btnTime = findViewById(R.id.btnTime_FoodParcel1);
        btnFood = findViewById(R.id.btnFood_FoodParcel1);
        btnSubmit = findViewById(R.id.btnSubmit_FoodParcel1);
        btnCancel = findViewById(R.id.btnCancel_FoodParcel1);

        txtName = findViewById(R.id.txtName_FoodParcel1);
        txtMobileNo = findViewById(R.id.txtMobileNo_FoodParcel1);

        btnTime.setOnClickListener(this :: showTimePicker);
        btnDate.setOnClickListener(this :: showDatePicker);
        btnSubmit.setOnClickListener(this :: onSubmit);
        btnCancel.setOnClickListener(this :: onCancel);
        btnFood.setOnClickListener(this :: showFood);

        itemsSelected = new ArrayList<>();
    }

    private void showFood(View view) {

        txvShowFood.setText("");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Food that you want");
        builder.setMultiChoiceItems(
                items,
                null,
                multiChoiceClickListener
        )
                .setPositiveButton(
                        "Done",
                        positiveListener
                );
        dialog = builder.create();
        dialog.show();

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

    private void onSubmit(View view) {

        Name = txtName.getText().toString();
        MobileNo = txtMobileNo.getText().toString();
        String d = txvShowDate.getText().toString();
        String t = txvShowTime.getText().toString();
        String f = txvShowFood.getText().toString();

        if(     Name.isEmpty() ||
                MobileNo.isEmpty() ||
                d.isEmpty() ||
                t.isEmpty() ||
                f.isEmpty()
        )
            mt("All fields are mandatory");
        else {

            ArrayList<String> data = new ArrayList<>();
            data.add(Name);
            data.add(MobileNo);
            data.add(Date);
            data.add(Time);
            data.add(f);
            data.add(User);

            /*
            * 0.Name
            * 1.MobileNo
            * 2.Date
            * 3.Time
            * 4.Food
            * 5.User
            */

            Intent intent = new Intent(this,FoodParcel2.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(KEY_DATA,data);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    private void onCancel(View view) {

        Intent intent = new Intent(this,Home_customer.class);
        startActivityForResult(intent,201);
    }

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Time = hourOfDay+":"+minute;

            if((hourOfDay >= 12 && hourOfDay <= 14) && (minute >= 0 && minute <= 59)){
                txvShowTime.setText(Time);
                mt("Thanks for Booking Lunch");
            }
            else if((hourOfDay >= 16 && hourOfDay <= 19) && (minute >= 0 && minute <= 59)){
                txvShowTime.setText(Time);
                mt("Thanks for Booking Evening Breakfast");
            }
            else if((hourOfDay >= 19 && hourOfDay <= 23) && (minute >= 0 && minute <= 59)) {
                txvShowTime.setText(Time);
                mt("Thanks for Booking Dinner");
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

    DialogInterface.OnMultiChoiceClickListener multiChoiceClickListener = (dialog, which, isChecked) -> {

        if(isChecked)
            itemsSelected.add(items[which]);
    };

    DialogInterface.OnClickListener positiveListener = (dialog, which) -> {

        Log.i(TAG, "Before Filter => "+itemsSelected);

        Set<String> set = new HashSet<>(itemsSelected);
        itemsSelected.clear();
        itemsSelected.addAll(set);

        Log.i(TAG, "After Filter => "+itemsSelected);

        for( int i = 0 ; i <= itemsSelected.size()-1; i++ ){
            txvShowFood.append(""+itemsSelected.get(i));
            txvShowFood.append("\n");
        }

        itemsSelected.clear();

    };

    private void mt(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
