package com.example.amieruljapri.myapplication27;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by amierul.japri on 12/19/2016.
 */

public class GlpiDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Glpi.db";
    public static final String TABLE_NAME = "GlpiCreateTicket";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TYPE = "Type";
    public static final String COLUMN_ITEM = "Item";
    public static final String COLUMN_ITEM_ID = "ID";

    //debug
    public String TAG = "retrofit";

    public GlpiDatabase(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+" ("
                +COLUMN_ID      +" INTEGER PRIMARY KEY, "
                +COLUMN_ITEM_ID +" TEXT, "
                +COLUMN_ITEM    +" TEXT, "
                +COLUMN_TYPE    +" TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //for development only
        //prepare for data migration in case table scheme is updated
        //so no data loss occur
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //suppose to be dynamic
    public Map<String,String> getListDropdownValue(String value){

        Map<String,String> map = new TreeMap<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_TYPE+" = ?",new String[]{value});

        cursor.moveToFirst();
        Log.d(TAG,"Query is valid and Move to 1st executed");

        while(!cursor.isAfterLast()){
            String item = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM));
            String id = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_ID));
            map.put(id,item);
            cursor.moveToNext();
        }
        Log.d(TAG,"cursor Hashmap: "+map.toString());

        cursor.close();
        return map;
    }

    //getemail
    public String getEmail(String name){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ITEM_ID+" = ?",new String[]{name});
        cursor.moveToFirst();

        String email = null;

        while(!cursor.isAfterLast()) {
            email = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM));
            cursor.moveToNext();
        }
        cursor.close();


        return email;
    }
}
