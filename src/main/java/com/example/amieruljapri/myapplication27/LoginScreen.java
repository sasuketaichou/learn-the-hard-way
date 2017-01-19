package com.example.amieruljapri.myapplication27;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by amierul.japri on 1/16/2017.
 */

public class LoginScreen extends AppCompatActivity {
    private ApiClient apiClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        apiClient = new ApiClient();

        Button button = (Button)findViewById(R.id.btn_login);
    }

    public void onClick(View v){
        EditText username = (EditText)findViewById(R.id.input_username);
        EditText password = (EditText)findViewById(R.id.input_password);

        apiClient.login(username.getText().toString(), password.getText().toString(), false);
        Log.v("retrofit","login run");
        Log.v("retrofit",String.valueOf(apiClient.authenticate()));


        if(apiClient.authenticate()) {
            new LoginAsync().execute();
            Log.v("retrofit","apiclient auth");
        } else
            Toast.makeText(this,"Wrong username or password",Toast.LENGTH_SHORT).show();
    }

    private class LoginAsync extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... strings) {
            startActivity(new Intent(LoginScreen.this, MainActivity.class));
            Log.v("retrofit","doInbackground");
            return null;
        }
    }
}
