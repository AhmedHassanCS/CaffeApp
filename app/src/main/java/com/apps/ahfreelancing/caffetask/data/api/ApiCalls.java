package com.apps.ahfreelancing.caffetask.data.api;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public class ApiCalls {
    private Retrofit retrofit;

    @Inject
    public ApiCalls(Retrofit retrofit){
        this.retrofit = retrofit;
    }
}
