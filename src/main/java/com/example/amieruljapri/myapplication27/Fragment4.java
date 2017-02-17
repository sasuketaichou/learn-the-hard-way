package com.example.amieruljapri.myapplication27;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amierul.japri on 1/5/2017.
 */

public class Fragment4 extends Fragment {
    List<Object> list;
    StatusTicketRecyclerView adapter;
    RecyclerView recyclerView;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_ticket, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        switch (i){
            case R.id.action_filter:
                Toast.makeText(getActivity(),"Filter",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rvTicket);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.v("fragment4",list.toString());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation()));
        Log.v("fragment4","oncreateView fragment 4");
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.filter_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Filter"));
        tabLayout.addTab(tabLayout.newTab().setText("Clear"));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(getActivity(),"Tab : "+tab.getPosition(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AsyncGetTicketList().execute();
        setHasOptionsMenu(true);
        Log.v("fragment4","oncreate fragment 4");
    }


    class AsyncGetTicketList extends AsyncTask<String,Void,Void> {
        ProgressDialog dialog;

        @Override
        protected Void doInBackground(String... strings) {
            ApiClient apiClient = ApiClient.getInstance(getContext());

            list = new ArrayList<>();
            list =apiClient.getTicketStatus();
            Log.v("fragment4","doinbackground");
            Log.v("fragment4",((PojoListTicketsValues)list.get(0)).id);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new StatusTicketRecyclerView(list);
            recyclerView.setAdapter(adapter);
            //adapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    }
}
