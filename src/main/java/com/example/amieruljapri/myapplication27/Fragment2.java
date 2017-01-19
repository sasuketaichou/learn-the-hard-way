package com.example.amieruljapri.myapplication27;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    List<PojoListTicketsValues> list;
    int newSt, assignSt, plannedSt, pendingSt, solvedSt, closedSt;
    ApiClient apiClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiClient = new ApiClient();
        list =apiClient.getTicketStatus();

        for(PojoListTicketsValues status : list){

            switch (status.status){

                case "1":
                    newSt++;
                    break;
                case "2":
                    assignSt++;
                    break;
                case "3":
                    plannedSt++;
                    break;
                case "4":
                    pendingSt++;
                    break;
                case "5":
                    solvedSt++;
                    break;
                case "6":
                    closedSt++;
                    break;
            }

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_home,container,false);

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

        return rootView;
    }
}
