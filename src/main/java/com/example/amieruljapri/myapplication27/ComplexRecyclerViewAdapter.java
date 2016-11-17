package com.example.amieruljapri.myapplication27;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by amierul.japri on 11/16/2016.
 */

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> items;

    public ComplexRecyclerViewAdapter(List<Object> items) {
        this.items = items;
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
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
                viewHolder = new ViewHolder1(v);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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
        TicketCategory ticketCategory = (TicketCategory) items.get(position);
        if(ticketCategory != null){
            vh2.getLabel3().setText(ticketCategory.category);
            vh2.getLabel4().setText(ticketCategory.categoryRespond);
        }
    }

    private void configureViewHolder1(ViewHolder1 vh1, int position) {
        TicketType ticketType = (TicketType) items.get(position);
        if(ticketType != null){
            vh1.getLabel1().setText(ticketType.type);
            vh1.getLabel2().setText(ticketType.typeRespond);
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof TicketType){
            return 0;
        } else if (items.get(position) instanceof TicketCategory){
            return 1;
        }

        return -1;
    }
}
