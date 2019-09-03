package com.apps.ahfreelancing.caffetask.presentation.di;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import javax.inject.Inject;

public class ViewModelFactory <T extends ViewModel> implements ViewModelProvider.Factory {

    T viewModel;
    @Inject
    public ViewModelFactory(T viewModel){
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public <V extends ViewModel> V create(@NonNull Class<V> modelClass) {
        return (V) viewModel;
    }
}

