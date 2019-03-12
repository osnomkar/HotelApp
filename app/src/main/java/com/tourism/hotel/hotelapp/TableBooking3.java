package com.tourism.hotel.hotelapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;


/*
*Table Booking 3
* After Successful verification Write details to Remote Cloud server
*/
public class TableBooking3 extends AppCompatActivity {


    ArrayList<String> data;
    int RegId;

    //Init UI
    Button btnCancel, btnSave;
    TextView txvName,txvMobileNo,txvTime,txvDate,txvNoGuest,txvRegId;

    //Init Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_booking3);

        //Get Customer Details
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        data = new ArrayList<>();
        data = bundle.getStringArrayList(TableBooking2.KEY);

        //Assign RegId
        RegId = generateRandom();

        //Init UI
        txvName = findViewById(R.id.txvName_tableBooking3);
        txvMobileNo = findViewById(R.id.txvMobileNo_tableBooking3);
        txvTime = findViewById(R.id.txvTime_tableBooking3);
        txvDate = findViewById(R.id.txvDate_tableBooking3);
        txvNoGuest = findViewById(R.id.txvNoGuest_tableBooking3);
        txvRegId = findViewById(R.id.txvRegId_tableBooking3);

        btnSave = findViewById(R.id.btnSave_tableBooking3);
        btnCancel = findViewById(R.id.btnCancel_tableBooking3);

        btnSave.setOnClickListener(this :: onSave);
        btnCancel.setOnClickListener(this :: onCancel);

        //Review Customer Details
        txvName.setText(data.get(0));
        txvMobileNo.setText(data.get(1));
        txvDate.setText(data.get(2));
        txvTime.setText(data.get(3));
        txvNoGuest.setText(data.get(4)+" Guests");

        data.add(String.valueOf(RegId));
        txvRegId.setText(data.get(6));

        //Init Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    /* 0.Name
     * 1.Mobile No
     * 2.Date
     * 3.Time
     * 4.NoGuest
     * 5.UserEmail
     * 6.RegId
     */

    private void onSave(View view) {

        if (isInternetAvailable()) {

            //Init DB format
            database_tableBooking db_tb = new database_tableBooking(
                    data.get(0),
                    data.get(1),
                    data.get(2),
                    data.get(3),
                    data.get(4),
                    data.get(5),
                    data.get(6));

            //Get actual location reference
            (databaseReference.child("TableBooking"))
                    .child(data.get(6))
                    .setValue(db_tb);

            mt("Your Order Booked Successfully");

            Intent intent = new Intent(this, Home_customer.class);
            startActivityForResult(intent, 103);
        }
        else
            mt("Internet not available. Please try again !!");
    }

    private void onCancel(View view) {
        Intent intent = new Intent(this,Home_customer.class);
        startActivityForResult(intent,103);
    }


    private void mt(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }

    public boolean isInternetAvailable() {

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }

    //Generate Random No
    public int generateRandom() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);
    }
}
