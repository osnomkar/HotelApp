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

public class HomeDelivery2 extends AppCompatActivity {

    public static final String TAG = HomeDelivery2.class.getCanonicalName(),
    KEY = "keyHD2";

    ArrayList<String> data;
    FirebaseAuth hdAuth;
    public static String mVerificationId;

    ProgressBar pbHd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_delivery2);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        data = bundle.getStringArrayList(HomeDelivery1.KEY_DATA);

        sendVerificationCode(data.get(1));

        hdAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btnVerify_HomeDelivery2).setOnClickListener(this :: onVerify);
        findViewById(R.id.btnCancel_HomeDelivery2).setOnClickListener(this :: onCancel);

        pbHd = findViewById(R.id.pb_HomeDelivery2);
        pbHd.setVisibility(View.INVISIBLE);
    }

    private void onCancel(View view) {
        Intent intent = new Intent(this, Home_customer.class);
        startActivityForResult(intent,302);
    }

    private void onVerify(View view) {
        pbHd.setVisibility(View.VISIBLE);
        verifyVerificationCode(((EditText)findViewById(R.id.txtOTP_HomeDelivery2)).getText().toString());
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

    public boolean isInternetAvailable() {

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks hdCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Get coe by sms
            String code = phoneAuthCredential.getSmsCode();

            Log.d(TAG, "PhoneAuthCredentials => "+phoneAuthCredential);


            if(code != null) {
                ((EditText)findViewById(R.id.txtOTP_HomeDelivery2)).setText(code);
                verifyVerificationCode(code);
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
                hdCallback
        );
    }

    private void verifyVerificationCode(String code){
        //create credentials
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing user
        signInWithPhoneAuthCredential(credential);
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        hdAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");

                                mt("Verification Successful");
                                pbHd.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(this,HomeDelivery3.class);
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
    public void mt(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
