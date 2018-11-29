/**
 * Copyright 2016 Erik Jhordan Rey. <p/> Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at <p/> http://www.apache.org/licenses/LICENSE-2.0 <p/> Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package com.sample.mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.sample.mvvm.Application;
import com.sample.mvvm.data.UserRequest;
import com.sample.mvvm.data.UserResponse;
import com.sample.mvvm.data.UserService;
import com.sample.mvvm.model.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class UserViewModel extends Observable {

    public ObservableInt peopleProgress;
    public ObservableInt peopleRecycler;
    public ObservableInt peopleLabel;
    public ObservableField<String> messageLabel;

    private List<Users> peopleList;
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public UserViewModel(@NonNull Context context) {

        this.context = context;
        this.peopleList = new ArrayList<>();
        peopleProgress = new ObservableInt(View.GONE);
        peopleRecycler = new ObservableInt(View.GONE);
        peopleLabel = new ObservableInt(View.VISIBLE);
        messageLabel = new ObservableField<>("Loading...");
    }

    public void onClickFabLoad(View view) {
        initializeViews();
        fetchPeopleList();
    }

    public void initializeViews() {
        peopleLabel.set(View.GONE);
        peopleRecycler.set(View.GONE);
        peopleProgress.set(View.VISIBLE);
    }

    private void fetchPeopleList() {

        Application peopleApplication = Application.create(context);
        UserRequest peopleService = peopleApplication.getPeopleService();

        Disposable disposable = peopleService.fetchPeople(UserService.RANDOM_USER_URL)
                .subscribeOn(peopleApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserResponse>() {
                    @Override
                    public void accept(UserResponse peopleResponse) {
                        changePeopleDataSet(peopleResponse.getUsersList());
                        peopleProgress.set(View.GONE);
                        peopleLabel.set(View.GONE);
                        peopleRecycler.set(View.VISIBLE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        messageLabel.set("Loading...");
                        peopleProgress.set(View.GONE);
                        peopleLabel.set(View.VISIBLE);
                        peopleRecycler.set(View.GONE);
                    }
                });

        compositeDisposable.add(disposable);
    }

    private void changePeopleDataSet(List<Users> peoples) {
        peopleList.addAll(peoples);
        setChanged();
        notifyObservers();
    }

    public List<Users> getPeopleList() {
        return peopleList;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }
}
