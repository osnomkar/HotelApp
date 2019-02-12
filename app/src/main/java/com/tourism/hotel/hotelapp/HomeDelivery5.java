package com.tourism.hotel.hotelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class HomeDelivery5 extends AppCompatActivity{

    ArrayList<String> data;
    public static final String TAG = HomeDelivery5.class.getCanonicalName();

    TextView txvName, txvMobileNo, txvEmail, txvTime, txvDate, txvAddress, txvFood, txvRegId;
    Button btnCancel, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_delivery5);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        assert bundle != null;
        data = bundle.getStringArrayList(HomeDelivery4.KEY);

        /*
         * 0.Name
         * 1.MobileNo
         * 2.Email
         * 3.Address
         * 4.Date
         * 5.Time
         * 6.Food
         * 7.User
         * 8.RegId
         */

        txvName = findViewById(R.id.txvName_HomeDelivery5);
        txvMobileNo = findViewById(R.id.txvMobileNo_HomeDelivery5);
        txvEmail = findViewById(R.id.txvEmail_HomeDelivery5);
        txvTime = findViewById(R.id.txvShowTime_HomeDelivery5);
        txvDate = findViewById(R.id.txvShowDate_HomeDelivery5);
        txvFood = findViewById(R.id.txvShowFood_HomeDelivery5);
        txvAddress = findViewById(R.id.txvAddress_HomeDelivery5);

        btnSave = findViewById(R.id.btnSave_HomeDelivery5);
        btnCancel = findViewById(R.id.btnCancel_HomeDelivery5);
        btnSave.setOnClickListener(this :: onSave);
        btnCancel.setOnClickListener(this :: onCancel);

        txvName.setText(data.get(0));
        txvMobileNo.setText(data.get(1));
        txvEmail.setText(data.get(2));
        txvAddress.setText(data.get(3));
        txvDate.setText(data.get(4));
        txvTime.setText(data.get(5));
        txvFood.setText(data.get(6));
        txvRegId.setText(data.get(8));
    }

    private void onCancel(View view) {
        Intent intent = new Intent(this, Home_customer.class);
        startActivityForResult(intent,305);
    }

    private void onSave(View view) {
        Intent intent = new Intent(this, Home_customer.class);
        startActivityForResult(intent,305);
    }


}
