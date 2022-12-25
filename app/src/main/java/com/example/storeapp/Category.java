package com.example.storeapp;

import android.graphics.Bitmap;

public class Category {
    private byte[] categoryImage;
    private String categoryName;


    public Category(byte[] categoryImage, String categoryName) {
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
    }


    public void setCategoryImage(byte[] categoryImage) {
        this.categoryImage = categoryImage;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public byte[] getCategoryImage() {
        return categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
