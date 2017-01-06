package com.example.amieruljapri.myapplication27;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by amierul.japri on 9/15/2016.
 */
public class Fragment1 extends Fragment implements ItemClickSupport.OnItemClickListener {

    List<Object> items = null;
    private GlpiDatabase database = null;
    private ComplexRecyclerViewAdapter adapter = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //database instantiate
        database = new GlpiDatabase(getActivity());

        items = new ArrayList<>();
        passData();

        //testing
        //ApiClient apiClient = new ApiClient(getContext());
        //apiClient.login("glpi","glpi");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //always set the third param as false
        View rootView =inflater.inflate(R.layout.fragment_create,container,false);

        Button button = (Button)rootView.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //passData();
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

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(this);

        return rootView;
    }

    public void passData(){

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
        //5
        items.add("view5");

        Log.v("retrofit","items : "+items.toString());

        //to refresh view
        //adapter.notifyDataSetChanged();
        Log.d("retrofit","notifydataset : "+items.toString());
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

        switch(position) {

            case 0:
            case 1:
            case 2:
            case 4:

            {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                final ViewHolder1 vh1 = (ViewHolder1) holder;
                Map<String, String> map = (Map<String, String>) items.get(position);

                final CharSequence[] myList = map.values().toArray(new CharSequence[map.size()]);

                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                //Getting text from a TextView returns CharSequence.
                // To convert CharSequence to String, toString() function is used.
                ad.setTitle(vh1.getTitle().getText().toString());
                ad.setSingleChoiceItems(myList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), myList[i], Toast.LENGTH_SHORT).show();

                        vh1.getItem().setText(myList[i]);
                        dialogInterface.dismiss();
                    }
                });

                ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //want something?
                    }
                });

                ad.show();

            }
            break;

            case 3:

            {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                final ViewHolder2 vh2 = (ViewHolder2) holder;
                final CharSequence[] choice = new CharSequence[]{"Yes", "No"};

                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setTitle(vh2.getTitle().getText().toString());
                ad.setSingleChoiceItems(choice, -2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (choice[i].equals("Yes")) {
                            vh2.getEmailAnswer().setText("YES");
                        } else {
                            vh2.getEmailAnswer().setText("NO");
                        }
                        dialogInterface.dismiss();
                    }
                });
                ad.show();
            }
            break;

            case 5:
            {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                final ViewHolder3 vh3 = (ViewHolder3) holder;

                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setTitle(vh3.getTitle().getText().toString());
                //ad.setTitle("  ");
                //ad.setMessage("hi cu all");

                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                //linearLayout.setPadding(100,1000,100,1000);

                final EditText titleBox = new EditText(getContext());
                TextView title = new TextView(getContext());
                title.setText("Title");
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                titleBox.setHint("Title");
                linearLayout.addView(title);
                linearLayout.addView(titleBox);

                final EditText descriptionBox = new EditText(getContext());
                TextView descTitle = new TextView(getContext());
                descTitle.setText("Description");
                descTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                descriptionBox.setHint("Description");
                //descriptionBox.setHeight(500);
                //descriptionBox.setWidth(5000);
                //descriptionBox.setPadding(100,100,100,100);
                linearLayout.addView(descTitle);
                linearLayout.addView(descriptionBox);

                ad.setView(linearLayout);

                ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //titleBox.getText().toString();
                        //.getText().toString();
                        dialogInterface.dismiss();
                    }
                });

                ad.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //what?
                    }
                });

                ad.show();


            }
        }
    }



}
