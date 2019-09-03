package com.apps.ahfreelancing.caffetask.presentation.di.module;

import dagger.Module;

import javax.inject.Named;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
@Module
public class ThreadModule {

    @Named("observeOn")
    @Provides
    public Scheduler provideObserveOnScheduler(){
        return AndroidSchedulers.mainThread();
    }

    @Named("subscribeOn")
    @Provides
    public Scheduler provideSubscribeOnScheduler(){
        return Schedulers.io();
    }
}
