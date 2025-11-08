package com.mycompany.quillcart;

public class Book {
    String title;
    String category;
    double priceTaka;
    int stock;

    public Book(String title, String category, double priceTaka, int stock) {
        this.title = title;
        this.category = category;
        this.priceTaka = priceTaka;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return title;
    }
}