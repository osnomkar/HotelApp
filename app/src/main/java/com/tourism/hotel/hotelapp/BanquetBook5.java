package com.tourism.hotel.hotelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class BanquetBook5 extends AppCompatActivity {

    TextView txvName, txvMobileNo, txvEmail, txvDate, txvTime, txvPurpose, txvRegId;
    Button btnCancel, btnSave;

    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banquet_book5);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        assert bundle != null;
        data = bundle.getStringArrayList(BanquetBook4.KEY);

        /*
        0. Name
        1. MobileNo
        2. Email
        3. Date
        4. Time
        5. Purpose
        6. User
        7. RegID
         */

        txvName = findViewById(R.id.txvName_BanquetBook5);
        txvMobileNo = findViewById(R.id.txvMobileNo_BanquetBook5);
        txvEmail = findViewById(R.id.txvEmail_BanquetBook5);
        txvDate = findViewById(R.id.txvShowDate_BanquetBook5);
        txvTime = findViewById(R.id.txvShowTime_BanquetBook5);
        txvPurpose = findViewById(R.id.txvPurpose_BanquetBook5);
        txvRegId = findViewById(R.id.txvRegId_BanquetBook5);


        btnSave = findViewById(R.id.btnSave_BanquetBook5);
        btnCancel = findViewById(R.id.btnCancel_BanquetBook5);
        btnSave.setOnClickListener(this :: onSave);
        btnCancel.setOnClickListener(this :: onCancel);

        txvName.setText(data.get(0));
        txvMobileNo.setText(data.get(1));
        txvEmail.setText(data.get(2));
        txvDate.setText(data.get(3));
        txvTime.setText(data.get(4));
        txvPurpose.setText(data.get(5));
        txvRegId.setText(data.get(7));
    }

    private void onSave(View view) {
        Intent intent = new Intent(this, Home_customer.class);
        startActivityForResult(intent,405);
    }

    private void onCancel(View view) {
        Intent intent = new Intent(this, Home_customer.class);
        startActivityForResult(intent,405);
    }
}
