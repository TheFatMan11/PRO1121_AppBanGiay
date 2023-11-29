package com.thuydev.pro1121appbangiay.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.model.Don;
import com.thuydev.pro1121appbangiay.model.DonHang;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Adapter_naptien extends RecyclerView.Adapter<Adapter_naptien.ViewHolder> {
    List<HashMap<String,Object>> list_donHang;
    Context context;
    FirebaseFirestore db;
    int manhinh = 0;




    public Adapter_naptien(List<HashMap<String, Object>> list, Context context) {
        this.list_donHang = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(((Activity) context).getLayoutInflater().
                inflate(R.layout.item_lichsugg, parent, false));
    }

    @SuppressLint({"RecyclerView", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ma.setText(list_donHang.get(position).get("maGG").toString());
        holder.sotien.setText(list_donHang.get(position).get("sotien").toString());
        holder.ngay.setText(list_donHang.get(position).get("time").toString());
        Log.e("TAG", "onBindViewHolder: "+list_donHang );
    }






    @Override
    public int getItemCount() {
        return list_donHang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ma, sotien, ngay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ma = itemView.findViewById(R.id.tv_maGG);
            sotien = itemView.findViewById(R.id.tv_sotien_gg);
            ngay = itemView.findViewById(R.id.tv_thoigiangg);
        }
    }
}
