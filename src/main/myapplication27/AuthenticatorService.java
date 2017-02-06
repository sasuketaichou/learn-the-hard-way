package com.example.amieruljapri.myapplication27;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by amierul.japri on 12/15/2016.
 */

public class AuthenticatorService extends Service {
    private Authenticator mAuthenticator;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }

    @Override
    public void onCreate() {
        mAuthenticator = new Authenticator(this);
    }
}
