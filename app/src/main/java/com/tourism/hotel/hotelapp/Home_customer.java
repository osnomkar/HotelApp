package com.tourism.hotel.hotelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home_customer extends AppCompatActivity {


    //Initialize Grid Recycler view
    public RecyclerView recyclerGrid;
    public RecyclerView.LayoutManager layoutManager;
    public ArrayList<String> Services;

    String UserEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_customer);

        //Setting Up recycler view
        recyclerGrid = findViewById(R.id.recyclerGrid_Home_customer);
        recyclerGrid.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2);
        recyclerGrid.setLayoutManager(layoutManager);

        //Grid values of recycler view
        Services = new ArrayList<>();
        Services.add("Table Booking");
        Services.add("Food Parcel");
        Services.add("Home Delivery");
        Services.add("Banquet Booking");

        recyclerGridAdapter recyclerGridAdapter = new recyclerGridAdapter(this, Services,UserEmail);
        recyclerGrid.setAdapter(recyclerGridAdapter);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
    }

    //Receiving multiple activities after navigation
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if (requestCode == 101)
                mt("Received from Tb1");
            if (requestCode == 102)
                mt("Received from Tb2");
            if (requestCode == 103)
                mt("Received from Tb3");
            if(requestCode == 777){
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                assert bundle != null;
                UserEmail = bundle.getString(Login.KEY_USER);
                mt("Received from Login");
            }
        }
    }

    //Toast dialog
    private void mt(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
