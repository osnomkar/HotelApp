package com.tourism.hotel.hotelapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class admin extends AppCompatActivity {

    //Firebase Realtime Database Initialization
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    DatabaseReference tbReference;
    ValueEventListener tbRegIds,tbDetails;
    ArrayList<String> tregIds = new ArrayList<>();
    HashMap<String,database_tableBooking> tbdata = new HashMap<>();

    DatabaseReference hdReference;
    ValueEventListener hdRegIds,hdDetails;
    ArrayList<String> hregIds = new ArrayList<>();
    HashMap<String,database_HomeDelivery> hddata = new HashMap<>();

    DatabaseReference fpReference;
    ValueEventListener fpRegIds,fpDetails;
    ArrayList<String> fregIds = new ArrayList<>();
    HashMap<String,database_FoodParcel> fpdata = new HashMap<>();

    DatabaseReference bbReference;
    ValueEventListener bbRegIds,bbDetails;
    ArrayList<String> bregIds = new ArrayList<>();
    HashMap<String,database_BanquetBooking> bbdata = new HashMap<>();

    ProgressBar pbDetail;

    RecyclerView recyclerViewTb;
    RecyclerView.LayoutManager layoutManagerTb;

    RecyclerView recyclerViewFp;
    RecyclerView.LayoutManager layoutManagerFp;

    RecyclerView recyclerViewHd;
    RecyclerView.LayoutManager layoutManagerHd;

    RecyclerView recyclerViewBb;
    RecyclerView.LayoutManager layoutManagerBb;

    public static final String TAG = admin.class.getCanonicalName();

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOffscreenPageLimit(3);

        TabLayout tabLayout = findViewById(R.id.tabs);

        TabLayout.TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener =
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout);
        TabLayout.ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener =
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager);

        mViewPager.addOnPageChangeListener(tabLayoutOnPageChangeListener);
        tabLayout.addOnTabSelectedListener(viewPagerOnTabSelectedListener);

        //Can be made for refresh

        pbDetail = findViewById(R.id.pb);
        pbDetail.setVisibility(View.VISIBLE);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        tbReference = databaseReference.child("/TableBooking");
        hdReference = databaseReference.child("/HomeDelivery");
        fpReference = databaseReference.child("/FoodParcel");
        bbReference = databaseReference.child("/BanquetBooking");


        new fetchRegId().execute();

        (new Handler()).postDelayed(() -> new fetchDetail().execute(), 3000);

        (new Handler()).postDelayed(this::dispTabs, 10000);

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
            pbDetail.setVisibility(View.VISIBLE);
            new fetchRegId().execute();
            (new Handler()).postDelayed(() -> new fetchDetail().execute(), 3000);
            dispTabs();
            pbDetail.setVisibility(View.INVISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            /*// getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);*/

            switch (position){
                case 0:
                    tabTableBooking tabTableBooking = new tabTableBooking();
                    return tabTableBooking;

                case 1:
                    tabFoodParcel tabFoodParcel = new tabFoodParcel();
                    return tabFoodParcel;

                case 2:
                    tabHomeDelivery tabHomeDelivery = new tabHomeDelivery();
                    return tabHomeDelivery;

                case 3:
                    tabBanquetBooking tabBanquetBooking = new tabBanquetBooking();
                    return tabBanquetBooking;

                    default:
                        return null;
            }

        }



        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }

    private void mt(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void loadRegBB(){

        bbRegIds = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String bregId;
                    bregId = ds.getKey();
                    bregIds.add(bregId);
                }
                Log.i(TAG,"bb RegIds => "+bregIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mt("Unable to connect Database");
            }
        };

        bbReference.addValueEventListener(bbRegIds);


    }

    private void loadBB(){
        bbDetails = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String key = dataSnapshot.getKey();
                bbdata.put(key,dataSnapshot.getValue(database_BanquetBooking.class));
                Log.i(TAG,"BBdata => "+bbdata);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mt("Unable to fetch details");
            }
        };


        for (int i = 0; i <= bregIds.size() - 1; i++) {
            DatabaseReference dR = bbReference.child("/"+bregIds.get(i));
            dR.addValueEventListener(bbDetails);
        }
    }

    private void loadRegHD(){

        hdRegIds = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String hregId;
                    hregId = ds.getKey();
                    hregIds.add(hregId);
                }
                Log.i(TAG,"hd regIds => "+hregIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mt("Unable to connect Database");
            }
        };

        hdReference.addValueEventListener(hdRegIds);

    }

    private void loadHD(){
        hdDetails = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String key = dataSnapshot.getKey();
                hddata.put(key,dataSnapshot.getValue(database_HomeDelivery.class));
                Log.i(TAG,"Hddata => "+hddata);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mt("Unable to fetch details");
            }
        };


        for (int i = 0; i <= hregIds.size() - 1; i++) {
            DatabaseReference dR = hdReference.child("/"+hregIds.get(i));
            dR.addValueEventListener(hdDetails);
        }
    }

    private void loadRegFP(){

        fpRegIds = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String fregId;
                    fregId = ds.getKey();
                    fregIds.add(fregId);
                }
                Log.i(TAG,"Fp regIds => "+fregIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mt("Unable to connect Database");
            }
        };

        fpReference.addValueEventListener(fpRegIds);

    }

    private void loadFP(){
        fpDetails = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String key = dataSnapshot.getKey();
                fpdata.put(key,dataSnapshot.getValue(database_FoodParcel.class));
                Log.i(TAG,"fpdata => "+fpdata);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mt("Unable to fetch details");
            }
        };


        for (int i = 0; i <= fregIds.size() - 1; i++) {
            DatabaseReference dR = fpReference.child("/"+fregIds.get(i));
            dR.addValueEventListener(fpDetails);
        }
    }

    private void loadRegTB(){

        tbRegIds = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String tregId;
                    tregId = ds.getKey();
                    tregIds.add(tregId);
                }
                Log.i(TAG,"tb regids => "+tregIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mt("Unable to connect Database");
            }
        };

        tbReference.addValueEventListener(tbRegIds);

    }

    private void loadTB(){
        tbDetails = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String key = dataSnapshot.getKey();
                tbdata.put(key,dataSnapshot.getValue(database_tableBooking.class));
                Log.i(TAG,"tb data => "+tbdata);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mt("Unable to fetch details");
            }
        };


        for (int i = 0; i <= tregIds.size() - 1; i++) {
            DatabaseReference dR = tbReference.child("/"+tregIds.get(i));
            dR.addValueEventListener(tbDetails);
        }

    }

    class fetchRegId extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            loadRegBB();
            loadRegHD();
            loadRegFP();
            loadRegTB();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
        }
    }

    class fetchDetail extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            loadBB();
            loadFP();
            loadHD();
            loadTB();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
        }
    }

    private void dispTabs(){
        pbDetail.setVisibility(View.INVISIBLE);
        tabTB();
        tabFP();
        tabHD();
        tabBB();
    }

    private void tabTB(){

        recyclerViewTb = findViewById(R.id.recycler_tb);
        recyclerViewTb.setHasFixedSize(false);
        layoutManagerTb = new LinearLayoutManager(this);
        recyclerViewTb.setLayoutManager(layoutManagerTb);

        recyclerTbAdapter recyclerTbAdapter = new recyclerTbAdapter(this,tregIds,tbdata);
        recyclerViewTb.setAdapter(recyclerTbAdapter);

    }

    private void tabHD(){

        recyclerViewHd = findViewById(R.id.recycler_hd);
        recyclerViewHd.setHasFixedSize(false);
        layoutManagerHd = new LinearLayoutManager(this);
        recyclerViewHd.setLayoutManager(layoutManagerHd);

        recyclerHdAdapter recyclerHdAdapter = new recyclerHdAdapter(this,hregIds,hddata);
        recyclerViewHd.setAdapter(recyclerHdAdapter);
    }

    private void tabFP(){
        recyclerViewFp = findViewById(R.id.recycler_fp);
        recyclerViewFp.setHasFixedSize(false);
        layoutManagerFp = new LinearLayoutManager(this);
        recyclerViewFp.setLayoutManager(layoutManagerFp);

        recyclerFpAdapter recyclerFpAdapter = new recyclerFpAdapter(this,fregIds,fpdata);
        recyclerViewFp.setAdapter(recyclerFpAdapter);
    }

    private void tabBB(){
        recyclerViewBb = findViewById(R.id.recycler_bb);
        recyclerViewBb.setHasFixedSize(false);
        layoutManagerBb = new LinearLayoutManager(this);
        recyclerViewBb.setLayoutManager(layoutManagerBb);

        recyclerBbAdapter recyclerBbAdapter = new recyclerBbAdapter(this,bregIds,bbdata);
        recyclerViewBb.setAdapter(recyclerBbAdapter);
    }

}
