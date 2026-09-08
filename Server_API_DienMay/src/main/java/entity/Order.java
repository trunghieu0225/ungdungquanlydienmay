package entity;

public class Order {

    private int orderId;
    private int userId;
    private String username;
    private String voucherCode;
    private double totalAmount;
    private String status;

    public Order() {
    }

    public Order(int orderId,
                 int userId,
                 String username,
                 String voucherCode,
                 double totalAmount,
                 String status) {

        this.orderId = orderId;
        this.userId = userId;
        this.username = username;
        this.voucherCode = voucherCode;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}