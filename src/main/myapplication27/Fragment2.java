package com.example.amieruljapri.myapplication27;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amierul.japri on 9/15/2016.
 */
public class Fragment2 extends Fragment {

    int newSt, assignSt, plannedSt, pendingSt, solvedSt, closedSt;
    View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AsyncGetList().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView =inflater.inflate(R.layout.fragment_home,container,false);
        return rootView;


    }

    private void initView() {

        TextView newSt = (TextView)rootView.findViewById(R.id.newSt);
        newSt.setText(String.valueOf(this.newSt));
        TextView assignSt = (TextView)rootView.findViewById(R.id.assignSt);
        assignSt.setText(String.valueOf(this.assignSt));
        TextView plannedSt = (TextView)rootView.findViewById(R.id.plannedSt);
        plannedSt.setText(String.valueOf(this.plannedSt));
        TextView pendingSt = (TextView)rootView.findViewById(R.id.pendingSt);
        pendingSt.setText(String.valueOf(this.pendingSt));
        TextView solvedSt = (TextView)rootView.findViewById(R.id.solvedSt);
        solvedSt.setText(String.valueOf(this.solvedSt));
        TextView closedSt = (TextView)rootView.findViewById(R.id.closedSt);
        closedSt.setText(String.valueOf(this.closedSt));
    }

    class AsyncGetList extends AsyncTask<Void,Void,Void>{
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ApiClient apiClient = ApiClient.getInstance(getContext());

            newSt = apiClient.getCountTicketStatus(1);
            assignSt = apiClient.getCountTicketStatus(2);
            plannedSt = apiClient.getCountTicketStatus(3);
            pendingSt = apiClient.getCountTicketStatus(4);
            solvedSt = apiClient.getCountTicketStatus(5);
            closedSt = apiClient.getCountTicketStatus(6);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            initView();
            dialog.dismiss();
        }
    }
}
