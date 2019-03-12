package com.tourism.hotel.tabbedactivity;

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

public class recyclerHdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Initialize Layouts
    private LayoutInflater inflater;
    private HashMap<String,database_HomeDelivery> hddata;
    ArrayList<String> hregIds;
    public Context context;
    private database_HomeDelivery dbhd;


    public recyclerHdAdapter(Context context, ArrayList<String> myhregIds, HashMap<String,database_HomeDelivery> myhddata){
        this.context = context;
        inflater = LayoutInflater.from(context);
        hregIds = myhregIds;
        hddata = myhddata;
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

        dbhd = new database_HomeDelivery();

        dbhd = hddata.get(hregIds.get(position));

        ((TextView)viewHolder.itemView.findViewById(R.id.txvRegId_card)).setText(dbhd.RegId);
        ((TextView)viewHolder.itemView.findViewById(R.id.txvName_card)).setText(dbhd.Name);
        ((TextView)viewHolder.itemView.findViewById(R.id.txvDate_card)).setText(dbhd.Date);
        ((TextView)viewHolder.itemView.findViewById(R.id.txvTime_card)).setText(dbhd.Time);

    }

    @Override
    public int getItemCount() {
        return hddata.size();
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
