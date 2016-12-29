package com.example.amieruljapri.myapplication27;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amierul.japri on 11/16/2016.
 */

public class ViewHolder1 extends RecyclerView.ViewHolder {

    private TextView title,item,edit;

    public ViewHolder1(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.titleView1);
        item = (TextView) itemView.findViewById(R.id.item);
        edit = (TextView)itemView.findViewById(R.id.editView1);
    }


    public TextView getTitle() {
        return title;
    }

    public TextView getItem() {
        return item;
    }

    public TextView getEdit() {
        return edit;
    }
}
