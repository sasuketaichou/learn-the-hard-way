package com.example.amieruljapri.myapplication27;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by amierul.japri on 1/5/2017.
 */
public class ViewHolder3 extends RecyclerView.ViewHolder {
    TextView title, titleEdit;

    public ViewHolder3(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.titleView3);
        titleEdit = (TextView) itemView.findViewById(R.id.editTextTitleview3);
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getTitleEdit() {
        return titleEdit;
    }

}
