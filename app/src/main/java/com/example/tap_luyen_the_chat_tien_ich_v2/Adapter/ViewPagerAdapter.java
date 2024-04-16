package com.example.tap_luyen_the_chat_tien_ich_v2.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment.ProfileFragment;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment.HistoryFragment;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment.PlansListFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PlansListFragment();
            case 1:
                return new HistoryFragment();
            case 2:
                return new ProfileFragment();
            default:
                return new PlansListFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
