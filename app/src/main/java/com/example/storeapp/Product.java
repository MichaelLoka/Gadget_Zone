package com.example.storeapp;

public class Product {
    private String Name;
    private String Price;
    private int id;
    private byte[] productImage;
    private int Quantity;
    private String Category;

    public int getId() {
        return id;
    }



    public Product(String Name, String Price, byte[] productImage, int quantity, String Category, int id){
        this.Name = Name;
        this.productImage = productImage;
        this.Price = Price;
        this.Quantity = quantity;
        this.Category = Category;
        this.id= id;
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

    public String getPrice() {
        return Price;
    }
    public void setPrice(String price) {
        Price = price;
    }

    public byte[] getProductImage() {
        return productImage;
    }
    public void setProductImage(byte[] productImage) {this.productImage= productImage;}

    public int getQuantity() {
        return Quantity;
    }
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

}
