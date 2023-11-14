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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.thuydev.pro1121appbangiay.fragment.Frg_quanLyHoaDon;
import com.thuydev.pro1121appbangiay.model.DonHang;
import com.thuydev.pro1121appbangiay.model.User;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.model.GioHang;
import com.thuydev.pro1121appbangiay.model.SanPham;

import java.util.List;

public class Adapter_quanlyhoadon extends RecyclerView.Adapter<Adapter_quanlyhoadon.viewHolder> {
    Context context;
    List<GioHang> list_gio;
    List<SanPham> list_sanPham;
    List<User> list_Users;
    Frg_quanLyHoaDon hoaDon;
    List<DonHang> list_doHang;

    FirebaseFirestore db;

//    public Adapter_quanlyhoadon(Context context, List<DonHang> listDoHang) {
//        this.context = context;
//        list_doHang = listDoHang;
//    }


    public Adapter_quanlyhoadon(List<SanPham> list_sanPham, List<User> list_Users, List<DonHang> list_doHang, Context context) {

        this.list_sanPham = list_sanPham;
        this.list_Users = list_Users;
        this.list_doHang = list_doHang;

        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_ql_don_hang, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if (list_Users.size() == 0 && list_doHang.size() == 0 && list_sanPham.size() == 0) {
            return;
        }
        String[] data = getdata(position);
        Log.e("TAG", "onBindViewHolder: " +getdata(position));
        if (data.length<=0){
            return;
        }
        holder.tv_tenKH.setText("Họ tên:" + data[0]);
        holder.tv_diaChi.setText("Địa chỉ: "+data[1]);
        holder.tv_sdt.setText("Sđt: " + data[2]);
        holder.tv_gia.setText("Giá :" +data[3]);
        holder.tv_soluong.setText("Số lượng sản phẩm mua: "+data[4]);
    }

    private String[] getdata(int position) {
        String[] a = new String[]{"","","","",""};
        for (User u : list_Users) {
            if (list_doHang.get(position).getMaKhachHang()
                    .equals(u.getMaUser())) {
                a[0] = u.getHoTen();
                a[1] = u.getChonDiaCHi();
                a[2] = u.getSDT();
            }
            Log.e("TAG", "getdata: 1"+u.getMaUser() );
            Log.e("TAG", "getdata: 2"+list_doHang.get(position).getMaKhachHang() );
        }
        Long tong = 0l;
        for (SanPham s : list_sanPham) {
            for (int i = 0; i < list_doHang.get(position).getListSP().size(); i++) {
                if (s.getMaSp().equals(list_doHang.get(position).getListSP().get(i))) {
                    tong += s.getGia();
                }
            }
        }
        a[3] = tong + "";
        a[4] = String.valueOf((list_doHang.get(position).getListSP().size() + 1));
        return a;
    }


    private User getKhachHang(String maKH) {
        for (User user : list_Users) {
            if (maKH.equals(user.getMaUser())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return list_doHang.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenKH, tv_tenSP, tv_gia, tv_diaChi, tv_sdt, tv_soluong;
        ImageButton btn_Huy, btn_xacNhan;
        ImageView anh;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenKH = itemView.findViewById(R.id.tv_tenKhach);
            tv_diaChi = itemView.findViewById(R.id.tv_diaChi);
            tv_sdt = itemView.findViewById(R.id.tv_sdt);
            tv_soluong = itemView.findViewById(R.id.tv_soLuong_);
            tv_tenSP = itemView.findViewById(R.id.tv_tensp);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            anh = itemView.findViewById(R.id.imgv_anhsp);

            btn_Huy = itemView.findViewById(R.id.ibtn_Huy);
            btn_xacNhan = itemView.findViewById(R.id.ibtn_XacNhan);
        }

    }
}
