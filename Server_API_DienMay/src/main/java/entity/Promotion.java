package entity;

public class Promotion {

    private String code;
    private double discount;
    private double minTotal;

    public Promotion() {
    }

    public Promotion(String code, double discount, double minTotal) {
        this.code = code;
        this.discount = discount;
        this.minTotal = minTotal;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getMinTotal() {
        return minTotal;
    }

    public void setMinTotal(double minTotal) {
        this.minTotal = minTotal;
    }
}