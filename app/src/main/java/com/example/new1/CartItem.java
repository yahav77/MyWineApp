package com.example.new1;

public class CartItem {
    private Wine item;
    private int quantity;

    public CartItem(Wine item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public CartItem() {
    }

    public Wine getItem() {
        return item;
    }

    public void setItem(Wine item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
