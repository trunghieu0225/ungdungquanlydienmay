package com.example.dienmayapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.R;

public class TaiKhoanUserActivity extends AppCompatActivity {

    ImageView btnBack;
    TextView txtTenNguoiDung, txtUsername;
    Button btnLichSuDonHang, btnDangXuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taikhoan_user);

        btnBack = findViewById(R.id.btnBack);
        txtTenNguoiDung = findViewById(R.id.txtTenNguoiDung);
        txtUsername = findViewById(R.id.txtUsername);
        btnLichSuDonHang = findViewById(R.id.btnLichSuDonHang);
        btnDangXuat = findViewById(R.id.btnDangXuat);

        SharedPreferences prefs = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String fullname = prefs.getString("fullname", "");
        String username = prefs.getString("username", "");

        if (!fullname.isEmpty()) {
            txtTenNguoiDung.setText(fullname);
        } else {
            txtTenNguoiDung.setText("Người dùng");
        }

        txtUsername.setText("Tài khoản: " + username);

        btnBack.setOnClickListener(v -> finish());

        btnLichSuDonHang.setOnClickListener(v -> {
            Intent intent = new Intent(TaiKhoanUserActivity.this, LichSuDonHang.class);
            startActivity(intent);
        });

        btnDangXuat.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(TaiKhoanUserActivity.this, Trangchu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}