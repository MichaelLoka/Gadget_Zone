package com.example.storeapp;

import android.text.Html;

public class Product {
    private String Description;
    private String Price;
    private int Imange_id;

    public Product(String Description, String Price, int Image_id){
        this.Description = Description;
        this.Imange_id = Image_id;
        this.Price = Price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public long getImange_id() {
        return Imange_id;
    }

    public void setImange_id(int imange_id) {
        Imange_id = imange_id;
    }
}
