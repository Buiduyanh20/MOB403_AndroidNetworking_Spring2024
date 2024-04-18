package quannkph29999.fpoly.executeservice;

public class Product {
    private String _id;
    private String name;
    private int price;
    private String brand;

    public Product(String _id, String name, int price, String brand) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.brand = brand;
    }

    public Product(String name, int price, String brand) {
        this.name = name;
        this.price = price;
        this.brand = brand;
    }

    public Product() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
