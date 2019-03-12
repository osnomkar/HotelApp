package com.tourism.hotel.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public View view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
    }

    public MyAdapter(ArrayList<String> myDataset){
        dataSet = myDataset;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mycardview,viewGroup,false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ((TextView)viewHolder.itemView.findViewById(R.id.txv_name)).setText(dataSet.get(i));

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
