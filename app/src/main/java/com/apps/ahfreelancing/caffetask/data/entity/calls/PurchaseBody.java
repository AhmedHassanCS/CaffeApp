package com.apps.ahfreelancing.caffetask.data.entity.calls;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed Hassan on 9/4/2019.
 */
public class PurchaseBody {
    @SerializedName("user_id") private int userId;
    @SerializedName("product_id") private int product_id;
    @SerializedName("brunche_id") private int branchId;
    @SerializedName("addition") private String additions;
    @SerializedName("subadd") private String subAdditions;
    @SerializedName("count") private int quantity;

    public PurchaseBody(String additions, String subAdditions, int quantity) {
        this.userId = 1;
        this.product_id = 1;
        this.branchId = 1;
        this.additions = additions;
        this.subAdditions = subAdditions;
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getBranchId() {
        return branchId;
    }

    public String getAdditions() {
        return additions;
    }

    public String getSubAdditions() {
        return subAdditions;
    }

    public int getQuantity() {
        return quantity;
    }
}
