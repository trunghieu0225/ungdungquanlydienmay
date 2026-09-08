package entity;

public class OrderRequest {

    private String username;
    private String voucherCode;
    private double totalAmount;

    public OrderRequest() {
    }

    public OrderRequest(String username,
                        String voucherCode,
                        double totalAmount) {
        this.username = username;
        this.voucherCode = voucherCode;
        this.totalAmount = totalAmount;
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
}