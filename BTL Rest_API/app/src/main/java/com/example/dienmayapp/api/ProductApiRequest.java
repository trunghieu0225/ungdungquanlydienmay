package com.example.dienmayapp.api;

public class ProductApiRequest {

    private String name;
    private double price;
    private double oldPrice;
    private String image;
    private String description;
    private String gift;
    private String rating;

    public ProductApiRequest() {
    }

    public ProductApiRequest(
            String name,
            double price,
            double oldPrice,
            String image,
            String description,
            String gift,
            String rating
    ) {
        this.name = name;
        this.price = price;
        this.oldPrice = oldPrice;
        this.image = image;
        this.description = description;
        this.gift = gift;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getGift() {
        return gift;
    }

    public String getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}