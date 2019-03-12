package com.tourism.hotel.hotelapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BanquetBook2 extends AppCompatActivity {

    public static final String TAG = BanquetBook2.class.getCanonicalName(),
            KEY = "key";

    FirebaseAuth bbAuth;
    public static String mVerificationId, code;
    ArrayList<String> data;

    Button btnVerify, btnCancel;
    ProgressBar pbBb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banquet_book2);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;

        /* 1.Name
         * 2.Mobile No
         * 3.Date
         * 4.Time
         * 5.Purpose
         * 6.UserEmail
         * 7.RegId
         */

        data = bundle.getStringArrayList(BanquetBook1.KEY_DATA);

        assert data != null:"Phone Number not recieved";

        bbAuth = FirebaseAuth.getInstance();

        SendVerification();

        btnVerify = findViewById(R.id.btnVerify_BanquetBook2);
        btnVerify.setOnClickListener(this :: onVerify);

        btnCancel = findViewById(R.id.btnCancel_BanquetBook2);
        btnCancel.setOnClickListener(this :: onCancel);

        pbBb = findViewById(R.id.pb_BanquetBook2);
        pbBb.setVisibility(View.INVISIBLE);

    }

    private void onCancel(View view) {
        Intent intent = new Intent(this,Home_customer.class);
        startActivityForResult(intent,402);
    }

    private void onVerify(View view) {
        pbBb.setVisibility(View.VISIBLE);
        if(isInternetAvailable())
            verifyVerificationCode(code);
        else
            mt("Internet not available . Please try again !!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            SendVerification();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void SendVerification(){
        if (isInternetAvailable())
            sendVerificationCode(data.get(1));
        else
            mt("Internet not available !! Please Refresh the page.");
    }

    private void sendVerificationCode(String mobile){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+mobile,
                60,
                TimeUnit.SECONDS,
                this,
                bbCallback
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks bbCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Get coe by sms
            code = phoneAuthCredential.getSmsCode();

            if(code != null) {
                ((EditText)findViewById(R.id.txtOTP_BanquetBook2)).setText(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            mt(e.getMessage());
        }

        @Override
        public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(verificationId, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = verificationId;
        }
    };

    private void verifyVerificationCode(String code){
        //create credentials
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing user
        signInWithPhoneAuthCredential(credential);
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        bbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");

                                mt("Verification Successful");

                                pbBb.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(this,BanquetBook3.class);
                                Bundle bundle = new Bundle();
                                bundle.putStringArrayList(KEY,data);
                                intent.putExtras(bundle);
                                startActivity(intent);

                            } else {
                                // Sign in failed, display a message and update the UI
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    // The verification code entered was invalid

                                    mt("Code Invalid");

                                }
                            }
                        }

                );
    }

    public boolean isInternetAvailable() {

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }

    public void mt(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
