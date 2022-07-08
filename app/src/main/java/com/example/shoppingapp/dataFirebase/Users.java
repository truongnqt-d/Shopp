package com.example.shoppingapp.dataFirebase;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
@IgnoreExtraProperties
public class Users implements Serializable {
    @Exclude
    private boolean isAdmin;
    @Exclude
    private int id;
    private String fullName;
    private String age;
    private String genDer;
    private String imagePerson;

    public Users() {}

    public Users(boolean isAdmin, String fullName, String age, String genDer, String imagePerson) {
        this.isAdmin = isAdmin;
        this.fullName = fullName;
        this.age = age;
        this.genDer = genDer;
        this.imagePerson = imagePerson;
    }

    public Users(int id, String fullName, String age, String genDer, String imagePerson) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.genDer = genDer;
        this.imagePerson = imagePerson;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGenDer() {
        return genDer;
    }

    public void setGenDer(String genDer) {
        this.genDer = genDer;
    }

    public void setImagePerson(String imagePerson) {
        this.imagePerson = imagePerson;
    }

    public String getImagePerson() {
        return imagePerson;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Users{" +
                "isAdmin=" + isAdmin +
                ", fullName='" + fullName + '\'' +
                ", age='" + age + '\'' +
                ", genDer='" + genDer + '\'' +
                ", imagePerson='" + imagePerson + '\'' +
                '}';
    }
}
