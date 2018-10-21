package com.shuzzy.junaid.shuzzy;

public class Model {
    String url,brand,price,color;

    public Model(String url, String brand, String price,String color) {
        this.url = url;
        this.brand = brand;
        this.price = price;
        this.color=color;
    }
    public Model(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return brand;
    }

    public void setName(String name) {
        this.brand = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
