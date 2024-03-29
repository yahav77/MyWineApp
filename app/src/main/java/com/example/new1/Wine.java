package com.example.new1;

public class Wine {
    private String name;
    private double price;

    private String ml;

    private int year;
    private int image;

    public Wine(String name, double price, String ml, int year, int image) {
        this.name = name;
        this.price = price;
        this.ml = ml;
        this.year = year;
        this.image = image;
    }

    public Wine() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMl() {
        return ml;
    }

    public void setMl(String ml) {
        this.ml = ml;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
