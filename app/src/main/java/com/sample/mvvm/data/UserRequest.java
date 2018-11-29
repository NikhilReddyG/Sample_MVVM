package com.sample.mvvm.data;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface UserRequest {
    @GET Observable<UserResponse> fetchPeople(@Url String url);
}
