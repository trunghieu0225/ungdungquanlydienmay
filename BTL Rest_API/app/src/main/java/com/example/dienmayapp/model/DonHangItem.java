package com.example.dienmayapp.model;

public class DonHangItem {
    private int orderId;
    private String customerName;
    private String productName;
    private String paymentMethod;
    private String voucher;
    private String price;
    private String total;
    private String status;

    public DonHangItem(int orderId, String customerName, String productName,
                       String paymentMethod, String voucher,
                       String price, String total, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.productName = productName;
        this.paymentMethod = paymentMethod;
        this.voucher = voucher;
        this.price = price;
        this.total = total;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProductName() {
        return productName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getVoucher() {
        return voucher;
    }

    public String getPrice() {
        return price;
    }

    public String getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }
}