package com.apps.ahfreelancing.caffetask.data.api;

import com.apps.ahfreelancing.caffetask.data.entity.ProductWrapper;
import com.apps.ahfreelancing.caffetask.data.entity.calls.ProductBody;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public class ApiCalls {
    private ProductApi productApi;

    @Inject
    public ApiCalls(Retrofit retrofit){
        this.productApi = retrofit.create(ProductApi.class);
    }

    public Single<ProductWrapper> getProduct(int id){
        return productApi.getProduct(new ProductBody(id));
    }
}
