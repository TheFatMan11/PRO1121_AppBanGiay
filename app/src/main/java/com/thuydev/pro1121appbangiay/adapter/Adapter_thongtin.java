package com.thuydev.pro1121appbangiay.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.thuydev.pro1121appbangiay.fragment.Fragment_khoanchi;
import com.thuydev.pro1121appbangiay.fragment.Fragment_thongtin;

public class Adapter_thongtin extends FragmentStateAdapter {
    int i = 2;

    public Adapter_thongtin(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new Fragment_thongtin();
        } else {
            return new Fragment_khoanchi();
        }
    }

    @Override
    public int getItemCount() {
        return i;
    }
}
