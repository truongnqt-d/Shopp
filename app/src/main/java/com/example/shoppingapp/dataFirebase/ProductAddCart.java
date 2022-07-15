package com.example.shoppingapp.dataFirebase;

import java.io.Serializable;

public class ProductAddCart {
    private String id;
    private String title;
    private String price;
    private String description;
    private String imgProduct;
    private String rating;
    private String extraDay;
    private String idUser;
    private int quantity;

    public ProductAddCart() {
    }

    public ProductAddCart(String id, String title, String price, String description,
                          String imgProduct, String rating, String extraDay, String idUser, int quantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.imgProduct = imgProduct;
        this.rating = rating;
        this.extraDay = extraDay;
        this.idUser = idUser;
        this.quantity = quantity;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getExtraDay() {
        return extraDay;
    }

    public void setExtraDay(String extraDay) {
        this.extraDay = extraDay;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
