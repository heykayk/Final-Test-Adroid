package com.example.finaltest.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.finaltest.fragment.FragmentHome;
import com.example.finaltest.fragment.FragmentInfo;
import com.example.finaltest.fragment.FragmentSearch;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentSearch();
            case 2:
                return new FragmentInfo();
            default:
                return new FragmentSearch();

        }
    }


    @Override
    public int getCount() {
        return 3;
    }
}
