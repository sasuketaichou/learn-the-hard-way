package com.example.amieruljapri.myapplication27;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amierul.japri on 12/19/2016.
 */

public final class ApiClient {

    private static volatile ApiClient instance;

    public static final String TICKET_TYPE = "tickettype";
    public static final String TICKET_CATEGORY = "itilcategory";
    public static final String TICKET_URGENCY = "ticketurgency";
    public static final String TICKET_HARDWARE = "helpdesk";
    public static final String TICKET_LOCATION = "location";
    public static final String EMAIL = "email_user";


    private String METHOD = "method";
    private String SESSION = "session";

    private String METHOD_CREATETICKET = "glpi.createTicket";

    private String METHOD_DROPDOWNVALUES = "glpi.listDropdownValues";
    private String DROPDOWNVALUES_DROPDOWN = "dropdown";


    private String METHOD_LOGIN = "glpi.doLogin";
    private String METHOD_LOGIN_PASS = "login_password";
    private String METHOD_LOGIN_USERNAME = "login_name";

    private String METHOD_HELPDESK = "glpi.listHelpdeskTypes";

    private String METHOD_LISTTICKETS = "glpi.listTickets";

    private String METHOD_MYINFO = "glpi.getMyInfo";

    public static final String BASE_URL = "http://10.4.133.211/glpi/plugins/webservices/";
    private static Retrofit retrofit = null;

    private String session = null;
    private String TAG = "retrofit";
    private ApiInterface apiService = null;
    private GlpiDatabase database = null;
    private ArrayList<String> mType = null;
    private int count;
    private boolean authentication;
    public Context context;

    public static ApiClient getInstance(Context context){
        if(instance == null){
            synchronized (ApiClient.class){
                if(instance == null){
                    instance = new ApiClient(context);
                }
            }
        }
        return instance;
    }

    public ApiClient(Context context){
        this.context = context;

        //get context for database
        if(database == null) {
            database = new GlpiDatabase(context);
        }
    }



