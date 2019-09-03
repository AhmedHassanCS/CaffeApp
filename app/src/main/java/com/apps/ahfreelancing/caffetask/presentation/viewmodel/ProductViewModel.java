package com.apps.ahfreelancing.caffetask.presentation.viewmodel;

import androidx.lifecycle.ViewModel;

import com.apps.ahfreelancing.caffetask.data.repository.ProductRepository;

import javax.inject.Inject;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public class ProductViewModel extends ViewModel {
    private ProductRepository productRepository;

    @Inject
    public ProductViewModel(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
}
