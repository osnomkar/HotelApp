package com.tourism.hotel.hotelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
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
                                KEY = "key";
    FirebaseAuth tbAuth;
    public static String mVerificationId;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_parcel2);

        /* Name
         * MobileNo
         * Date
         * Time
         * UserEmail
         */

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;

        data = bundle.getStringArrayList(TableBooking1.KEY_DATA);

        assert data != null:"Phone Number not recieved";
        sendVerificationCode(data.get(1));

        tbAuth = FirebaseAuth.getInstance();

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks tbCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Get coe by sms
            String code = phoneAuthCredential.getSmsCode();

            Log.d(TAG, "PhoneAuthCredentials => "+phoneAuthCredential);


            if(code != null) {
                ((EditText)findViewById(R.id.txtOTP_tableBooking2)).setText(code);
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
                tbCallback
        );
    }

    private void verifyVerificationCode(String code){
        //create credentials
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing user
        signInWithPhoneAuthCredential(credential);
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        tbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");

                                mt("Verification Successful");

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
    public void mt(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
