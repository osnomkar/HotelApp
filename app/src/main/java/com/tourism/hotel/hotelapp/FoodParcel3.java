package com.tourism.hotel.hotelapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class FoodParcel3 extends AppCompatActivity {

    ArrayList<String> data;
    int RegId;

    Button btnCancel, btnSave;
    TextView txvName,txvMobileNo,txvTime,txvDate,txvFood,txvRegId;

    //Init Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_parcel3);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        data = new ArrayList<>();
        data = bundle.getStringArrayList(FoodParcel2.KEY);

        RegId = generateRandom();
        /*
         * 0.Name
         * 1.MobileNo
         * 2.Date
         * 3.Time
         * 4.Food
         * 5.User
         * 6.RegID
         */

        txvName = findViewById(R.id.txvName_FoodParcel3);
        txvMobileNo = findViewById(R.id.txvMobileNo_FoodParcel3);
        txvTime = findViewById(R.id.txvShowTime_FoodParcel3);
        txvDate = findViewById(R.id.txvShowDate_FoodParcel3);
        txvFood= findViewById(R.id.txvShowFood_FoodParcel3);
        txvRegId = findViewById(R.id.txvRegId_FoodParcel3);

        btnSave = findViewById(R.id.btnSave_FoodParcel3);
        btnCancel = findViewById(R.id.btnCancel_FoodParcel3);

        btnSave.setOnClickListener(this :: onSave);
        btnCancel.setOnClickListener(this :: onCancel);

        //Review Customer Details
        txvName.setText(data.get(0));
        txvMobileNo.setText(data.get(1));
        txvTime.setText(data.get(3));
        txvDate.setText(data.get(2));
        txvFood.setText(data.get(4));

        data.add(String.valueOf(RegId));
        txvRegId.setText(data.get(6));

        //Init Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void onCancel(View view) {

        Intent intent = new Intent(this, Home_customer.class);
        startActivityForResult(intent,203);
    }

    private void onSave(View view) {

        if (isInternetAvailable()) {
            //Init DB format
            database_FoodParcel db_fp = new database_FoodParcel(
                    data.get(0),
                    data.get(1),
                    data.get(2),
                    data.get(3),
                    data.get(4),
                    data.get(5),
                    data.get(6));

            //Get actual location reference
            (databaseReference.child("FoodParcel"))
                    .child(data.get(6))
                    .setValue(db_fp);

            mt("Your Order Booked Successfully");

            Intent intent = new Intent(this, Home_customer.class);
            startActivityForResult(intent, 203);
        }
        else
            mt("Internet not available . Please try again !!");

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
