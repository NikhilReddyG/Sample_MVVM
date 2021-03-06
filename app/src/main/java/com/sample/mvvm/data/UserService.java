package com.sample.mvvm.data;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {

    private final static String BASE_URL = "http://api.randomuser.me/";
    public final static String RANDOM_USER_URL = "http://api.randomuser.me/?results=10&nat=en";
    public final static String PROJECT_URL = "https://github.com/erikcaffrey/People-MVVM";

    public static UserRequest create() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(UserRequest.class);
    }
}
