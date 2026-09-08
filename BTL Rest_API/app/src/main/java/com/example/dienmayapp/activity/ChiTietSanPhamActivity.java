package com.example.dienmayapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.R;
import com.example.dienmayapp.model.GioHang;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    ImageView imgChiTiet, btnBackChiTiet;
    TextView txtTenChiTiet, txtDanhGiaChiTiet, txtGiaChiTiet, txtGiaCuChiTiet, txtQuaChiTiet, txtMoTaChiTiet;
    Button btnThemVaoGio, btnMuaNgay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_sanpham);

        btnBackChiTiet = findViewById(R.id.btnBackChiTiet);
        imgChiTiet = findViewById(R.id.imgChiTiet);
        txtTenChiTiet = findViewById(R.id.txtTenChiTiet);
        txtDanhGiaChiTiet = findViewById(R.id.txtDanhGiaChiTiet);
        txtGiaChiTiet = findViewById(R.id.txtGiaChiTiet);
        txtGiaCuChiTiet = findViewById(R.id.txtGiaCuChiTiet);
        txtQuaChiTiet = findViewById(R.id.txtQuaChiTiet);
        txtMoTaChiTiet = findViewById(R.id.txtMoTaChiTiet);
        btnThemVaoGio = findViewById(R.id.btnThemVaoGio);
        btnMuaNgay = findViewById(R.id.btnMuaNgay);

        btnBackChiTiet.setOnClickListener(v -> finish());

        String ten = getIntent().getStringExtra("ten");
        String gia = getIntent().getStringExtra("gia");
        String giaCu = getIntent().getStringExtra("giaCu");
        String qua = getIntent().getStringExtra("qua");
        String danhGia = getIntent().getStringExtra("danhGia");
        String moTa = getIntent().getStringExtra("moTa");
        String anh = getIntent().getStringExtra("anh");
        Toast.makeText(this, "Ảnh = " + anh, Toast.LENGTH_LONG).show();


        txtTenChiTiet.setText(ten);
        txtGiaChiTiet.setText(gia);
        txtGiaCuChiTiet.setText(giaCu);
        txtQuaChiTiet.setText(qua);
        txtDanhGiaChiTiet.setText(danhGia);
        txtMoTaChiTiet.setText(moTa);
        int imageId = getResources().getIdentifier(
                anh,
                "drawable",
                getPackageName()
        );

        imgChiTiet.setImageResource(imageId);

        btnThemVaoGio.setOnClickListener(v -> {
            if (!daDangNhap()) {
                chuyenSangDangNhap();
                return;
            }

            GioHang item = new GioHang(
                    ten,
                    parsePrice(gia),
                    parsePrice(giaCu),
                    1,
                    anh
            );

            QuanLyGioHang.getInstance().addToCart(item);
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        btnMuaNgay.setOnClickListener(v -> {

            SharedPreferences prefs = getSharedPreferences("USER_FILE", MODE_PRIVATE);

            boolean isLogin = prefs.getBoolean("isLogin", false);

            if (!isLogin) {
                Toast.makeText(this, "Chưa đăng nhập", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(
                        ChiTietSanPhamActivity.this,
                        DangNhapActivity.class));

                return;
            }

            Toast.makeText(this, "Đã đăng nhập", Toast.LENGTH_SHORT).show();

            GioHang item = new GioHang(
                    ten,
                    parsePrice(gia),
                    parsePrice(giaCu),
                    1,
                    anh
            );

            QuanLyGioHang.getInstance().addToCart(item);

            startActivity(new Intent(
                    ChiTietSanPhamActivity.this,
                    GioHangActivity.class));
        });
    }

    private boolean daDangNhap() {

        SharedPreferences prefs =
                getSharedPreferences("USER_FILE", MODE_PRIVATE);

        boolean isLogin = prefs.getBoolean("isLogin", false);

        Toast.makeText(this,
                "isLogin = " + isLogin,
                Toast.LENGTH_LONG).show();

        return isLogin;
    }

    private void chuyenSangDangNhap() {
        Toast.makeText(this, "Bạn cần đăng nhập trước", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ChiTietSanPhamActivity.this, DangNhapActivity.class);
        startActivity(intent);
    }

    private double parsePrice(String priceText) {
        if (priceText == null) return 0;

        String cleaned = priceText
                .replace(".", "")
                .replace("đ", "")
                .replace(",", "")
                .replace("-", "")
                .replace("%", "")
                .trim();

        try {
            return Double.parseDouble(cleaned);
        } catch (Exception e) {
            return 0;
        }
    }
}