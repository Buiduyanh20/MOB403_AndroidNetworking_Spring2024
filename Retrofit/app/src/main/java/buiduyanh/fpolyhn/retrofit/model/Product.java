package buiduyanh.fpolyhn.retrofit.model;

public class Product {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String price;
    private String brand;
    private String id;

    public Product(String name, String price, String brand) {
        this.name = name;
        this.price = price;
        this.brand = brand;
    }
}
