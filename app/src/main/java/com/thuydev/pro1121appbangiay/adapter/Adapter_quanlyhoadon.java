package com.thuydev.pro1121appbangiay.adapter;

import android.app.Activity;
import android.content.Context;
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


    public Adapter_quanlyhoadon(List<GioHang> list_gio,List<SanPham> list_sanPham,List<User> list_Users,  List<DonHang> list_doHang,  Context context) {
        this.list_gio = list_gio;
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

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        SanPham sanPham = getTenSP(list_sanPham.get(position).getMaSp());
        if (sanPham == null) {
            return;
        }
        holder.tv_tenSP.setText("Tên sản phẩm: " + sanPham.getTenSP());
        holder.tv_gia.setText("Giá :" + sanPham.getGia());
        GioHang hang = getgioHang(list_gio.get(position).getMaKhachHang());
        if (hang == null) {
            return;
        }
        holder.tv_soluong.setText("Số lượng: " + hang.getSoLuong());
        User user = getKhachHang(list_Users.get(position).getMaUser());
        holder.tv_tenKH.setText("Họ tên:" + user.getHoTen());
        holder.tv_sdt.setText("Sđt: " + user.getSDT());

    }

    private SanPham getTenSP(String maSP) {
        for (SanPham u : list_sanPham) {
            if (maSP.equals(u.getTenSP())) {
                return u;
            }
        }
        return null;
    }

    private GioHang getgioHang(String maGH) {
        for (GioHang hang : list_gio) {
            if (maGH.equals(hang.getMaGio())) {
                return hang;
            }
        }
        return null;
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
            tv_soluong = itemView.findViewById(R.id.tv_Soluong);
            tv_tenSP = itemView.findViewById(R.id.tv_tensp);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            anh = itemView.findViewById(R.id.imgv_anhsp);

            btn_Huy = itemView.findViewById(R.id.ibtn_Huy);
            btn_xacNhan = itemView.findViewById(R.id.ibtn_XacNhan);
        }

    }
}
