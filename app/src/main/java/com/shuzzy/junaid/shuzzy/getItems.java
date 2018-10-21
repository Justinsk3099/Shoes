package com.shuzzy.junaid.shuzzy;

public class getItems {

    private String brand,color,description,price,uri;

    public getItems() {
    }

    public getItems(String brand, String color, String description, String price, String uri) {
        this.brand = brand;
        this.color = color;
        this.description = description;
        this.price = price;
        this.uri = uri;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
