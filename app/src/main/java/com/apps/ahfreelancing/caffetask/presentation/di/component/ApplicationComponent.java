package com.apps.ahfreelancing.caffetask.presentation.di.component;

import com.apps.ahfreelancing.caffetask.presentation.di.module.ApiModule;
import com.apps.ahfreelancing.caffetask.presentation.di.module.ThreadModule;
import com.apps.ahfreelancing.caffetask.presentation.view.activity.ProductActivity;

import javax.inject.Named;

import dagger.Component;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
@Component(modules = {ApiModule.class, ThreadModule.class})
public interface ApplicationComponent {
    void inject(ProductActivity productActivity);

    Retrofit retrofit();


    @Named("observeOn")
    Scheduler observeScheduler();

    @Named("subscribeOn")
    Scheduler subscribeScheduler();
}
