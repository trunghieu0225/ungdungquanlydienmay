package com.example.dienmayapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.R;
import com.example.dienmayapp.activity.LichSuDonHang;
import com.example.dienmayapp.activity.XemDonHangActivity;
import com.example.dienmayapp.database.DatabaseManager;
import com.example.dienmayapp.model.DonHangItem;

import java.util.ArrayList;

public class LichSuDonHangAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<DonHangItem> list;
    private final DatabaseManager databaseManager;

    public LichSuDonHangAdapter(Context context, ArrayList<DonHangItem> list) {
        this.context = context;
        this.list = list;
        this.databaseManager = new DatabaseManager(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getOrderId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_lichsu_donhang, parent, false);
        } else {
            view = convertView;
        }

        TextView txtTenSanPham = view.findViewById(R.id.txtTenSanPham);
        TextView txtTongTien = view.findViewById(R.id.txtTongTien);
        TextView txtTrangThai = view.findViewById(R.id.txtTrangThai);
        Button btnHuyDon = view.findViewById(R.id.btnHuyDon);

        DonHangItem item = list.get(position);

        txtTenSanPham.setText(item.getProductName());
        txtTongTien.setText("Tổng tiền: " + item.getTotal());
        txtTrangThai.setText(item.getStatus());

        if ("Chờ xác nhận".equals(item.getStatus())) {
            btnHuyDon.setVisibility(View.VISIBLE);
        } else {
            btnHuyDon.setVisibility(View.GONE);
        }

        btnHuyDon.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Huỷ đơn hàng")
                    .setMessage("Bạn có muốn huỷ đơn hàng này không?")
                    .setPositiveButton("Huỷ đơn", (dialog, which) -> {
                        boolean result = databaseManager.cancelOrder(item.getOrderId());
                        if (result) {
                            Toast.makeText(context, "Đã huỷ đơn hàng", Toast.LENGTH_SHORT).show();
                            if (context instanceof LichSuDonHang) {
                                ((LichSuDonHang) context).loadOrders();
                            }
                        } else {
                            Toast.makeText(context, "Không thể huỷ đơn", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });

        view.setOnClickListener(v -> {
            Intent intent = new Intent(context, XemDonHangActivity.class);
            intent.putExtra("orderId", item.getOrderId());
            context.startActivity(intent);
        });

        return view;
    }
}