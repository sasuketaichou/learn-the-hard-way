package com.example.amieruljapri.myapplication27;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.contentContainer) != null){
            if (savedInstanceState != null) {
                return;
            }
        }

        Fragment1 fragment1 = new Fragment1();
        fragment1.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentContainer, fragment1).commit();
        
        initLay();
    }

    private void initLay() {
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                Fragment fragment = null;

                switch (tabId) {
                    case R.id.tab_search:
                        fragment = new Fragment1();
                        break;

                    case R.id.tab_camera:
                        fragment = new Fragment2();
                        break;

                    case R.id.tab_gallery:
                        fragment = new Fragment1();
                        break;
                }


                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.contentContainer,fragment);
                transaction.commit();
            }
        });
    }
}
