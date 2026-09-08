package com.example.dienmayapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.R;
import com.example.dienmayapp.adapter.LichSuDonHangAdapter;
import com.example.dienmayapp.database.DatabaseManager;
import com.example.dienmayapp.model.DonHangItem;

import java.util.ArrayList;

public class LichSuDonHang extends AppCompatActivity {

    ImageView btnBack;
    ListView lvLichSuDonHang;

    DatabaseManager databaseManager;
    ArrayList<DonHangItem> orderList;
    LichSuDonHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lichsu_donhang);

        btnBack = findViewById(R.id.btnBack);
        lvLichSuDonHang = findViewById(R.id.lvLichSuDonHang);

        databaseManager = new DatabaseManager(this);

        btnBack.setOnClickListener(v -> finish());

        loadOrders();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOrders();
    }

    public void loadOrders() {
        SharedPreferences prefs = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String customerName = prefs.getString("fullname", "");
        String username = prefs.getString("username", "");

        if (customerName == null || customerName.isEmpty()) {
            customerName = username;
        }

        orderList = databaseManager.getOrdersByCustomer(customerName);
        adapter = new LichSuDonHangAdapter(this, orderList);
        lvLichSuDonHang.setAdapter(adapter);
    }
}