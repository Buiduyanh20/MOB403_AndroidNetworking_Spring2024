package buiduyanh.fpolyhn.lab5_nwk.Model;

import com.google.gson.annotations.SerializedName;

public class ProductModel{

    @SerializedName("_id")
    private String id;
    private String name;
    private int price;
    private String color;


    public ProductModel() {
    }

    public ProductModel(String id, String name, int price, String color ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.color = color;

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



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
