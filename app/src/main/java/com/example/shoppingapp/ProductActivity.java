package com.example.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shoppingapp.dataFirebase.Users;
import com.example.shoppingapp.fragment.MyViewPage2Adapter;
import com.example.shoppingapp.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProductActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ViewPager2 mViewPager2;
    private MyViewPage2Adapter mMyViewPage2Adapter;

    private static int FRAGMENT_PRODUCT = 1;
    private static int FRAGMENT_NOTIFICATION = 2;
    private static int FRAGMENT_CHAT = 3;
    private static int FRAGMENT_PROFILE = 4;
    private int itemId;
    private int itemId_call;
    private Bundle bundleUser;
    private Users users;

    private static final String TAG = "ProductActivity";


    private static int CurrentFragment = FRAGMENT_PRODUCT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initUi();
        initListener();
    }

    private void initUi() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.getMenu().findItem(R.id.navigation_title).setChecked(true);

        mViewPager2 = findViewById(R.id.view_pager2);
        mViewPager2.setUserInputEnabled(false);
        mMyViewPage2Adapter = new MyViewPage2Adapter(this, getUser());
        mViewPager2.setAdapter(mMyViewPage2Adapter);
    }

    private void initListener() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                itemId = item.getItemId();
                return menuItem(true);
            }
        });

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                itemId_call = position;

                itemCall();
            }
        });
    }

    private boolean menuItem(boolean menu_item) {

        switch (itemId){
            case R.id.navigation_title:
                mViewPager2.setCurrentItem(0);
                CurrentFragment = FRAGMENT_PRODUCT;
                break;

            case R.id.navigation_notification:
                mViewPager2.setCurrentItem(1);
                CurrentFragment = FRAGMENT_NOTIFICATION;
                break;

            case R.id.navigation_chat_history:
                mViewPager2.setCurrentItem(2);
                CurrentFragment = FRAGMENT_CHAT;
                break;

            case R.id.navigation_profile:
                mViewPager2.setCurrentItem(3);
                CurrentFragment = FRAGMENT_PROFILE;
                break;
        }

        return menu_item;
    }

    private void itemCall() {

//        switch (itemId_call){
//            case 0:
//                CurrentFragment = FRAGMENT_PRODUCT;
//                bottomNavigationView.getMenu().findItem(R.id.navigation_title).setChecked(true);
//                break;
//
//            case 1:
//                CurrentFragment = FRAGMENT_NOTIFICATION;
//                bottomNavigationView.getMenu().findItem(R.id.navigation_notification).setChecked(true);
//                break;
//
//            case 2:
//                CurrentFragment = FRAGMENT_CHAT;
//                bottomNavigationView.getMenu().findItem(R.id.navigation_chat_history).setChecked(true);
//                break;
//
//            case 3:
//                CurrentFragment = FRAGMENT_PROFILE;
//                bottomNavigationView.getMenu().findItem(R.id.navigation_profile).setChecked(true);
//                break;
//        }
    }

    private Users getUser(){
        bundleUser = getIntent().getExtras();
        users = (Users) bundleUser.get("userLogin");
        if(users == null){
            users = (Users) bundleUser.get("userSplash");
        }
        return users;
    }
}