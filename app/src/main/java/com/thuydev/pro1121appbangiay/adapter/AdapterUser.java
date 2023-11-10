package com.thuydev.pro1121appbangiay.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.model.User;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.viewHolder> {
    private final Context context;
    private final List<User> list;

    public AdapterUser(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_quan_ly_khach_hang, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.tvTen.setText("Tên: " + list.get(position).getHoTen());
        holder.tvEmail.setText("Email: " + list.get(position).getEmail());
        long trangThai = 1;
        if (trangThai == 0) {
            holder.tvTrangThai.setText("Không hoạt động");
        } else if (trangThai == 1) {
            holder.tvTrangThai.setText("Đang hoạt động");
            holder.tvTrangThai.setTextColor(ContextCompat.getColor(context, R.color.xanhla));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvTen, tvEmail, tvTrangThai;
        ImageButton ibtn_xoa;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tv_Ten);
            tvEmail = itemView.findViewById(R.id.tv_Email);
            tvTrangThai = itemView.findViewById(R.id.tv_Trangthai);
            ibtn_xoa = itemView.findViewById(R.id.ibtn_xoa);
        }
    }
}
