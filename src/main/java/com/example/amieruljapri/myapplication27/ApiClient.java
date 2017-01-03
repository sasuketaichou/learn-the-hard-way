package com.example.amieruljapri.myapplication27;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amierul.japri on 12/19/2016.
 */

public class ApiClient {

    public static final String TICKET_TYPE = "tickettype";
    public static final String TICKET_CATEGORY = "itilcategory";
    public static final String TICKET_URGENCY = "ticketurgency";
    public static final String TICKET_HARDWARE = "helpdesk";
    public static final String TICKET_LOCATION = "location";
    public static final String EMAIL = "email_user";


    private String METHOD = "method";
    private String SESSION = "session";

    private String METHOD_DROPDOWNVALUES = "glpi.listDropdownValues";
    private String DROPDOWNVALUES_DROPDOWN = "dropdown";


    private String METHOD_LOGIN = "glpi.doLogin";
    private String METHOD_LOGIN_PASS = "login_password";
    private String METHOD_LOGIN_USERNAME = "login_name";

    private String METHOD_HELPDESK = "glpi.listHelpdeskTypes";

    private String METHOD_MYINFO = "glpi.getMyInfo";

    public static final String BASE_URL = "http://10.4.133.211/glpi/plugins/webservices/";
    private static Retrofit retrofit = null;

    private String session = null;
    private String TAG = "retrofit";
    private ApiInterface apiService = null;
    private GlpiDatabase database = null;
    private ArrayList<String> mType = null;
    private int count;

    public ApiClient(Context context){
        //get context for database
        database = new GlpiDatabase(context);
    }

    //to be used only inside this class
    //tutorial used to be public
    private Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public void updateDropdown(String type){
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

    public void login(String username, String pass) {

        Map<String,String> data = new HashMap<>();
        data.put(METHOD,METHOD_LOGIN);
        data.put(METHOD_LOGIN_PASS,pass);
        data.put(METHOD_LOGIN_USERNAME,username);

        //instantiate apiService
        //if login is not called, other method in this class
        //cannot be used
        apiService = getClient().create(ApiInterface.class);

        Call<GlpiLogin> loginCall =apiService.login(data);
        Log.d(TAG,"apiservice not null. great");

        loginCall.enqueue(new Callback<GlpiLogin>() {
            @Override
            public void onResponse(Call<GlpiLogin> call, Response<GlpiLogin> response) {

                /**
                 logincall is on background.
                 after ui thread is finish, then background (enqueue)
                 only after that u get the session key required
                 to use other rest GLPI method
                */

                session = response.body().session;
                Log.d(TAG,"lOGINsuccess :"+session);

                //to set type
                mType = new ArrayList<>();

                //updateDropdown(TICKET_TYPE);
                //updateDropdown(TICKET_URGENCY);
                //updateDropdown(TICKET_CATEGORY);

                //different method
                //updateDropdownHardware();

                //getinfo
                //getInfo();

                //resetCount();

            }

            @Override
            public void onFailure(Call<GlpiLogin> call, Throwable t) {

                Log.e(TAG,"LOGINfailed"+t);
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
}
