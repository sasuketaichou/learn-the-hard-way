package com.example.amieruljapri.myapplication27;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by amierul.japri on 1/16/2017.
 */

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(LoginScreen.class.getSimpleName());
        Button button = (Button)findViewById(R.id.btn_login);
    }

    public void onClick(View v){
        EditText username = (EditText)findViewById(R.id.input_username);
        EditText password = (EditText)findViewById(R.id.input_password);

        final ApiClient apiClient = ApiClient.getInstance(getBaseContext());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        apiClient.login(username.getText().toString(), password.getText().toString(), false);
        Log.v("retrofit","login run");
        Log.v("retrofit",String.valueOf(apiClient.authenticate()));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(apiClient.authenticate()){
                    Log.v("retrofit","inside handler");
                    startActivity(new Intent(LoginScreen.this,MainActivity.class));
                }
                progressDialog.dismiss();
            }
        }, 2000);
    }
}
