package com.tourism.hotel.hotelapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

//Welcome page of the HotelApp

public class Welcome extends  Activity {

    private static int SPLASH_TIME_OUT = 1000;

    //Progress bar for Getting internet connection
    ProgressBar pb;

    //For refreshing after internet connection
    FloatingActionButton flB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.pb_Welcome);
        flB = findViewById(R.id.flB_Welcome);
        flB.setOnClickListener(this :: onRefresh);
        flB.setActivated(false);
        flB.hide();

        myContent();

    }

    //On press of refresh button
    private void onRefresh(View view) {
        myContent();
    }

    //actual contents in welcome page
    private void myContent(){
        if(isInternetAvailable()) {

            pb.setVisibility(View.INVISIBLE);

            new Handler().postDelayed(new Runnable() {

                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity

                    Intent i = new Intent(Welcome.this, Login.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
        else {
            mt("Internet Not Available. Please Start Internet");
            flB.setActivated(true);
            flB.show();
        }
    }

    //Toast Dialog
    private void mt(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    //Check for Internet connection
    public boolean isInternetAvailable() {

        pb.setVisibility(View.VISIBLE);
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }
}
