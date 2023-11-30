package com.thuydev.pro1121appbangiay.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thuydev.pro1121appbangiay.R;
import com.thuydev.pro1121appbangiay.model.User;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Adapter_dsYeuCauNap extends RecyclerView.Adapter<Adapter_dsYeuCauNap.viewHolder> {
    private final Context context;
    private final List<HashMap<String, Object>> list;
    private final List<User> list_use;
    FirebaseFirestore db;


    public Adapter_dsYeuCauNap(Context context, List<HashMap<String, Object>> list, List<User> listUse) {
        this.context = context;
        this.list = list;
        this.list_use = listUse;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_danhsachyeucaunap, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.tv_Email.setText("Email: " + list.get(position).get("email").toString());
        holder.tv_soTien.setText("+" + NumberFormat.getNumberInstance(Locale.getDefault()).format(list.get(position).get("sotien")));
        holder.tv_maNG.setText("Mã người dùng: " + list.get(position).get("maND").toString());
        holder.tv_maGD.setText("Mã giao dịch: " + list.get(position).get("maGG").toString());
        holder.btn_xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xacNhan(position);
            }
        });
    }


    private void xacNhan(int p) {
        db = FirebaseFirestore.getInstance();
        Long soTien = Long.valueOf(list.get(p).get("sotien").toString());
        Long soDU = Long.valueOf(list_use.get(p).getSoDu().toString());
        Log.e("TAG","SODU"+soDU);
        Long tongTien = soDU+soTien;
        String mND = list.get(p).get("maND").toString();
        HashMap<String, Object> tien = new HashMap<>();
        tien.put("soDu", tongTien);
        db.collection("user").document(mND).update(tien).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(context, "Thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_Email, tv_soTien, tv_maGD, tv_maNG;
        AppCompatButton btn_xacNhan, btn_Huy;
        ImageView img_anhGD;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Email = itemView.findViewById(R.id.tv_Email_nap);
            tv_soTien = itemView.findViewById(R.id.tv_Sotien_nap);
            tv_maNG = itemView.findViewById(R.id.tv_MaND);
            tv_maGD = itemView.findViewById(R.id.tv_MaGiaoDich);
            img_anhGD = itemView.findViewById(R.id.imgv_anhNap);
            btn_xacNhan = itemView.findViewById(R.id.btn_xacNhan_nap);
            btn_Huy = itemView.findViewById(R.id.btn_Huy_yc);
        }
    }
}
