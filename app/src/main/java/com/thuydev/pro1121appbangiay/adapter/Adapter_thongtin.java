package com.thuydev.pro1121appbangiay.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.thuydev.pro1121appbangiay.fragment.Fragment_khoanchi;
import com.thuydev.pro1121appbangiay.fragment.Fragment_thongtin;
import com.thuydev.pro1121appbangiay.model.User;

public class Adapter_thongtin extends FragmentStateAdapter {
    int i = 2;
    Fragment_thongtin fragmentThongtin;
    Fragment_khoanchi fragmentKhoanchi;

    public Adapter_thongtin(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragmentThongtin = new Fragment_thongtin();
        fragmentKhoanchi = new Fragment_khoanchi();
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {

            return fragmentThongtin;
        } else {
            return fragmentKhoanchi;
        }
    }

    @Override
    public int getItemCount() {
        return i;
    }

    public void data(User user){
        fragmentThongtin.setUs(user);
    }
}
