package com.example.shoppingapp.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shoppingapp.dataFirebase.Users;

public class MyViewPage2Adapter extends FragmentStateAdapter {
    private Users user;
    public MyViewPage2Adapter(@NonNull FragmentActivity fragmentActivity, Users users) {
        super(fragmentActivity);
        this.user = users;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ProductFragment();

            case 1:
                return new NotificationFragment();

            case 2:
                return new ChatHistoryFragment();

            case 3:
                return ProfileFragment.newInstance(user);

            default:
                return new ProductFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
