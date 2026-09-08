package com.example.dienmayapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.dienmayapp.activity.ChiTietSanPhamActivity;
import com.example.dienmayapp.model.Sanpham;

import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Sanpham> list;

    public SanphamAdapter(Context context, ArrayList<Sanpham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_sanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sanpham sp = list.get(position);

        String imageName = sp.getAnh();

        int resId = context.getResources()
                .getIdentifier(
                        imageName,
                        "drawable",
                        context.getPackageName()
                );

        if (resId != 0) {
            holder.imgAnh.setImageResource(resId);
        } else {
            holder.imgAnh.setImageResource(R.drawable.dieuhoa1);
        }
        holder.txtTen.setText(sp.getTen());
        holder.txtGia.setText(sp.getGia());
        holder.txtGiaCu.setText(sp.getGiaCu());
        holder.txtQua.setText(sp.getQua());
        holder.txtDanhGia.setText(sp.getDanhGia());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
            intent.putExtra("ten", sp.getTen());
            intent.putExtra("gia", sp.getGia());
            intent.putExtra("giaCu", sp.getGiaCu());
            intent.putExtra("qua", sp.getQua());
            intent.putExtra("danhGia", sp.getDanhGia());
            intent.putExtra("moTa", sp.getMoTa());
            intent.putExtra("anh", sp.getAnh());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAnh;
        TextView txtTen, txtGia, txtGiaCu, txtQua, txtDanhGia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAnh = itemView.findViewById(R.id.imgSanPham);
            txtTen = itemView.findViewById(R.id.txtName);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtGiaCu = itemView.findViewById(R.id.txtGiaCu);
            txtQua = itemView.findViewById(R.id.txtQua);
            txtDanhGia = itemView.findViewById(R.id.txtDanhGia);
        }
    }
}