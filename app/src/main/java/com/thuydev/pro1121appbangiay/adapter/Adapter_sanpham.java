package com.thuydev.pro1121appbangiay.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.model.SanPham;

import java.util.List;

public class Adapter_sanpham extends RecyclerView.Adapter<Adapter_sanpham.ViewDolder>{
List<SanPham> list;
Context context;
    FirebaseFirestore db;
    public Adapter_sanpham(List<SanPham> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewDolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        return new ViewDolder(inflater.inflate(R.layout.item_quan_ly_giay,parent,false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewDolder holder, int position) {
        Glide.with(context).load(list.get(position).getAnh()).error(R.drawable.logo3).into(holder.anh);
        holder.ten.setText(list.get(position).getTenSP());
        holder.gia.setText("Giá: "+Integer.parseInt(list.get(position).getGia()+"")+" VND");
        holder.soluong.setText("Số lượng: "+Integer.parseInt(list.get(position).getSoLuong()+"")+"");
        db = FirebaseFirestore.getInstance();
        db.collection("hang").document(list.get(position).getMaHang())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()==false){
                            return;
                        }
                        holder.thuonghieu.setText("Thương hiệu: "+documentSnapshot.getString("tenHang"));
                    }
                });

        holder.namxuatban.setText("Năm sản xuất: "+list.get(position).getNamSX());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewDolder extends RecyclerView.ViewHolder {
ImageView anh;
TextView ten,gia,soluong,thuonghieu,namxuatban;
ImageButton update,delete;
        public ViewDolder(@NonNull View itemView) {
            super(itemView);
            anh = itemView.findViewById(R.id.imv_anhsp);
            ten = itemView.findViewById(R.id.tv_Tensp);
            gia = itemView.findViewById(R.id.tv_Gia);
            soluong = itemView.findViewById(R.id.tv_Soluong);
            thuonghieu = itemView.findViewById(R.id.tv_Thuonghieu);
            namxuatban = itemView.findViewById(R.id.tv_Namsx);
            update = itemView.findViewById(R.id.ibtn_update);
            delete = itemView.findViewById(R.id.ibtn_delete);
        }
    }
}
