package com.example.amieruljapri.myapplication27;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by amierul.japri on 11/16/2016.
 */

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> items;
    public PojoCreateTicketValues ticket;

    public ComplexRecyclerViewAdapter(List<Object> items) {
        this.items = items;
        ticket = PojoCreateTicketValues.getInstance();
        //get data from oncreate fragment1
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch(viewType){
            case 0:
                View v1 = inflater.inflate(R.layout.layout_viewholder1,parent,false);
                viewHolder = new ViewHolder1(v1);
                break;
            case 1:
                View v2 = inflater.inflate(R.layout.layout_viewholder2,parent,false);
                viewHolder = new ViewHolder2(v2);
                break;
            case 2:
                View v3 = inflater.inflate(R.layout.layout_viewholder3,parent,false);
                viewHolder = new ViewHolder3(v3);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
                viewHolder = new ViewHolder1(v);
                break;
        }

        return viewHolder;
    }

    /**
     * This method internally calls onBindViewHolder(ViewHolder, int) to update the
     * RecyclerView.ViewHolder contents with the item at the given position
     * and also sets up some private fields to be used by RecyclerView.
     *
     * @param holder The type of RecyclerView.ViewHolder to populate
     * @param position Item position in the viewgroup.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //the logic of getview need to rethink

        switch(holder.getItemViewType()){
            case 0:
                ViewHolder1 vh1 = (ViewHolder1) holder;
                configureViewHolder1(vh1,position);
                break;
            case 1:
                ViewHolder2 vh2 = (ViewHolder2) holder;
                configureViewHolder2(vh2,position);
                break;
            case 2:
                ViewHolder3 vh3 = (ViewHolder3) holder;
                configureViewHolder3(vh3,position);
                break;
            default:
                break;
        }
    }

    private void configureViewHolder3(ViewHolder3 vh3, int position) {
        String title = null;
        String hint = null;
        switch (position){
            case 5:
                title = "TITLE";
                hint = "Title";
                break;
            case 6:
                title = "DESCRIPTION";
                hint = "Description";
                break;

        }

        vh3.getTitle().setText(title);
        vh3.getTitleEdit().setText(hint);
    }

    private void configureViewHolder2(ViewHolder2 vh2, int position) {
        //new view define here
        //for email, title
        String email = (String)items.get(position);

        if(email == null){
            email = "null";
        }
        else if(email.isEmpty()){
            email = "-";
        }
        //default value
        vh2.getEmail().setText(email);
        ticket.user_email = email;
        ticket.user_email_notification = "1";
    }

    private void configureViewHolder1(ViewHolder1 vh1, int position) {

        Map<String,String> map = (Map<String, String>) items.get(position);
        Log.v("retrofit","configureViewHolder1 : "+map.toString());

        if(!map.isEmpty()){

            //get all the value available
            List<String> list = new ArrayList<>();

            for(Map.Entry<String,String> entry : map.entrySet()){
                //get all key
                list.add(entry.getKey());
            }
            String defaultValue = map.get(list.get(0));
            //set default value
            vh1.getItem().setText(defaultValue);

            //title
            String mTitle = null;
            switch (position){
                case 0:
                    mTitle = "TYPE";
                    ticket.type = defaultValue;
                    break;
                case 1:
                    mTitle = "CATEGORY";
                    ticket.category = defaultValue;
                    break;
                case 2:
                    mTitle = "URGENCY";
                    ticket.urgency = defaultValue;
                    break;
                case 4:
                    mTitle = "HARDWARE";
                    ticket.itemtype = defaultValue;
                    break;
            }
            //set title
            vh1.getTitle().setText(mTitle);
        }
    }

    @Override
    public int getItemCount() {
        //Log.v("retrofit", "Items size getItemCount : "+String.valueOf(this.items.size()));
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        //multiple return is a bad practice
        int viewType = 9;
        switch (position){
            case 0:
                //ApiClient.TICKET_TYPE
            case 1:
                //ApiClient.TICKET_CATEGORY
            case 2:
                //ApiClient.TICKET_URGENCY
            case 4:
                //ApiClient.TICKET_HARDWARE
            case 7:
                //ApiClient.TICKET_LOCATION
                viewType = 0;
                break;
            case 3:
                viewType = 1;
                break;
            case 5:
            case 6:
                viewType = 2;
                break;

            default:
                break;
        }
        return viewType;
    }
}
