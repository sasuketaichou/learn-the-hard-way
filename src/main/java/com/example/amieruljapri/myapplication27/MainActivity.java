package com.example.amieruljapri.myapplication27;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends FragmentActivity {

    public static final String AUTHORITY = "com.example.amieruljapri.myapplication27";
    //same as authenticator.xml
    public static final String ACCOUNT_TYPE = "com.example.amieruljapri";
    public static final String ACCOUNT = "dummyaccount";
    Account mAccount;
    
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //create Dummy account
        mAccount = CreateSyncAccount(this);

        if(findViewById(R.id.contentContainer) != null){
            if (savedInstanceState != null) {
                return;
            }
        }

        //this start 1st
        //and then recreate again for bottombar
        fragment1 = new Fragment1();
        fragment3 = new Fragment3();
        fragment2 = new Fragment2();
        fragment2.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentContainer, fragment2).commit();
        
        initLay();
    }

    public static Account CreateSyncAccount(Context context) {
        //Create the account type and default account
        Account newAccount = new Account(ACCOUNT,ACCOUNT_TYPE);

        //Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager)context.getSystemService(ACCOUNT_SERVICE);

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */

        if(accountManager.addAccountExplicitly(newAccount,null,null)){
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        }
        else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }

        return newAccount;
    }

    private void initLay() {
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId) {
                    case R.id.tab_search:
                        displayFragment(fragment1,fragment2,fragment3);
                        break;

                    case R.id.tab_camera:
                        displayFragment(fragment2,fragment1,fragment3);
                        break;

                    case R.id.tab_gallery:
                        displayFragment(fragment3,fragment2,fragment1);
                        break;
                }
            }
        });
    }

    private void displayFragment(Fragment fragmentShow, Fragment fragmentHide,Fragment fragmentHide2) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if(fragmentShow.isAdded()){
            transaction.show(fragmentShow);
        } else {
            transaction.add(R.id.contentContainer,fragmentShow);
        }

        if (fragmentHide.isAdded()){
            transaction.hide(fragmentHide);
        }

        if (fragmentHide2.isAdded()){
            transaction.hide(fragmentHide2);
        }
        transaction.commit();
    }
}
