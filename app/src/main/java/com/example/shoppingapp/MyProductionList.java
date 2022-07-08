package com.example.shoppingapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.shoppingapp.item_sNavigationDrawer.AddProductFragment;
import com.example.shoppingapp.item_sNavigationDrawer.UpdateProductFragment;
import com.example.shoppingapp.item_sNavigationDrawer.DeleteProductFragment;
import com.example.shoppingapp.item_sNavigationDrawer.ProductsFragment_navigation;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class MyProductionList extends AppCompatActivity {

    private SNavigationDrawer sNavigationDrawer;
    private Class fragmentClass;
    public static Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_production_list);
        initUi();
        initListener();
    }

    private void initUi() {
        sNavigationDrawer = findViewById(R.id.navigationDrawer);
        item_sNavigationDrawer();
    }

    private void initListener() {}

    private void item_sNavigationDrawer () {
        List<MenuItem> menuItems = new ArrayList<>();

        //Use the MenuItem given by this library and not the default one.
        //First parameter is the title of the menu item and then the second parameter is the image which will be the background of the menu item.

        menuItems.add(new MenuItem("Products",R.drawable.news_bg));
        menuItems.add(new MenuItem("Add Product",R.drawable.feed_bg));
        menuItems.add(new MenuItem("Update Product",R.drawable.message_bg));
        menuItems.add(new MenuItem("Delete Product",R.drawable.music_bg));

        //then add them to navigation drawer

        sNavigationDrawer.setMenuItemList(menuItems);
        fragmentClass =  ProductsFragment_navigation.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }



        //Listener to handle the menu item click. It returns the position of the menu item clicked. Based on that you can switch between the fragments.

        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Position "+position);

                switch (position){
                    case 0:{
                        fragmentClass = ProductsFragment_navigation.class;
                        break;
                    }
                    case 1:{
                        fragmentClass = AddProductFragment.class;
                        break;
                    }
                    case 2:{
                        fragmentClass = UpdateProductFragment.class;
                        break;
                    }
                    case 3:{
                        fragmentClass = DeleteProductFragment.class;
                        break;
                    }

                }

                //Listener for drawer events such as opening and closing.
                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening(){

                    }

                    @Override
                    public void onDrawerClosing(){
                        System.out.println("Drawer closed");

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        System.out.println("State "+newState);
                    }
                });
            }
        });
    }
}