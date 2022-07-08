package com.example.shoppingapp.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewPage2Adapter extends FragmentStateAdapter {
    public MyViewPage2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
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
                return new ProfileFragment();

            default:
                return new ProductFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
