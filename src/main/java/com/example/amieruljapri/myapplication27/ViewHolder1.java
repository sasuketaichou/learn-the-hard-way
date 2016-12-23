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

    private TextView label1;
    private Spinner spinner;

    public ViewHolder1(View itemView) {
        super(itemView);
        label1 = (TextView) itemView.findViewById(R.id.text1);
        spinner = (Spinner) itemView.findViewById(R.id.spinner);
    }

    public TextView getLabel1() {
        return label1;
    }

    public Spinner getSpinner() {
        return spinner;
    }

}
