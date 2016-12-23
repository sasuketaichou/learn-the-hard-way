package com.example.amieruljapri.myapplication27;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amierul.japri on 11/16/2016.
 */

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ArrayList<String>> items;
    private Context context= null;

    public ComplexRecyclerViewAdapter(List<ArrayList<String>> items) {
        this.items = items;
        //get data from oncreate fragment1
        //eg: tickettype, category, location, etc
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        switch(viewType){
            case 0:
                View v1 = inflater.inflate(R.layout.layout_viewholder1,parent,false);
                viewHolder = new ViewHolder1(v1);
                break;
            case 1:
                View v2 = inflater.inflate(R.layout.layout_viewholder2,parent,false);
                viewHolder = new ViewHolder2(v2);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
                viewHolder = new ViewHolder1(v);
                break;
        }

        return viewHolder;
    }

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
            default:
                break;
        }
    }

    private void configureViewHolder2(ViewHolder2 vh2, int position) {
        //new view define here
        //for email, title

    }

    private void configureViewHolder1(ViewHolder1 vh1, int position) {

        //receive list of ticket type here
        //check the size of list

        ArrayList<String> list = items.get(position);

        if(!list.isEmpty()){
            //title
            vh1.getLabel1().setText(list.get(0));

            /**
             * CAREFULL!!!
             * repeated use can crash view
             */
            //remove title
            list.remove(0);
            //this is spinner
            ArrayAdapter spinnerAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item,list);
            vh1.getSpinner().setAdapter(spinnerAdapter);


        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {

        String title = items.get(position).get(0);

        //multiple return is a bad practice
        int viewType = 0;

        switch (title){
            case ApiClient.TICKET_TYPE:
            case ApiClient.TICKET_CATEGORY:
            case ApiClient.TICKET_LOCATION:
            case ApiClient.TICKET_URGENCY:
            case ApiClient.TICKET_HARDWARE:
                viewType = 0;
                break;
            case ApiClient.BASE_URL:
                viewType = 1;
                break;
        }

        return viewType;
    }
}
