package com.tourism.hotel.hotelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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


/*
*Table Booking 2
* Verify Customer through Mobile Number
*/

public class TableBooking2 extends AppCompatActivity {

    public static final String TAG = TableBooking2.class.getCanonicalName(),
                                KEY = "keyTB2";

    //Initiate Firebase Authuntication
    FirebaseAuth tbAuth;

    //Store Verification details and customer details
    public static String mVerificationId, code;
    ArrayList<String> data;

    //Init UI
    Button btnVerify, btnCancel;
    ProgressBar pbTb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_booking2);

        //Get Customer Details from Previous activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;

        /*1.Name
        * 2.Mobile No
        * 3.Address
        * 4.Date
        * 5.Time
        * 5.Food
        * 6.UserEmail
        * 7.RegId
        */

        data = bundle.getStringArrayList(TableBooking1.KEY_DATA);


        assert data != null:"Phone Number not recieved";

        sendVerificationCode(data.get(1));

        //Init Firebase Auth
        tbAuth = FirebaseAuth.getInstance();

        //Init UI
        btnVerify = findViewById(R.id.btnVerify_tableBooking2);
        btnVerify.setOnClickListener(this :: onVerify);
        btnCancel = findViewById(R.id.btnCancel_tableBooking2);
        btnCancel.setOnClickListener(this :: onCancel);

        pbTb = findViewById(R.id.pb_tableBooking2);
        pbTb.setVisibility(View.INVISIBLE);
    }

    private void onCancel(View view) {

        Intent intent = new Intent(this,Home_customer.class);
        startActivityForResult(intent,102);
    }

    //Send Code to Mobile Number given by Customer
    private void sendVerificationCode(String mobile){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+mobile,
                60,
                TimeUnit.SECONDS,
                this,
                tbCallback
        );
    }


    //Callback for verifying various status after send code to customer mobile number
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks tbCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Get coe by sms
            code = phoneAuthCredential.getSmsCode();

            if(code != null) {
                ((EditText)findViewById(R.id.txtOTP_tableBooking2)).setText(code);
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

    //Navigate to next activity after successful verification
    private void onVerify(View view) {

        pbTb.setVisibility(View.VISIBLE);
        verifyVerificationCode(code);
    }

    //Verify Code given by customer and get credential
    private void verifyVerificationCode(String code){
        //create credentials
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing user
        signInWithPhoneAuthCredential(credential);
    }

    //Sign in with Credential
    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        tbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");

                        mt("Verification Successful");

                        pbTb.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(this,TableBooking3.class);
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

    //Toast Dialog
    public void mt(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
