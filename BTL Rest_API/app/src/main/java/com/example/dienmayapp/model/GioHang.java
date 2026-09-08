package com.example.dienmayapp.model;

public class GioHang {

    private String name;
    private double price;
    private double oldPrice;
    private int quantity;

    // đổi từ int -> String
    private String imageResId;

    public GioHang() {
    }

    public GioHang(String name,
                   double price,
                   double oldPrice,
                   int quantity,
                   String imageResId) {

        this.name = name;
        this.price = price;
        this.oldPrice = oldPrice;
        this.quantity = quantity;
        this.imageResId = imageResId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageResId() {
        return imageResId;
    }

    public void setImageResId(String imageResId) {
        this.imageResId = imageResId;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

}