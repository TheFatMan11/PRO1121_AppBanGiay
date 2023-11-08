package com.thuydev.pro1121appbangiay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ManHinhChao_Activity extends AppCompatActivity {
    private SharedPreferences preferences;
    private FirebaseFirestore db;
    private DocumentReference reference;
    private  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        preferences = getSharedPreferences("begin",MODE_PRIVATE);
        int i =  preferences.getInt("only",0);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i==0){
                    Intent intent = new Intent(ManHinhChao_Activity.this,ManHinhKhoiDau_Activity.class);
                    startActivity(intent);
                    thaygiatri();
                    finish();
                }else {
                vaomanhinh();
                }

            }
        },3000);
    }

    private void thaygiatri() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("only",1);
        editor.apply();
    }


    private void vaomanhinh() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            intent = new Intent(this, DangNhap_Activity.class);
            startActivity(intent);
        } else {
            db = FirebaseFirestore.getInstance();
            final Long[] chucvu = new Long[]{0L};
            reference = db.collection("user").document(user.getUid());
            reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isComplete()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Tài liệu người dùng tồn tại
                            Map<String, Object> userData = document.getData();
                            chucvu[0] = (Long) userData.get("chucVu");
                            if (chucvu[0]==null){
                                return;
                            }
                            if (chucvu[0] == 1) {
                                intent = new Intent(ManHinhChao_Activity.this, ManHinhAdmin.class);
                                startActivity(intent);
                            } else if (chucvu[0] == 2) {
                                intent = new Intent(ManHinhChao_Activity.this, ManHinhNhanVien.class);
                                startActivity(intent);
                            } else if (chucvu[0] == 3) {
                                intent = new Intent(ManHinhChao_Activity.this, ManHinhKhachHang.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ManHinhChao_Activity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        } else {
                            Toast.makeText(ManHinhChao_Activity.this, "Người dùng không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ManHinhChao_Activity.this, "Lỗi truy vấn", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
}