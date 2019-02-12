package com.tourism.hotel.hotelapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    //Firebase Realtime Database Initialization
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,loginIds;


    String txtEmail,txtPassword;

    //Temporary data collection
    List<String> Userlist,Passwordlist;
    ValueEventListener userlistner,passwordlistener;

    //for Internet connection and fetching data
    ProgressBar pbLogin;


    public static final String TAG = Login.class.getCanonicalName();

    //Details for admin login
    public static final String ADMIN = "admin@hotel",
                                ADMIN_PASSWORD = "admin",
                                KEY_USER = "UserEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_a);

        //Initiate firebase and Make directory /users
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        loginIds =  databaseReference.child("/users");

        //Initiating progress bar
        pbLogin = findViewById(R.id.pb_Login);
        pbLogin.setVisibility(View.VISIBLE);

        loadLoginList();

        //Initiating UI
        findViewById(R.id.btnLogin_LoginA).setOnClickListener(this::onLogin);
        findViewById(R.id.txvNewAccount_LoginA).setOnClickListener(this::onNewAccount);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadLoginList();
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadLoginList();
    }

    //Navigation for new customers
    private void onNewAccount(View view) {
        Intent intent = new Intent(Login.this, NewAccont.class);
        startActivity(intent);
    }

    //Validating Login details and navigation to dashboard
    private void onLogin(View view) {

        //Check for active internet connection
        if(isInternetAvailable()) {

            //Get Details from User
            txtEmail = ((EditText) findViewById(R.id.txtEmail_LoginA)).getText().toString();
            txtPassword = ((EditText) findViewById(R.id.txtPassword_LoginA)).getText().toString();

            if (txtEmail.isEmpty())
                mt("Email cannot be Empty");

            else if (txtPassword.isEmpty())
                mt("Password cannot be Empty");

            //Admin Login
            else if (txtEmail.equals(ADMIN) && txtPassword.equals(ADMIN_PASSWORD)) {
                startActivity(new Intent(this, Home_admin.class));
                clearText();
            }
            //Check for exiting username
            else if (Userlist.contains(txtEmail)) {
                if (Passwordlist.contains(txtPassword)) {
                    Intent intent = new Intent(this, Home_customer.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY_USER, txtEmail);
                    //intent.putExtras(bundle);
                    //startActivity(intent);
                    startActivityForResult(intent, 777, bundle);
                    clearText();
                } else {
                    mt("Invalid password");
                    clearText();
                }

            } else {
                mt("User not found");
                clearText();
            }
        }
        else
            mt("Internet Not available. Please Start your Internet");
    }

    //Fetch username list and login list
    private void loadLoginList(){

        userlistner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Userlist = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String user = ds.getKey();
                    Userlist.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                    mt(databaseError.getDetails());
            }
        };


        passwordlistener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Passwordlist = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String password = ds.getValue(String.class);
                    Passwordlist.add(password);
                }
                //list list of Password
                Log.i(TAG,"PasswordList => "+Passwordlist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        loginIds.addValueEventListener(userlistner);
        loginIds.addValueEventListener(passwordlistener);

        pbLogin.setVisibility(View.INVISIBLE);
    }

    //Toast Dialog
    private void mt(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    //Clear Text after navigating to another activity
    private void clearText(){
        ((EditText)findViewById(R.id.txtEmail_LoginA)).setText("");
        ((EditText)findViewById(R.id.txtPassword_LoginA)).setText("");
    }

    //Check for active intetnet connection
    public boolean isInternetAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
