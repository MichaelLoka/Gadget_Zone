package com.example.storeapp.Model;

public class Category {

    private String Cat_Name;
    private int photo_id;

    public String getCat_Name() {
        return Cat_Name;
    }

    public void setCat_Name(String cat_Name) {
        Cat_Name = cat_Name;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public Category(String cat_Name, int photo_id) {
        Cat_Name = cat_Name;
        this.photo_id = photo_id;
    }
}
