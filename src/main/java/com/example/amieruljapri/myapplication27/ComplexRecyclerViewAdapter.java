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
import java.util.List;

/**
 * Created by amierul.japri on 11/16/2016.
 */

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ArrayList<String>> items;
    private Context context = null;

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

    private void configureViewHolder1(final ViewHolder1 vh1, int position) {
        //receive list of ticket type here
        //check the size of list
        ArrayList<String> list = items.get(position);
        Log.v("retrofit","configureViewHolder1 : "+list);

        if(!list.isEmpty()){
            //title
            final String title = list.get(0);
            vh1.getTitle().setText(title);
            vh1.getItem().setText(list.get(1));

            //bad practice
            //Fixme
            //list.remove(0);
            //final CharSequence[] myList = list.toArray(new CharSequence[list.size()]);

             /**
             * above is legit if notifydatasetchanged is call once
             * since onbindviewholder will be call several times
             * it will keep the old list (list.remove)
             * as reference to update recyclerview
             **/

            final CharSequence[] myList = new CharSequence[list.size()-1];
            for(int i =1; i<list.size();i++){
                myList[i-1] = list.get(i);
            }

            vh1.getEdit().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //set alert dialog
                    AlertDialog.Builder ad = new AlertDialog.Builder(context);
                    ad.setTitle(title);
                    ad.setSingleChoiceItems(myList, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context,myList[i],Toast.LENGTH_SHORT).show();

                            //final viewholder
                            //still in testing
                            //what is the consequences
                            vh1.getItem().setText(myList[i]);
                            dialogInterface.dismiss();
                        }
                    });

                    ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context,"Cancel",Toast.LENGTH_SHORT).show();
                        }
                    });

                    ad.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        Log.v("retrofit", "Items size getItemCount : "+String.valueOf(this.items.size()));
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {

        String title = items.get(position).get(0);
        Log.v("retrofit","getItemViewType title : "+title);

        //multiple return is a bad practice
        int viewType = 9;
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

        Log.v("retrofit","Viewtype :"+String.valueOf(viewType));
        return viewType;
    }
}
