package com.thuydev.pro1121appbangiay.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.thuydev.pro1121appbangiay.fragment.ThongKe_DoanhThu;
import com.thuydev.pro1121appbangiay.fragment.ThongKe_Top10SP;

public class viewPagerAdapter extends FragmentStateAdapter {
     public viewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }




    @NonNull
    @Override
    public Fragment createFragment(int position) {
         switch (position){
             case 0: return new ThongKe_DoanhThu();
             case 1: return new ThongKe_Top10SP();
             default: return new ThongKe_DoanhThu();
         }

    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
