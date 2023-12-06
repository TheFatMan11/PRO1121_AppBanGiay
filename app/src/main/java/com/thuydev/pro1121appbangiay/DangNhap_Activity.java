package com.thuydev.pro1121appbangiay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.thuydev.pro1121appbangiay.adapter.MyNotification;
import com.thuydev.pro1121appbangiay.model.User;

import java.util.Date;
import java.util.Map;

public class DangNhap_Activity extends AppCompatActivity {
    private Button dangky, dangNhap;
    private EditText email, matKhau;
    private TextView quenMK;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db;
    private DocumentReference reference;
    private Intent intent;
    private ListenerRegistration registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhxa();
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chuyen(DangKy_Activity.class);
            }
        });

        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangnhap();
            }
        });
        quenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quenMK();
            }
        });
    }

    private void quenMK() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_quenpass, null, false);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        EditText email = view.findViewById(R.id.edt_email_quen);
        Button gui = view.findViewById(R.id.btn_quen);

        gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quenPass(email, dialog);

            }
        });
    }

    private void quenPass(EditText email, Dialog dialog) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email.getText().toString();
        if (emailAddress.isEmpty()) {
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Sẽ mất một lúc vui lòng chờ");
        progressDialog.show();
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.cancel();
                            Toast.makeText(DangNhap_Activity.this, "Đã gửi link khôi phục hãy kiểm tra email", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
    }

    private void anhxa() {
        dangky = findViewById(R.id.btn_dangky);
        dangNhap = findViewById(R.id.btn_dangnhap);
        email = findViewById(R.id.edt_email_dangnhap);
        matKhau = findViewById(R.id.edt_matkhau_dangnhap);
        quenMK = findViewById(R.id.tv_quenpass);
        progressDialog = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
    }


    public void dangnhap() {
        String mEmail = email.getText().toString().trim();
        String mPass = matKhau.getText().toString().trim();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (email.getText().toString().isEmpty() || matKhau.getText().toString().isEmpty()) {
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Sẽ mất một lúc vui lòng chờ");
        progressDialog.show();



        mAuth.signInWithEmailAndPassword(mEmail, mPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            checkBan(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(DangNhap_Activity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }

                    }
                });
    }

    private void checkBan(FirebaseUser user) {
        db.collection("user").whereEqualTo("maUser",user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isComplete()){
                    Toast.makeText(DangNhap_Activity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (task.isSuccessful()){
                    if (task.getResult().toObjects(User.class).get(0).getTrangThai()==1){
                        DangNhap(task.getResult().toObjects(User.class).get(0));
                    }else {
                        Toast.makeText(DangNhap_Activity.this, "Tài khoản bạn đã bị đình chỉ vui lòng liên", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }

                }
            }
        });
    }

    private void chuyen(Class a) {
        Intent intent = new Intent(this, a);
        startActivity(intent);
    }



    private void DangNhap(User user) {
        if (user.getChucVu() == 1) {
            intent = new Intent(DangNhap_Activity.this, ManHinhAdmin.class);
        } else if (user.getChucVu() == 2) {
            intent = new Intent(DangNhap_Activity.this, ManHinhNhanVien.class);
        } else if (user.getChucVu() == 3) {
            intent = new Intent(DangNhap_Activity.this, ManHinhKhachHang.class);
        } else {
            Toast.makeText(DangNhap_Activity.this, "Lỗi", Toast.LENGTH_SHORT).show();
        }
        finishAffinity();
        if (!isFinishing()) {
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}