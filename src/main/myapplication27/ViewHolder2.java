package com.example.amieruljapri.myapplication27;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by amierul.japri on 11/16/2016.
 */

public class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView title, email,followup,emailAnswer,edit;

    public ViewHolder2(View v) {
        super(v);
        title = (TextView) v.findViewById(R.id.titleView2);
        email = (TextView) v.findViewById(R.id.emailView2);
        followup = (TextView)v.findViewById(R.id.followupView2);
        edit = (TextView)v.findViewById(R.id.editView2);
        edit.setOnClickListener(this);
        emailAnswer = (TextView)v.findViewById(R.id.emailAnswerView2);

    }

    public TextView getTitle() {
        return title;
    }

    public TextView getEmail() {
        return email;
    }

    public TextView getFollowup() {
        return followup;
    }

    public TextView getEmailAnswer() {
        return emailAnswer;
    }

    public TextView getEdit() {
        return edit;
    }

    @Override
    public void onClick(View view) {
        //do something


    }
}
