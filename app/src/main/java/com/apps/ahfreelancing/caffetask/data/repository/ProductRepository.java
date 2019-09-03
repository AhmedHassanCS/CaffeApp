package com.apps.ahfreelancing.caffetask.data.repository;

import com.apps.ahfreelancing.caffetask.data.api.ApiCalls;
import com.apps.ahfreelancing.caffetask.data.entity.ProductWrapper;
import com.apps.ahfreelancing.caffetask.data.entity.calls.PurchaseBody;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public class ProductRepository {
    private ApiCalls apiCalls;

    @Inject
    public ProductRepository(ApiCalls apiCalls){
        this.apiCalls = apiCalls;
    }

    public Single<ProductWrapper> getProduct(int id){
        return apiCalls.getProduct(id);
    }

    public Single<Boolean> purchase(PurchaseBody purchaseBody){
        return apiCalls.storeOrder(purchaseBody).map(purchaseResponse -> purchaseResponse.getCode() == 200);
    }

}
