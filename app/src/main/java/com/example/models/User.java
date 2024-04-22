package com.example.models;

import java.util.ArrayList;

public class User {
    private String uid;
    private String email;
    private String name;
    private int age;
    private String phone;

    private ArrayList<Order> orders = new ArrayList<>();


    public String getUid() {
        return uid;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

    public User(String uid, String email, String imageUrl, String name, int age, String phone) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.imageUrl = imageUrl;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
