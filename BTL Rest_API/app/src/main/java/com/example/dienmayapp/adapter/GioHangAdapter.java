package com.example.dienmayapp.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.dienmayapp.model.GioHang;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHolder> {

    public interface OnCartActionListener {
        void onIncrease(int position, GioHang item);
        void onDecrease(int position, GioHang item);
        void onDelete(int position, GioHang item);
    }

    private final Context context;
    private final List<GioHang> GioHang;
    private final OnCartActionListener listener;

    public GioHangAdapter(Context context, List<GioHang> GioHang, OnCartActionListener listener) {
        this.context = context;
        this.GioHang = GioHang;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_giohang, parent, false);
        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangViewHolder holder, int position) {
        GioHang item = GioHang.get(position);

        String imageName = item.getImageResId();

        if (imageName != null) {

            imageName = imageName
                    .replace(".png", "")
                    .replace(".jpg", "")
                    .replace(".jpeg", "");

            int imageId = context.getResources().getIdentifier(
                    imageName,
                    "drawable",
                    context.getPackageName()
            );

            if (imageId != 0) {
                holder.imgProduct.setImageResource(imageId);
            }

        }
        holder.tvProductName.setText(item.getName());
        holder.tvPrice.setText(formatCurrency(item.getPrice()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        if (item.getOldPrice() > 0) {
            holder.tvOriginalPrice.setVisibility(View.VISIBLE);
            holder.tvOriginalPrice.setText(formatCurrency(item.getOldPrice()));
            holder.tvOriginalPrice.setPaintFlags(
                    holder.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
            );
        } else {
            holder.tvOriginalPrice.setVisibility(View.GONE);
        }

        holder.btnIncrease.setOnClickListener(v -> listener.onIncrease(position, item));
        holder.btnDecrease.setOnClickListener(v -> listener.onDecrease(position, item));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(position, item));
    }

    @Override
    public int getItemCount() {
        return GioHang.size();
    }

    public void updateData() {
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, GioHang.size());
    }

    private String formatCurrency(double amount) {
        NumberFormat fmt = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        return fmt.format((long) amount) + "đ";
    }

    public static class GioHangViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvPrice, tvOriginalPrice, tvQuantity;
        ImageButton btnDecrease, btnIncrease, btnDelete;

        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvOriginalPrice = itemView.findViewById(R.id.tvOriginalPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
