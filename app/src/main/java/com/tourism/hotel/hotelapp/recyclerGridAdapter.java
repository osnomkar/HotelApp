package com.tourism.hotel.hotelapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class recyclerGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Initialize Layouts
    private LayoutInflater inflater;
    private ArrayList<String> Services;
    public Context context;
    String UserEmail;


    public recyclerGridAdapter(Context context,ArrayList<String> myServices, String UserEmail){
        this.context = context;
        inflater = LayoutInflater.from(context);
        Services = myServices;
        this.UserEmail = UserEmail;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //Inflation child layout into parent layout
        View v = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.home_recycler_item,viewGroup,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((TextView)viewHolder.itemView.findViewById(R.id.txvService)).setText(Services.get(position));
    }

    @Override
    public int getItemCount() {
        return Services.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        //Define Own View Holder
        TextView service;
        ImageView icon;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            service = itemView.findViewById(R.id.txvService);
            icon = itemView.findViewById(R.id.imgService);
            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent = null;

            switch (getAdapterPosition()) {

                case 0:
                    intent = new Intent(context, TableBooking1.class);
                    break;
                case 1:
                    intent = new Intent(context, FoodParcel1.class);
                    break;
                case 2:
                    intent = new Intent(context, HomeDelivery1.class);
                    break;
                case 3:
                    intent = new Intent(context, BanquetBook1.class);
                    break;
            }
            //Passing UserName with Each Service
            Bundle bundle = new Bundle();
            bundle.putString("Key",UserEmail);
            intent.putExtras(bundle);
            context.startActivity(intent);

        }
    }
}