    //to be used only inside this class
    //tutorial used to be public
    private Retrofit getClient(){
        if(retrofit == null){

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    private void updateDropdown(String type){
        //map for multiple data/params
        Map<String,String> data = new HashMap<>();
        data.put(METHOD,METHOD_DROPDOWNVALUES);
        data.put(SESSION,session);
        data.put(DROPDOWNVALUES_DROPDOWN,type);

        //to replace type every call
        mType.add(type);

        /**
         *  enqueue is background
         *  first in first out
         *  so mType should get the
         *  count = 0 for the
         *  1st executed enqueue
         */

        Call<List<PojoTypeUrgencyValues>> call = apiService.getTypeUrgencyValues(data);
        call.enqueue(new Callback<List<PojoTypeUrgencyValues>>() {
            @Override
            public void onResponse(Call<List<PojoTypeUrgencyValues>> call, Response<List<PojoTypeUrgencyValues>> response) {

                //to increase count
                //for background use
                count++;

                //getWritable
                SQLiteDatabase db = database.getWritableDatabase();
                ContentValues cv = new ContentValues();

                //to iterate without the use of count
                //while(mType.iterator().hasNext())

                for (int i =0; i<response.body().size();i++){

                    cv.put(GlpiDatabase.COLUMN_ITEM, response.body().get(i).name);
                    cv.put(GlpiDatabase.COLUMN_TYPE,mType.get(count));
                    cv.put(GlpiDatabase.COLUMN_ITEM_ID,String.valueOf(response.body().get(i).id));
                    Log.d(TAG,GlpiDatabase.COLUMN_ITEM+" : "+response.body().get(i).name);

                    //insert to database
                    db.insert(GlpiDatabase.TABLE_NAME,null,cv);
                    Log.d(TAG,cv.toString());
                }
            }

            @Override
            public void onFailure(Call<List<PojoTypeUrgencyValues>> call, Throwable t) {
                Log.e(TAG,mType.get(count)+" :"+t);
            }
        });
    }

    //pass update as true to update for 1st time
    //to be continue using shared preferences
    public void login(String username, String pass, final boolean update) {
        
        Map<String,String> data = new HashMap<>();
        data.put(METHOD,METHOD_LOGIN);
        data.put(METHOD_LOGIN_PASS,pass);
        data.put(METHOD_LOGIN_USERNAME,username);

        //instantiate apiService
        //if login is not called, other method in this class
        //cannot be used
        if(apiService == null){
            apiService = getClient().create(ApiInterface.class);
        }


        Call<GlpiLogin> loginCall =apiService.login(data);
        Log.d(TAG,"apiservice not null. great");

        loginCall.enqueue(new Callback<GlpiLogin>() {
            @Override
            public void onResponse(Call<GlpiLogin> call, Response<GlpiLogin> response) {

                /*
                 logincall is on background.
                 after ui thread is finish, then background (enqueue)
                 only after that u get the session key required
                 to use other rest GLPI method
                 */
                session = response.body().session;
                authentication = session!=null;
                Log.d(TAG,"lOGINsuccess :" + session);
                Log.v(TAG,"lOGINsuccess :"+authentication);

                if (update) {

                    //to set type
                    mType = new ArrayList<>();

                    updateDropdown(TICKET_TYPE);
                    updateDropdown(TICKET_URGENCY);
                    updateDropdown(TICKET_CATEGORY);

                    //different method
                    updateDropdownHardware();

                    //getinfo
                    getInfo();

                    resetCount();
                }

            }

            @Override
            public void onFailure(Call<GlpiLogin> call, Throwable t) {

                Log.e(TAG,"Login v1 : "+t+"\n"+call.request()+"\n"+call.request().url());
            }
        });
    }

    //set count to -1 everytime count is been used
    private void resetCount() {
        count = -1;
        //count = (count != -1)? -1:-1;
    }

    private void updateDropdownHardware() {

        Map<String,String> data = new HashMap<>();
        //different method
        data.put(METHOD,METHOD_HELPDESK);
        data.put(SESSION,session);

        Call<List<PojoHelpdeskValues>> call = apiService.getHelpdeskValues(data);
        call.enqueue(new Callback<List<PojoHelpdeskValues>>() {
            @Override
            public void onResponse(Call<List<PojoHelpdeskValues>> call, Response<List<PojoHelpdeskValues>> response) {
                SQLiteDatabase db = database.getWritableDatabase();
                ContentValues cv = new ContentValues();

                for (int i =0; i<response.body().size();i++) {

                    cv.put(GlpiDatabase.COLUMN_ITEM, response.body().get(i).name);
                    cv.put(GlpiDatabase.COLUMN_TYPE, TICKET_HARDWARE);
                    cv.put(GlpiDatabase.COLUMN_ITEM_ID, response.body().get(i).id);
                    Log.d(TAG, GlpiDatabase.COLUMN_ITEM + " : " + response.body().get(i).name);

                    db.insert(GlpiDatabase.TABLE_NAME, null, cv);
                    Log.d(TAG, cv.toString());
                }
            }

            @Override
            public void onFailure(Call<List<PojoHelpdeskValues>> call, Throwable t) {
                Log.e(TAG,TICKET_HARDWARE+" : "+t);
            }
        });
    }

    private void getInfo(){

        Map<String,String> data = new HashMap<>();
        data.put(METHOD,METHOD_MYINFO);
        data.put(SESSION,session);

        Call<PojoMyInfo> call = apiService.getMyInfo(data);
        call.enqueue(new Callback<PojoMyInfo>() {
            @Override
            public void onResponse(Call<PojoMyInfo> call, Response<PojoMyInfo> response) {
                SQLiteDatabase db = database.getWritableDatabase();
                ContentValues cv = new ContentValues();
                //for now
                //just to get email
                //for view 3
                cv.put(GlpiDatabase.COLUMN_TYPE,EMAIL);
                cv.put(GlpiDatabase.COLUMN_ITEM_ID,response.body().name);
                cv.put(GlpiDatabase.COLUMN_ITEM,response.body().email);
                db.insert(GlpiDatabase.TABLE_NAME,null,cv);
            }

            @Override
            public void onFailure(Call<PojoMyInfo> call, Throwable t) {

            }
        });
    }

    public List<Object> getTicketStatus(){

        List<Object> list = new ArrayList<>();

        Map<String,String> data = new HashMap<>();
        data.put(METHOD,METHOD_LISTTICKETS);
        data.put(SESSION,session);
        Log.v("retrofit","List ticketvalues Session : "+session);
        //mine : mirror the user requesting for ticket
        data.put("mine","");
        //id2name : option to enable id to name translation of dropdown fields
        data.put("id2name","");
        //limit is to be decide
        data.put("limit","100");

        Call<List<PojoListTicketsValues>> call = apiService.getListticketValues(data);
        Response<List<PojoListTicketsValues>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null) {
            for (PojoListTicketsValues mResponse : response.body()){
                list.add(mResponse);
                Log.v("retrofit","List ticketvalues Status : "+mResponse.status);
            }
        }
        return list;
    }

    public Integer getCountTicketStatus(int status){

        Map<String,String> data = new HashMap<>();
        data.put(METHOD,METHOD_LISTTICKETS);
        data.put(SESSION,session);
        data.put("mine","");
        data.put("id2name","");
        data.put("count","");
        data.put("limit","100");
        data.put("status",String.valueOf(status));

        Call<PojoCountTicketStatus> call = apiService.getcountTicketStatus(data);

        try {
            Log.v(TAG,call.request().toString());
            return Integer.valueOf(call.execute().body().count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean authenticate() {
        //Log.v("retrofit",session);
        return authentication;
    }

    public void createTicket(PojoCreateTicketValues ticket){

        Map<String,String> data = new HashMap<>();
        data.put(METHOD,METHOD_CREATETICKET);
        data.put(SESSION,session);
        data.put("title",ticket.title);
        data.put("content",ticket.content1);
        data.put("type",ticket.type);
        data.put("urgency",ticket.urgency);
        data.put("category",ticket.category);
        data.put("user_email",ticket.user_email);
        data.put("user_email_notification",ticket.user_email_notification);
        data.put("item",ticket.item);
        data.put("itemtype",ticket.itemtype);

        Call<PojoCreateTicketValues> call = apiService.createTicketValues(data);
        call.enqueue(new Callback<PojoCreateTicketValues>() {
            @Override
            public void onResponse(Call<PojoCreateTicketValues> call, Response<PojoCreateTicketValues> response) {
                Log.v(TAG,"Success : "+response.raw().toString());
            }

            @Override
            public void onFailure(Call<PojoCreateTicketValues> call, Throwable t) {
                Log.v(TAG,"Failed : "+t.toString()+" :::: "+call.request());
            }
        });

    }
}
