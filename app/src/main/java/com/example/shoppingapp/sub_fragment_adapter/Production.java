package com.example.shoppingapp.sub_fragment_adapter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
@IgnoreExtraProperties
public class Production implements Parcelable, Serializable {
//    Serializable
    @Exclude
    private String document;
    @Exclude
    private String id;
    @Exclude
    private String idUser;
    @Exclude
    private String extraDay;

    private int quantity;
    private String title;
    private String price;
    private String imgProduct;
    private String rating;

    public Production() {
    }

    public Production(String id, String imgProduct, String title, String price, String rating) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imgProduct = imgProduct;
        this.rating = rating;
    }

    public Production(String id, String idUser, String extraDay, int quantity, String title, String price, String imgProduct, String rating) {
        this.id = id;
        this.idUser = idUser;
        this.extraDay = extraDay;
        this.quantity = quantity;
        this.title = title;
        this.price = price;
        this.imgProduct = imgProduct;
        this.rating = rating;
    }

    protected Production(Parcel in) {
        document = in.readString();
        id = in.readString();
        idUser = in.readString();
        extraDay = in.readString();
        quantity = in.readInt();
        title = in.readString();
        price = in.readString();
        imgProduct = in.readString();
        rating = in.readString();
    }

    public static final Creator<Production> CREATOR = new Creator<Production>() {
        @Override
        public Production createFromParcel(Parcel in) {
            return new Production(in);
        }

        @Override
        public Production[] newArray(int size) {
            return new Production[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getExtraDay() {
        return extraDay;
    }

    public void setExtraDay(String extraDay) {
        this.extraDay = extraDay;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    @Exclude
    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(document);
        dest.writeString(id);
        dest.writeString(idUser);
        dest.writeString(extraDay);
        dest.writeInt(quantity);
        dest.writeString(title);
        dest.writeString(price);
        dest.writeString(imgProduct);
        dest.writeString(rating);
    }
}
