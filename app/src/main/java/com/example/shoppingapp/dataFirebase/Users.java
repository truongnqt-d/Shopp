package com.example.shoppingapp.dataFirebase;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
@IgnoreExtraProperties
public class Users implements Serializable {
    @Exclude
    private boolean isAdmin;
    @Exclude
    private String userUid;
    private String name;
    private int age;
    private String genDer;
    private String imagePerson;

    public Users() {}

    public Users(boolean isAdmin, String name, int age, String genDer, String imagePerson) {
        this.isAdmin = isAdmin;
        this.name = name;
        this.age = age;
        this.genDer = genDer;
        this.imagePerson = imagePerson;
    }

    public Users(String id, boolean isAdmin, String name, int age, String genDer, String imagePerson) {
        this.userUid = id;
        this.isAdmin = isAdmin;
        this.name = name;
        this.age = age;
        this.genDer = genDer;
        this.imagePerson = imagePerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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

    public String getId() {
        return userUid;
    }

    public void setId(String id) {
        this.userUid = id;
    }

    @Override
    public String toString() {
        return "Users{" +
                "isAdmin=" + isAdmin +
                ", fullName='" + name + '\'' +
                ", age='" + age + '\'' +
                ", genDer='" + genDer + '\'' +
                ", imagePerson='" + imagePerson + '\'' +
                '}';
    }
}
