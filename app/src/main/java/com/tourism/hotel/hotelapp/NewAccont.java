package com.tourism.hotel.hotelapp;

import android.content.Intent;
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

public class NewAccont extends AppCompatActivity {

    //Firebase Initialization
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,loginIds;
    List<String> Userlist;
    ValueEventListener userlistner;

    //Fetching data
    ProgressBar pb;

    public static final String TAG = NewAccont.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_accont);

        //Initializing connection with firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //Initializing progress bar
        pb = findViewById(R.id.pb_NewAccount);
        pb.setVisibility(View.VISIBLE);


        //Fetching User Lists

        loginIds =  databaseReference.child("/users");

        userlistner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Userlist = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String user = ds.getKey();
                    Userlist.add(user);
                }
                //list list of Users
                Log.i(TAG,"UserList => "+Userlist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG,"Error => "+databaseError.getDetails() );
                mt("Error Occurred !!! Try Again");
            }
        };

        loginIds.addValueEventListener(userlistner);

        //Initialize UI
        findViewById(R.id.btnSignUp_NewAccount).setOnClickListener(this::onSignUp);
        pb.setVisibility(View.INVISIBLE);

    }

    //After getting details by Customer
    private void onSignUp(View view) {


        String email = ((EditText) findViewById(R.id.txtEmail_NewAccount)).getText().toString();
        String password = ((EditText) findViewById(R.id.txtPassword_NewAccount)).getText().toString();

        if (email.isEmpty())
            mt("Email cannot be empty");
        else if (password.isEmpty())
            mt("Password cannot be empty");
        else if(Userlist.contains(email))
            mt("Username already used !!! Try New One");
        else {
            writeNewUser(email, password);
            mt("Account Created Successfully");

            Intent intent = new Intent(NewAccont.this, Login.class);
            startActivity(intent);
        }
    }

    //Write data into firebase realtime database
    private void writeNewUser(String email, String password) {

        (databaseReference.child("users"))
                .child(email)
                .setValue(password);
    }

    //Toast Dialog
    private void mt(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}
