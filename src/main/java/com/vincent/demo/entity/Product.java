package com.vincent.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {
    private String id;
    private String name;
    private String category;
    private String language;
    private String publisher;
    private String author;
    private int price;

    public Product() {

    }

    public Product(String id, String name, String category, String language, String publisher, int price, String author) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.language = language;
        this.publisher = publisher;
        this.price = price;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
