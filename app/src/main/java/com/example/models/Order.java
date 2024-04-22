package com.example.models;

import java.util.List;

public class Order {

    private int total;
    private List<CartItem> items;

    private String cardNumberFourDigits;
    private String cardExpiration;
    private String ccv;

    private String address;

    public Order(int total, List<CartItem> items, String cardNumberFourDigits, String cardExpiration, String ccv, String address) {
        this.total = total;
        this.items = items;
        this.cardNumberFourDigits = cardNumberFourDigits;
        this.cardExpiration = cardExpiration;
        this.ccv = ccv;
        this.address = address;
    }

    public Order() {}

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public String getCardNumberFourDigits() {
        return cardNumberFourDigits;
    }

    public void setCardNumberFourDigits(String cardNumberFourDigits) {
        this.cardNumberFourDigits = cardNumberFourDigits;
    }

    public String getCardExpiration() {
        return cardExpiration;
    }

    public void setCardExpiration(String cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
