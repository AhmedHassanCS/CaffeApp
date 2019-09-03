package com.apps.ahfreelancing.caffetask.data.api;

import com.apps.ahfreelancing.caffetask.data.entity.ProductWrapper;
import com.apps.ahfreelancing.caffetask.data.entity.calls.ProductBody;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public interface ProductApi {
    @GET("product")
    Single<ProductWrapper> getProduct(@Body ProductBody productBody);
}
