package com.example.dienmayapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.R;

public class TrangchuAdmin extends AppCompatActivity {

    Button btnGoProduct, btnGoUser, btnGoCategory, btnGoOrder, btnGoPromotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu_admin);

        btnGoProduct = findViewById(R.id.btnGoProduct);
        btnGoUser = findViewById(R.id.btnGoUser);
        btnGoCategory = findViewById(R.id.btnGoCategory);
        btnGoOrder = findViewById(R.id.btnGoOrder);
        btnGoPromotion = findViewById(R.id.btnGoPromotion);

        btnGoUser.setOnClickListener(v -> {
            Intent intent = new Intent(TrangchuAdmin.this, QuanLyUser.class);
            startActivity(intent);
        });

        btnGoProduct.setOnClickListener(v -> {
            Intent intent = new Intent(TrangchuAdmin.this, QuanLySanPham.class);
            startActivity(intent);
        });

        btnGoCategory.setOnClickListener(v -> {
            Intent intent = new Intent(TrangchuAdmin.this, QuanLyLoai.class);
            startActivity(intent);
        });

        btnGoOrder.setOnClickListener(v -> {
            Intent intent = new Intent(TrangchuAdmin.this, QuanLyDonHang.class);
            startActivity(intent);
        });

        btnGoPromotion.setOnClickListener(v -> {
            Intent intent = new Intent(TrangchuAdmin.this, QuanLyKhuyenMaiActivity.class);
            startActivity(intent);
        });
    }
}