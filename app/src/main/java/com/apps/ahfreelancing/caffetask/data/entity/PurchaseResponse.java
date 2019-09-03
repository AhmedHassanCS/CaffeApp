package com.apps.ahfreelancing.caffetask.data.entity;

/**
 * Created by Ahmed Hassan on 9/4/2019.
 */
public class PurchaseResponse {
    private int code;
    private String status;

    public PurchaseResponse(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
