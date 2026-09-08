package com.example.dienmayapp.activity;
import com.example.dienmayapp.model.GioHang;

import java.util.ArrayList;
import java.util.List;

public class QuanLyGioHang {
    private static QuanLyGioHang instance;
    private final List<GioHang> cartItems = new ArrayList<>();

    private QuanLyGioHang() {
    }

    public static QuanLyGioHang getInstance() {
        if (instance == null) {
            instance = new QuanLyGioHang();
        }
        return instance;
    }

    public List<GioHang> getCartItems() {
        return cartItems;
    }

    public void addToCart(GioHang newItem) {
        for (GioHang item : cartItems) {
            if (item.getName().equalsIgnoreCase(newItem.getName())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        cartItems.add(newItem);
    }

    public void removeItem(int position) {
        if (position >= 0 && position < cartItems.size()) {
            cartItems.remove(position);
        }
    }

    public void clearCart() {
        cartItems.clear();
    }
}
