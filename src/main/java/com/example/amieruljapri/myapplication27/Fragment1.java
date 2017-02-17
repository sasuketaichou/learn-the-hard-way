package com.example.amieruljapri.myapplication27;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by amierul.japri on 9/15/2016.
 */
public class Fragment1 extends Fragment implements ItemClickSupport.OnItemClickListener {

    List<Object> items = null;
    ComplexRecyclerViewAdapter adapter = null;
    PojoCreateTicketValues ticket;
    int SELECT_FILE = 1;
    int REQUEST_CAMERA = 2;
    int STORAGE_PERMISSION_CODE = 23;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_create, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                break;
            case R.id.action_send:
                ApiClient.getInstance(getContext()).createTicket(ticket);
                Log.v("retrofit",ticket.toString());
                break;
            case R.id.action_photo:
                if(permissionGranted()) {
                    
                    AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                    ad.setTitle("Photo");
                    CharSequence[] list = {"Camera", "Gallery"};
                    ad.setSingleChoiceItems(list, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == 0) {
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                startActivityForResult(intent, REQUEST_CAMERA);
                                dialogInterface.dismiss();
                            } else {
                                Intent intent = new Intent(
                                        Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/*");
                                startActivityForResult(
                                        Intent.createChooser(intent, "Select File"),
                                        SELECT_FILE);
                                dialogInterface.dismiss();
                            }
                        }
                    }).show();                   
                } else {
                    permissionRequest();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.v("permission","permission result");
        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(getContext(),"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(getContext(),"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void permissionRequest() {
        Log.v("permission","permission request");
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    private boolean permissionGranted() {
        Log.v("permission","permission granted");
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CAMERA){
                //camera
            } else {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(getContext(), selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);

                Bitmap bm = processBitmap(selectedImagePath);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                byte[] byteArray = bytes.toByteArray();
                String encoded = Base64.encodeToString(byteArray,Base64.DEFAULT);
            }
        }
    }

    private Bitmap processBitmap(String selectedImagePath) {
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        return bm;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        items = new ArrayList<>();
        passData();
        ticket = PojoCreateTicketValues.getInstance();

        //testing
        //ApiClient apiClient = ApiClient.getInstance(getContext());
        //apiClient.login("glpi","glpi",true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //always set the third param as false
        View rootView =inflater.inflate(R.layout.fragment_create,container,false);

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

        //database instantiate
        GlpiDatabase database = new GlpiDatabase(getActivity());

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
        items.add("view6");

        Log.v("retrofit","items : "+items.toString());

        //to refresh view
        //adapter.notifyDataSetChanged();
        Log.d("retrofit","notifydataset : "+items.toString());
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, final int position, final View v) {

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
                final CharSequence[] key = map.keySet().toArray(new CharSequence[map.size()]);

                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                //Getting text from a TextView returns CharSequence.
                // To convert CharSequence to String, toString() function is used.
                ad.setTitle(vh1.getTitle().getText().toString());
                ad.setSingleChoiceItems(myList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), key[i], Toast.LENGTH_SHORT).show();
                        createTicket(position,key[i].toString());
                        vh1.getItem().setText(myList[i]);
                        dialogInterface.dismiss();
                    }
                });

                ad.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
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
                final CharSequence[] choice = new CharSequence[]{"Yes","No"};

                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setTitle(vh2.getTitle().getText().toString());
                ad.setSingleChoiceItems(choice, -2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        vh2.getEmailAnswer().setText(choice[i]);
                        int answer = choice[i].equals("Yes")? 1:0;
                        ticket.user_email_notification = String.valueOf(answer);
                        dialogInterface.dismiss();
                    }
                });
                ad.show();
            }
            break;

            case 5:
            case 6:

            {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                final ViewHolder3 vh3 = (ViewHolder3) holder;

                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());

                ad.setTitle(vh3.getTitle().getText().toString());
                //ad.setMessage("");

                final EditText edittext = new EditText(getContext());
                ad.setView(edittext);


                ad.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //titleBox.getText().toString();
                        //.getText().toString();
                        String answer = edittext.getText().toString();
                        vh3.getTitleEdit().setText(answer);
                        if(position == 5){
                            ticket.title = answer;
                        } else {
                            ticket.content1 = answer;
                        }
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

    private void createTicket(int position, String string) {

        switch(position){
            case 0:
                ticket.type = string;
                break;
            case 1:
                ticket.category = string;
                break;
            case 2:
                ticket.urgency = string;
                break;
            case 4:
                ticket.itemtype = string;
                break;
        }
    }


}
