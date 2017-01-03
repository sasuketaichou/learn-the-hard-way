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
    private Context context = null;

    public ComplexRecyclerViewAdapter(List<Object> items) {
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

    private void configureViewHolder2(final ViewHolder2 vh2, int position) {
        //new view define here
        //for email, title
        final String title = "Email followup";
        String email = (String)items.get(position);
        if(email.isEmpty()){
            email = "-";
        }
        vh2.getEmail().setText(email);

        //charsequence
        final CharSequence[] choice = new CharSequence[]{"Yes","No"};

        //set edit onclick
//        vh2.getEdit().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder ad = new AlertDialog.Builder(context);
//                ad.setTitle(title);
//                ad.setSingleChoiceItems(choice, -2, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                       if(choice[i].equals("Yes")){
//                           vh2.getEmailAnswer().setText("YES");
//                       }else {
//                           vh2.getEmailAnswer().setText("NO");
//                       }
//
//                        dialogInterface.dismiss();
//                    }
//                });
//
//                ad.show();
//            }
//        });



    }

    private void configureViewHolder1(final ViewHolder1 vh1, int position) {
        //receive list of ticket type here
        //check the size of list
        Map<String,String> map = (Map<String, String>) items.get(position);
        Log.v("retrofit","configureViewHolder1 : "+map.toString());

        if(!map.isEmpty()){
            //title
            String mTitle = null;
            switch (position){
                case 0:
                    mTitle = "TYPE";
                    break;
                case 1:
                    mTitle = "URGENCY";
                    break;
                case 2:
                    mTitle = "CATEGORY";
                    break;
                case 4:
                    mTitle = "HARDWARE";
                    break;
            }
            //set title
            final String title = mTitle;
            vh1.getTitle().setText(title);

            //get all the value available
            List<String> list = new ArrayList<>();
            for(Map.Entry<String,String> entry : map.entrySet()){
                //get all key
                list.add(entry.getKey());
            }

            //set default value
            vh1.getItem().setText(map.get(list.get(0)));

            //convert map values to charsequence
            final CharSequence[] myList = map.values().toArray(new CharSequence[map.size()]);


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
                            //want something?
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

            default:
                break;
        }

        Log.v("retrofit","Viewtype :"+String.valueOf(viewType));
        return viewType;
    }
}
