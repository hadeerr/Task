package com.example.hp.task.Model;

/**
 * Created by hp on 7/16/2018.
 */

public class Product {
    int id ;
    String description , price;
    Image image;

    public Product(int id, String description, String price, Image image) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
