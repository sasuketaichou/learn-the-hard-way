package com.example.amieruljapri.myapplication27;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amierul.japri on 9/15/2016.
 */
public class Fragment1 extends Fragment {

    private List<Object> items;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = new ArrayList<>();
        items.add(new TicketType());
        items.add(new TicketCategory());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //always set the third param as false
        View rootView =inflater.inflate(R.layout.gallery_fragment,container,false);

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        //need to declare as LinearLayoutManager as to use getOrientation method below
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //pass the adapter or data in onCreate
        recyclerView.setAdapter(new ComplexRecyclerViewAdapter(items));

        //divider, since Oct 16, google implement DividerItemDecoration.java in recyclerview support library 25
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation()));
        
        return rootView;

    }
}
