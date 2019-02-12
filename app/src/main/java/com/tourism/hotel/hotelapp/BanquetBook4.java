package com.tourism.hotel.hotelapp;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;

public class BanquetBook4 extends AppCompatActivity {

    ArrayList<String> data;
    public static final String TAG = BanquetBook4.class.getCanonicalName();
    public static final String KEY = "KeyBB4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_banquet4);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        assert bundle != null;
        data = bundle.getStringArrayList(BanquetBook3.KEY);

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

        findViewById(R.id.btnPay_BanquetBook4).setOnClickListener(this :: onPay);
    }

    private void onPay(View view) {
        callInstamojoPay(
                data.get(2),
                data.get(1),
                "200",
                "Banquet Booking Test",
                data.get(0)
        );

        mt("Pay button started");
    }
    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
            pay.put("send_sms", true);
            pay.put("send_email", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;


    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG)
                        .show();
                Intent intent = new Intent(getApplicationContext(), BanquetBook5.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(BanquetBook4.KEY,data);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }


    private void mt(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
