package com.sample.mvvm;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.sample.mvvm.data.UserRequest;
import com.sample.mvvm.data.UserService;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class Application extends MultiDexApplication {

    private Scheduler scheduler;
    private UserRequest mUserRequest;

    private static Application get(Context context) {
        return (Application) context.getApplicationContext();
    }

    public static Application create(Context context) {
        return Application.get(context);
    }

    public UserRequest getPeopleService() {
        if (mUserRequest == null) {
            mUserRequest = UserService.create();
        }
        return mUserRequest;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }
        return scheduler;
    }

    public void setPeopleService(UserRequest peopleService) {
        this.mUserRequest = peopleService;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

}
