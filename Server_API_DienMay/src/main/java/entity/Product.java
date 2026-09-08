package entity;

public class Product {

    private int productId;
    private String name;
    private double price;
    private double oldPrice;
    private String image;
    private String description;
    private String gift;
    private String rating;

    public Product() {
    }

    public Product(int productId, String name, double price,
                   double oldPrice, String image, String description, String gift, String rating) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.oldPrice = oldPrice;
        this.image = image;
        this.description = description;
        this.gift = gift;
        this.rating = rating;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
