package com.example.dienmayapp.activity;



import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.R;
import com.example.dienmayapp.database.DatabaseManager;

public class XemDonHangActivity extends AppCompatActivity {

    ImageView btnBack;
    TextView txtKhachHang, txtSanPham, txtPhuongThucThanhToan, txtVoucher, txtGia, txtTongTien, txtTrangThai;

    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_donhang);

        btnBack = findViewById(R.id.btnBack);
        txtKhachHang = findViewById(R.id.txtKhachHang);
        txtSanPham = findViewById(R.id.txtSanPham);
        txtPhuongThucThanhToan = findViewById(R.id.txtPhuongThucThanhToan);
        txtVoucher = findViewById(R.id.txtVoucher);
        txtGia = findViewById(R.id.txtGia);
        txtTongTien = findViewById(R.id.txtTongTien);
        txtTrangThai = findViewById(R.id.txtTrangThai);

        databaseManager = new DatabaseManager(this);

        btnBack.setOnClickListener(v -> finish());

        int orderId = getIntent().getIntExtra("orderId", -1);
        if (orderId != -1) {
            loadOrderDetail(orderId);
        }
    }

    private void loadOrderDetail(int orderId) {
        Cursor cursor = databaseManager.getOrderDetailById(orderId);

        if (cursor != null && cursor.moveToFirst()) {
            String customerName = cursor.getString(cursor.getColumnIndexOrThrow("customer_name"));
            String productName = cursor.getString(cursor.getColumnIndexOrThrow("product_name"));
            String paymentMethod = cursor.getString(cursor.getColumnIndexOrThrow("payment_method"));
            String voucher = cursor.getString(cursor.getColumnIndexOrThrow("voucher"));
            String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));
            String total = cursor.getString(cursor.getColumnIndexOrThrow("total"));
            String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));

            txtKhachHang.setText(customerName);
            txtSanPham.setText(productName);
            txtPhuongThucThanhToan.setText(paymentMethod);
            txtVoucher.setText(voucher);
            txtGia.setText(price);
            txtTongTien.setText(total);
            txtTrangThai.setText(status);

            cursor.close();
        }
    }
}
