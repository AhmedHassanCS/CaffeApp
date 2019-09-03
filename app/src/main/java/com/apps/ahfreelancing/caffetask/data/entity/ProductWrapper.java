package com.apps.ahfreelancing.caffetask.data.entity;

import com.apps.ahfreelancing.caffetask.presentation.model.Product;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public class ProductWrapper {
    private int code;
    private String status;
    @SerializedName("Product") private Product product;

    public ProductWrapper(int code, String status, Product product) {
        this.code = code;
        this.status = status;
        this.product = product;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public Product getProduct() {
        return product;
    }
}
