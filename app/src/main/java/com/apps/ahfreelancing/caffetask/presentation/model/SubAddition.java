package com.apps.ahfreelancing.caffetask.presentation.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public class SubAddition {
    private int id;
    private String title;
    @SerializedName("subAdd") private int parent;
    @SerializedName("img") private String imageURL;
    private boolean chosen;

    public SubAddition(int id, String title, int parent, String imageURL) {
        this.id = id;
        this.title = title;
        this.parent = parent;
        this.imageURL = imageURL;
        this.chosen = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getParent() {
        return parent;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public boolean isChosen() {
        return chosen;
    }
}
