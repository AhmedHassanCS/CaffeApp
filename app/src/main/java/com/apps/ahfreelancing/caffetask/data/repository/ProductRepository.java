package com.apps.ahfreelancing.caffetask.data.repository;

import com.apps.ahfreelancing.caffetask.data.api.ApiCalls;

import javax.inject.Inject;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public class ProductRepository {
    private ApiCalls apiCalls;

    @Inject
    public ProductRepository(ApiCalls apiCalls){
        this.apiCalls = apiCalls;
    }
}
