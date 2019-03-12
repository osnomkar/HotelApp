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

public class recyclerFpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Initialize Layouts
    private LayoutInflater inflater;
    private HashMap<String,database_FoodParcel> fpdata;
    ArrayList<String> fregIds;
    public Context context;
    private database_FoodParcel dbfp;


    public recyclerFpAdapter(Context context, ArrayList<String> myfregIds, HashMap<String,database_FoodParcel> myfpdata){
        this.context = context;
        inflater = LayoutInflater.from(context);
        fregIds = myfregIds;
        fpdata = myfpdata;
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

        dbfp = new database_FoodParcel();

        dbfp = fpdata.get(fregIds.get(position));

        ((TextView)viewHolder.itemView.findViewById(R.id.txvRegId_card)).setText(dbfp.RegId);
        ((TextView)viewHolder.itemView.findViewById(R.id.txvName_card)).setText(dbfp.Name);
        ((TextView)viewHolder.itemView.findViewById(R.id.txvDate_card)).setText(dbfp.Date);
        ((TextView)viewHolder.itemView.findViewById(R.id.txvTime_card)).setText(dbfp.Time);

    }

    @Override
    public int getItemCount() {
        return fpdata.size();
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
