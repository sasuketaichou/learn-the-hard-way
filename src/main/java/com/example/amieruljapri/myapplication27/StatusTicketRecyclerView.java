package com.example.amieruljapri.myapplication27;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by amierul.japri on 2/10/2017.
 */

public class StatusTicketRecyclerView extends RecyclerView.Adapter<StatusTicketRecyclerView.ViewHolder> {

    List<Object> item;

    public StatusTicketRecyclerView(List<Object> item) {
        Log.v("fragment4","statusticketrecycler");
        this.item = item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.statusticketviewholder,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PojoListTicketsValues ticket = (PojoListTicketsValues) item.get(position);
        TextView id = holder.id;
        id.setText(ticket.id);
        TextView title = holder.title;
        title.setText(ticket.name);
        TextView type = holder.type;
        type.setText(ticket.type);
        TextView open = holder.openDate;
        open.setText(ticket.date);
        TextView category = holder.category;
        category.setText(ticket.itilcategoriesId);
        TextView eng = holder.engineer;
        eng.setText(ticket.users.requester.get(0).name);
        TextView close = holder.closeDate;
        close.setText(ticket.closedate);

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView id,title,type,openDate,category,engineer,closeDate;

        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView)itemView.findViewById(R.id.idStatus);
            title = (TextView)itemView.findViewById(R.id.titleStatus);
            type = (TextView)itemView.findViewById(R.id.typeStatus);
            openDate = (TextView)itemView.findViewById(R.id.openStatus);
            category = (TextView)itemView.findViewById(R.id.categoryStatus);
            engineer = (TextView)itemView.findViewById(R.id.engStatus);
            closeDate = (TextView)itemView.findViewById(R.id.closeStatus);
        }
    }
}
