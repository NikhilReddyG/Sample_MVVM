package com.sample.mvvm.data;

import com.google.gson.annotations.SerializedName;
import com.sample.mvvm.model.Users;

import java.util.List;

public class UserResponse {

    @SerializedName("results")
    private List<Users> mUserList;

    public List<Users> getUsersList() {
        return mUserList;
    }
}
