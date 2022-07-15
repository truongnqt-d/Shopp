package com.example.shoppingapp.dataFirebase;

import java.io.Serializable;

public class Advert implements Serializable {
    //    Serializable
//    private String percent;
    private String id;
    private String title;
    private String imgProduct;

    public Advert() {
    }

    public Advert(String id, String imgProduct, String title) {
        this.id = id;
        this.title = title;
        this.imgProduct = imgProduct;
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

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }
}

