package com.tourism.hotel.hotelapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class recyclerBbAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Initialize Layouts
    private LayoutInflater inflater;
    private HashMap<String,database_BanquetBooking> bbdata;
    ArrayList<String> bregIds;
    public Context context;
    private database_BanquetBooking dbbb;


    public recyclerBbAdapter(Context context, ArrayList<String> mybregIds, HashMap<String,database_BanquetBooking> mybbdata){
        this.context = context;
        inflater = LayoutInflater.from(context);
        bregIds = mybregIds;
        bbdata = mybbdata;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //Inflation child layout into parent layout
        View v = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.mycard,viewGroup,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        dbbb = new database_BanquetBooking();

        dbbb = bbdata.get(bregIds.get(position));

        ((TextView)viewHolder.itemView.findViewById(R.id.txvRegId_card)).setText(dbbb.RegId);
        ((TextView)viewHolder.itemView.findViewById(R.id.txvName_card)).setText(dbbb.Name);
        ((TextView)viewHolder.itemView.findViewById(R.id.txvDate_card)).setText(dbbb.Date);
        ((TextView)viewHolder.itemView.findViewById(R.id.txvTime_card)).setText(dbbb.Time);

    }

    @Override
    public int getItemCount() {
        return bbdata.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        //Define Own View Holder
        TextView service;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //service = itemView.findViewById(R.id.txvService);
            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent = null;

            /*switch (getAdapterPosition()) {

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
            }*/

        }
    }
}
