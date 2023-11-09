package com.thuydev.pro1121appbangiay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thuydev.pro1121appbangiay.model.User;

import java.util.Map;

public class DangKy_Activity extends AppCompatActivity {
private EditText email,matKhau,reMatKhau;
private Button dangKy;
private ProgressDialog progressDialog;
private FirebaseFirestore db ;
    private Intent intent;
    private DocumentReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
       anhXa();
        dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKy();
            }
        });
    }

    private void anhXa() {
        email = findViewById(R.id.edt_email_dangnky);
        matKhau = findViewById(R.id.edt_matkhau_dangky);
        reMatKhau = findViewById(R.id.edt_rematkhau_dangky);
        dangKy = findViewById(R.id.btn_dangky_on);
        progressDialog = new ProgressDialog(this);
    }

    public void dangKy(){
        String email1 = email.getText().toString().trim();
        String pass1 = matKhau.getText().toString().trim();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Sẽ mất một lúc vui lòng chờ");
        progressDialog.show();
        if (email1.isEmpty()||pass1.isEmpty()){
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            progressDialog.cancel();
            return;
        }
        if (!pass1.equals(reMatKhau.getText().toString().trim())){
            Toast.makeText(this, "Mật khẩu nhập lại phải giống nhau", Toast.LENGTH_SHORT).show();
            progressDialog.cancel();
            return;
        }
        if (!(pass1.length()>=6)){
            Toast.makeText(this, "Mật khẩu phải dài hơn 6 ký tụ", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email1, pass1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(DangKy_Activity.this,ManHinhKhachHang.class);
                            startActivity(intent);
                            finishAffinity();
                            taoUser();
                        } else {
                            Toast.makeText(DangKy_Activity.this, "Đăng ký tài khoản thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void vaomanhinh() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            intent = new Intent(this, DangNhap_Activity.class);
            startActivity(intent);
        } else {
            db = FirebaseFirestore.getInstance();
            final Long[] chucvu = {0l};
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
                            if (chucvu[0] == 1) {
                                intent = new Intent(DangKy_Activity.this, ManHinhAdmin.class);
                                startActivity(intent);
                                finish();
                            } else if (chucvu[0] == 2) {
                                intent = new Intent(DangKy_Activity.this, ManHinhNhanVien.class);
                                startActivity(intent);
                                finish();
                            } else if (chucvu[0] == 3) {
                                intent = new Intent(DangKy_Activity.this, ManHinhKhachHang.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(DangKy_Activity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DangKy_Activity.this, "Người dùng không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DangKy_Activity.this, "Lỗi truy vấn", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
    private void taoUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db=FirebaseFirestore.getInstance();
        db.collection("user").document(user.getUid()).set(new User(user.getUid(),user.getEmail(),0,0L,3)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DangKy_Activity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DangKy_Activity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }}
