package com.example.amieruljapri.myapplication27;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by amierul.japri on 9/15/2016.
 */
public class Fragment1 extends Fragment {

    List<Object> items = null;
    private GlpiDatabase database = null;
    private ComplexRecyclerViewAdapter adapter = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //database instantiate
        database = new GlpiDatabase(getActivity());

        items = new ArrayList<>();

        //testing
        //ApiClient apiClient = new ApiClient(getContext());
        //apiClient.login("glpi","glpi");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //always set the third param as false
        View rootView =inflater.inflate(R.layout.gallery_fragment,container,false);

        Button button = (Button)rootView.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passData();
            }
        });

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        //need to declare as LinearLayoutManager as to use getOrientation method below
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //instantiate adapter
        adapter = new ComplexRecyclerViewAdapter(items);

        recyclerView.setAdapter(adapter);

        //divider, since Oct 16, google implement DividerItemDecoration.java in recyclerview support library 25
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation()));

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getContext(),v.toString()+" : "+position,Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    //testing purpose
    public void passData(){

        /*
        * after login() success
        * updating database, only after that
        * database.getListDropdownValue method
        * is available
        */

        //add all list dropdown here
        //0
        items.add(database.getListDropdownValue(ApiClient.TICKET_TYPE));
        //1
        items.add(database.getListDropdownValue(ApiClient.TICKET_URGENCY));
        //2
        items.add(database.getListDropdownValue(ApiClient.TICKET_CATEGORY));
        //email view
        //3
        items.add(database.getEmail("glpi"));
        //4
        items.add(database.getListDropdownValue(ApiClient.TICKET_HARDWARE));

        Log.v("retrofit","items : "+items.toString());

        //to refresh view
        adapter.notifyDataSetChanged();
        Log.d("retrofit","notifydataset : "+items.toString());
    }
}