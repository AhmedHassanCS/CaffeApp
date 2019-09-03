package com.apps.ahfreelancing.caffetask.presentation.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public class Product {
    private int id;
    private String title;
    private String description;
    private String price;
    @SerializedName("img") private String imageURL;
    @SerializedName("Additions") private List<Addition> additions;

    public Product(int id, String title, String description, String price, String imageURL, List<Addition> additions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.additions = additions;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public List<Addition> getAdditions() {
        return additions;
    }
}

