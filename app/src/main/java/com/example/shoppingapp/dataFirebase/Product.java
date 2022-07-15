package com.example.shoppingapp.dataFirebase;


import java.io.Serializable;

public class Product implements Serializable {
    //    Serializable
//    private String percent;
    private String id;
    private String title;
    private String price;
    private String imgProduct;
    private String rating;

    public Product() {
    }

    public Product(String id, String imgProduct, String title, String price, String rating) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imgProduct = imgProduct;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
