package com.sample.mvvm.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sample.mvvm.R;
import com.sample.mvvm.databinding.TestActivityBinding;
import com.sample.mvvm.viewmodel.UserViewModel;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private TestActivityBinding mMainActivityBinding;
    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        setSupportActionBar(mMainActivityBinding.toolbar);
        setupListPeopleView(mMainActivityBinding.listPeople);
        setupObserver(mUserViewModel);
    }

    private void initDataBinding() {
        mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.test_activity);
        mUserViewModel = new UserViewModel(this);
        mMainActivityBinding.setMainViewModel(mUserViewModel);
    }

    private void setupListPeopleView(RecyclerView listPeople) {
        UserAdapter adapter = new UserAdapter();
        listPeople.setAdapter(adapter);
        listPeople.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserViewModel.reset();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof UserViewModel) {
            UserAdapter peopleAdapter = (UserAdapter) mMainActivityBinding.listPeople.getAdapter();
            UserViewModel peopleViewModel = (UserViewModel) observable;
            peopleAdapter.setPeopleList(peopleViewModel.getPeopleList());
        }
    }
}
