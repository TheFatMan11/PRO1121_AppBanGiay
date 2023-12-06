package com.thuydev.pro1121appbangiay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.thuydev.pro1121appbangiay.model.User;

import java.util.Map;

public class ManHinhChao_Activity extends AppCompatActivity {
    private SharedPreferences preferences;
    private FirebaseFirestore db;
    private DocumentReference reference;
    private ListenerRegistration registration;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        Log.e("TAG", "onCreate: " + "Đang mở activity");
        preferences = getSharedPreferences("begin", MODE_PRIVATE);
        int i = preferences.getInt("only", 0);
        db = FirebaseFirestore.getInstance();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i == 0) {
                    Intent intent = new Intent(ManHinhChao_Activity.this, ManHinhKhoiDau_Activity.class);
                    startActivity(intent);
                    thaygiatri();
                    finish();
                } else {
                    vaomanhinh();
                }

            }
        }, 3000);
    }

    private void thaygiatri() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("only", 1);
        editor.apply();
    }


    private void vaomanhinh() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            intent = new Intent(this, DangNhap_Activity.class);
            startActivity(intent);
        } else {
            checkBan(user);

        }

    }

    private void checkBan(FirebaseUser user) {
        db.collection("user").whereEqualTo("maUser",user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isComplete()){
                    Toast.makeText(ManHinhChao_Activity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (task.isSuccessful()){
                    if (task.getResult().toObjects(User.class).get(0).getTrangThai()==1){
                        DangNhap(task.getResult().toObjects(User.class).get(0));
                    }else {
                        Toast.makeText(ManHinhChao_Activity.this, "Tài khoản bạn đã bị đình chỉ vui lòng liên", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }

                }
            }
        });
    }

    private void DangNhap(User user) {
        if (user.getChucVu() == 1) {
            intent = new Intent(ManHinhChao_Activity.this, ManHinhAdmin.class);
        } else if (user.getChucVu() == 2) {
            intent = new Intent(ManHinhChao_Activity.this, ManHinhNhanVien.class);
        } else if (user.getChucVu() == 3) {
            intent = new Intent(ManHinhChao_Activity.this, ManHinhKhachHang.class);
        } else {
            Toast.makeText(ManHinhChao_Activity.this, "Lỗi", Toast.LENGTH_SHORT).show();
        }
        finishAffinity();
        if (!isFinishing()) {
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}