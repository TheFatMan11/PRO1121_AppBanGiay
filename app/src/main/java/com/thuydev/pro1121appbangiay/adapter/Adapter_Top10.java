package com.thuydev.pro1121appbangiay.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.model.GioHang;

import java.util.List;

public class Adapter_Top10 extends RecyclerView.Adapter<Adapter_Top10.viewHolder> {
    private final List<GioHang> list_gHang;
    private final Context context;

    public Adapter_Top10(List<GioHang> listGHang, Context context) {
        list_gHang = listGHang;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top10sp,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView tv_tenSp, tv_thuonghieu, tv_soLuong;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            anh = itemView.findViewById(R.id.imgv_anhtop10);
            tv_tenSp = itemView.findViewById(R.id.tv_TENSP);
            tv_thuonghieu = itemView.findViewById(R.id.tv_THUONGHIEU);
            tv_soLuong = itemView.findViewById(R.id.tv_SOLUONG);
        }
    }
}
