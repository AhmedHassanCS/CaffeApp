package com.apps.ahfreelancing.caffetask.presentation.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apps.ahfreelancing.caffetask.data.entity.ProductWrapper;
import com.apps.ahfreelancing.caffetask.data.entity.calls.PurchaseBody;
import com.apps.ahfreelancing.caffetask.data.repository.ProductRepository;
import com.apps.ahfreelancing.caffetask.presentation.model.Product;
import com.apps.ahfreelancing.caffetask.presentation.view.activity.ConnectionActivity;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public class ProductViewModel extends AndroidViewModel {
    private Scheduler subscribeOnScheduler;
    private Scheduler observeOnScheduler;

    private ProductRepository productRepository;

    private MutableLiveData<Product> productLiveData;
    private MutableLiveData<Boolean> purchaseLiveData;
    @Inject
    public ProductViewModel(
            Application application,
            ProductRepository productRepository,
            @Named("subscribeOn") Scheduler subscribeOn,
            @Named("observeOn") Scheduler observeOn){

        super(application);
        this.productRepository = productRepository;
        this.observeOnScheduler = observeOn;
        this.subscribeOnScheduler = subscribeOn;

        productLiveData = new MutableLiveData<>();
        purchaseLiveData = new MutableLiveData<>();
    }

    public LiveData<Product> getProductLiveData(int id) {
        updateProduct(id);
        return productLiveData;
    }

    public LiveData<Boolean> submitPurchase(PurchaseBody purchaseBody){
        productRepository.purchase(purchaseBody)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe(new PurchaseObserver());
        return purchaseLiveData;
    }

    public void updateProduct(int id){
        productRepository.getProduct(id)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe(new ProductObserver());

    }

    private class ProductObserver implements SingleObserver<ProductWrapper>{
        Disposable d;
        @Override
        public void onSubscribe(Disposable d) {
            this.d = d;
        }

        @Override
        public void onSuccess(ProductWrapper productWrapper) {
            if(productWrapper.getCode() == 200)
                productLiveData.setValue(productWrapper.getProduct());
            else {
                Intent i = new Intent(getApplication(), ConnectionActivity.class);
                getApplication().startActivity(i);
            }
            this.d.dispose();
        }


        @Override
        public void onError(Throwable e) {
            Log.d("Network_Error", e.getMessage());
            Intent i = new Intent(getApplication(), ConnectionActivity.class);
            i.setFlags(i.getFlags() | FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(i);
        }
    }

    class PurchaseObserver implements SingleObserver<Boolean>{

        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onSuccess(Boolean success) {
            purchaseLiveData.setValue(success);
        }

        @Override
        public void onError(Throwable e) {
            Log.d("Network_Error", e.getMessage());
            Intent i = new Intent(getApplication(), ConnectionActivity.class);
            getApplication().startActivity(i);
        }
    }
}
