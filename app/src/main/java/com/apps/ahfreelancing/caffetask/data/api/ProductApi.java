package com.apps.ahfreelancing.caffetask.data.api;

import com.apps.ahfreelancing.caffetask.data.entity.ProductWrapper;
import com.apps.ahfreelancing.caffetask.data.entity.PurchaseResponse;
import com.apps.ahfreelancing.caffetask.data.entity.calls.ProductBody;
import com.apps.ahfreelancing.caffetask.data.entity.calls.PurchaseBody;

import io.reactivex.Single;
import retrofit2.http.Body;

import retrofit2.http.POST;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public interface ProductApi {
    @POST("Product")
    Single<ProductWrapper> getProduct(@Body ProductBody productBody);

    @POST("store_Order")
    Single<PurchaseResponse> storeOrder(@Body PurchaseBody purchaseBody);

}
