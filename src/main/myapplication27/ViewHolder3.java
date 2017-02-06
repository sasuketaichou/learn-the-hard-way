package com.example.amieruljapri.myapplication27;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by amierul.japri on 1/5/2017.
 */
public class ViewHolder3 extends RecyclerView.ViewHolder {
    TextView title,description;
    EditText titleEdit, descriptionEdit;

    public ViewHolder3(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.titleView3);
        description = (TextView)itemView.findViewById(R.id.description);
        titleEdit = (EditText)itemView.findViewById(R.id.editTextTitleview3);
        descriptionEdit = (EditText)itemView.findViewById(R.id.editTextDescription);

    }

    public TextView getTitle() {
        return title;
    }

    public TextView getDescription() {
        return description;
    }

    public EditText getTitleEdit() {
        return titleEdit;
    }

    public EditText getDescriptionEdit() {
        return descriptionEdit;
    }
}
