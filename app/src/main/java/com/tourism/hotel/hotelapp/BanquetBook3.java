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

public class BanquetBook3 extends AppCompatActivity {

    ArrayList<String> data;
    int RegId;

    Button btnCancel, btnSave;
    TextView txvName,txvMobileNo,txvTime,txvDate,txvPurpose,txvRegId;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public static final String KEY = "keyBB3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banquet_book3);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        data = bundle.getStringArrayList(BanquetBook2.KEY);

        RegId = generateRandom();

        txvName = findViewById(R.id.txvName_BanquetBook3);
        txvMobileNo = findViewById(R.id.txvMobileNo_BanquetBook3);
        txvTime = findViewById(R.id.txvShowTime_BanquetBook3);
        txvDate = findViewById(R.id.txvShowDate_BanquetBook3);
        txvPurpose = findViewById(R.id.txvPurpose_BanquetBook3);
        txvRegId = findViewById(R.id.txvRegId_BanquetBook3);

        txvName.setText(data.get(0));
        txvMobileNo.setText(data.get(1));
        txvDate.setText(data.get(3));
        txvTime.setText(data.get(4));
        txvPurpose.setText(""+data.get(5) );

        data.add(""+RegId);
        txvRegId.setText(""+data.get(6));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        btnSave = findViewById(R.id.btnSave_BanquetBook3);
        btnCancel = findViewById(R.id.btnCancel_BanquetBook3);

        btnSave.setOnClickListener(this :: onSave);
        btnCancel.setOnClickListener(this :: onCancel);

    }

    private void onCancel(View view) {
        startActivity(new Intent(this,Login.class));
    }

    private void onSave(View view) {

        database_HomeDelivery db_hd = new database_HomeDelivery(
                data.get(0),
                data.get(1),
                data.get(2),
                data.get(3),
                data.get(4),
                data.get(5),
                data.get(6),
                data.get(7));

        (databaseReference.child("BanquetBooking"))
                .child(data.get(7))
                .setValue(db_hd);

        Toast.makeText(this,"Your Order Booked Successfully",Toast.LENGTH_LONG).show();

        Intent intent =  new Intent(this,BanquetBook4.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(KEY,data);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public int generateRandom() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(200000);
    }
}
