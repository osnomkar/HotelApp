package com.tourism.hotel.hotelapp;

import android.content.Intent;
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

public class HomeDelivery3 extends AppCompatActivity {

    ArrayList<String> data;
    int RegId;

    Button btnCancel, btnSave;
    TextView txvName,txvMobileNo,txvEmail,txvTime,txvDate,txvFood,txvAddress,txvRegId;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public static final String KEY = "keyHD3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_delivery3);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        data = bundle.getStringArrayList(HomeDelivery2.KEY);

        RegId = generateRandom();

        txvName = findViewById(R.id.txvName_HomeDelivery3);
        txvMobileNo = findViewById(R.id.txvMobileNo_HomeDelivery3);
        txvEmail = findViewById(R.id.txvEmail_HomeDelivery3);
        txvTime = findViewById(R.id.txvShowTime_HomeDelivery3);
        txvDate = findViewById(R.id.txvShowDate_HomeDelivery3);
        txvFood = findViewById(R.id.txvShowFood_HomeDelivery3);
        txvAddress = findViewById(R.id.txvAddress_HomeDelivery3);
        txvRegId = findViewById(R.id.txvRegId_HomeDelivery3);

        txvName.setText(data.get(0));
        txvMobileNo.setText(data.get(1));
        txvEmail.setText(data.get(2));
        txvAddress.setText(data.get(3));
        txvDate.setText(data.get(4));
        txvTime.setText(data.get(5));
        txvFood.setText(data.get(6) );

        data.add(""+RegId);
        txvRegId.setText(String.valueOf(data.get(8)));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        btnSave = findViewById(R.id.btnSave_HomeDelivery3);
        btnCancel = findViewById(R.id.btnCancel_HomeDelivery3);

        btnSave.setOnClickListener(this :: onSave);
        btnCancel.setOnClickListener(this :: onCancel);

    }

    /*
    * Name
    * MobileNo
    * Email
    * Address
    * Date
    * Time
    * Food
    * User
    * RegID
    * */

    private void onSave(View view) {

        database_HomeDelivery db_hd = new database_HomeDelivery(
                data.get(0),
                data.get(1),
                data.get(2),
                data.get(3),
                data.get(4),
                data.get(5),
                data.get(6),
                data.get(7),
                data.get(8));

        (databaseReference.child("HomeDelivery"))
                .child(data.get(8))
                .setValue(db_hd);

        Toast.makeText(this,"Your Order Booked Successfully",Toast.LENGTH_LONG).show();

        Intent intent =  new Intent(this,HomeDelivery4.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(KEY,data);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void onCancel(View view) {
        Intent intent = new Intent(this, Home_customer.class);
        startActivityForResult(intent,303);
    }

    public int generateRandom() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(200000);
    }
}
