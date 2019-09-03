package com.apps.ahfreelancing.caffetask.presentation.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public class Addition {
    private int id;
    private String title;
    @SerializedName("subAdd") private List<SubAddition> subAdditions;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<SubAddition> getSubAdditions() {
        return subAdditions;
    }

    public Addition(int id, String title, List<SubAddition> subAdditions) {
        this.id = id;
        this.title = title;
        this.subAdditions = subAdditions;
    }
}
