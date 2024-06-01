package com.example.fengshuistore.Model;

import android.net.Uri;

public class Product {
    private int id;
    private String name;
    private String description;
    private float price;
    private Uri image;

    public Product() {
    }

    public Product(int id, String name, String description, float price, Uri image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public Uri getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
