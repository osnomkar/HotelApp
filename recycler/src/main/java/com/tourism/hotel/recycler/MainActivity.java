package com.tourism.hotel.recycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;
    public ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        //layoutManager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        list.add("333");
        list.add("222");
        list.add("888");
        list.add("999");
        list.add("777");

        adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnAdd).setOnClickListener(this::onAdd);
    }

    private void onAdd(View view) {

        String text = ((EditText)findViewById(R.id.txtNo)).getText().toString();
        list.add(text);
    }


    public void onFloatB(View view) {
        adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
