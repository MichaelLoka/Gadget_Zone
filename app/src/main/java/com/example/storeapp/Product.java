package com.example.storeapp;

public class Product {
    private String Name;
    private int Price;
    private int Imange_id;
    private int Quantity;
    private String Category;
    private int id;
    public Product(int id, String Name, int quantity, int Price, int Image_id, String Category){
        this.id = id;
        this.Name = Name;
        this.Imange_id = Image_id;
        this.Price = Price;
        this.Quantity = quantity;
        this.Category = Category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getImange_id() {
        return Imange_id;
    }

    public void setImange_id(int imange_id) {
        Imange_id = imange_id;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

}
