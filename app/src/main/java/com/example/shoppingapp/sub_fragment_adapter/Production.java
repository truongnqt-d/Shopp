package com.example.shoppingapp.sub_fragment_adapter;

import android.graphics.Bitmap;
import android.os.Parcel;

import java.io.Serializable;

public class Production implements Serializable {
//    Serializable
//    private String percent;
    private String id;
    private boolean itemViewType;
    private int resourceId;
    private Bitmap bitmap;
    private String title;
    private String price;
    private String description;
    private String imgProduct;
    private String rating;
    private String percent;

    public Production() {
    }

    public Production(String imgProduct, String title, String description, String price, String rating, String percent) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.imgProduct = imgProduct;
        this.rating = rating;
        this.percent = percent;
    }

    public Production(int resourceId) {
        this.resourceId = resourceId;
    }

    public Production(Bitmap bitmap, String title, String price) {
        this.bitmap = bitmap;
        this.title = title;
        this.price = price;
    }

    public Production(String title, String price, String description) {
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public Production(int resourceId, String title, String price, String description) {
        this.resourceId = resourceId;
        this.title = title;
        this.price = price;
        this.description = description;
    }

    protected Production(Parcel in) {
        resourceId = in.readInt();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        title = in.readString();
        price = in.readString();
    }

    public boolean getItemViewType() {
        return itemViewType;
    }

    public void setItemViewType(boolean itemViewType) {
        this.itemViewType = itemViewType;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
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

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
