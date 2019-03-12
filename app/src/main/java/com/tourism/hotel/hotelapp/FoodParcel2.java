package com.tourism.hotel.hotelapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class FoodParcel2 extends AppCompatActivity {

    public static final String TAG = FoodParcel2.class.getCanonicalName(),
                                KEY = "keyFP2";
    FirebaseAuth fpAuth;
    public static String mVerificationId,code;
    ArrayList<String> data;

    Button btnVerify, btnCancel;
    ProgressBar pbfp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_parcel2);

        /*
         * 0.Name
         * 1.MobileNo
         * 2.Date
         * 3.Time
         * 4.Food
         * 5.User
         */

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;

        data = bundle.getStringArrayList(FoodParcel1.KEY_DATA);

        assert data != null:"Phone Number not recieved";

        SendVerification();

        fpAuth = FirebaseAuth.getInstance();

        btnVerify = findViewById(R.id.btnVerify_FoodParcel2);
        btnCancel = findViewById(R.id.btnCancel_FoodParcel2);
        btnVerify.setOnClickListener(this :: onVerify);
        btnCancel.setOnClickListener(this :: onCancel);

        pbfp = findViewById(R.id.pb_FoodParcel2);
        pbfp.setVisibility(View.INVISIBLE);
    }

    private void onCancel(View view) {

        Intent intent = new Intent(this,Home_customer.class);
        startActivityForResult(intent,202);
    }

    private void onVerify(View view) {

        pbfp.setVisibility(View.VISIBLE);

        if(isInternetAvailable())
            verifyVerificationCode(code);
        else
            mt("Internet not available . Please try Again !!");
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks fpCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Get coe by sms
            code = phoneAuthCredential.getSmsCode();

            Log.d(TAG, "PhoneAuthCredentials => "+phoneAuthCredential);


            if(code != null) {
                ((EditText)findViewById(R.id.txtOTP_FoodParcel2)).setText(code);

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

    private void sendVerificationCode(String mobile){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+mobile,
                60,
                TimeUnit.SECONDS,
                this,
                fpCallback
        );
    }

    private void verifyVerificationCode(String code){
        //create credentials
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing user
        signInWithPhoneAuthCredential(credential);
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        fpAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");

                                mt("Verification Successful");

                                pbfp.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(this,FoodParcel3.class);
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

    public void mt(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    public boolean isInternetAvailable() {

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }

    private void SendVerification(){
        if (isInternetAvailable())
            sendVerificationCode(data.get(1));
        else
            mt("Internet not available !! Please Refresh the page.");
    }
}
