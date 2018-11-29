/**
 * Copyright 2016 Erik Jhordan Rey.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sample.mvvm.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sample.mvvm.databinding.ItemPeopleBinding;
import com.sample.mvvm.R;
import com.sample.mvvm.model.Users;
import com.sample.mvvm.viewmodel.ItemPeopleViewModel;

import java.util.Collections;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.PeopleAdapterViewHolder> {

    private List<Users> mUserList;

    UserAdapter() {
        System.out.println("Nikhil 2222");
        this.mUserList = Collections.emptyList();
    }

    @NonNull
    @Override
    public PeopleAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPeopleBinding itemPeopleBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_people,
                        parent, false);
        return new PeopleAdapterViewHolder(itemPeopleBinding);
    }

    @Override
    public void onBindViewHolder(PeopleAdapterViewHolder holder, int position) {
        holder.bindPeople(mUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    void setPeopleList(List<Users> peopleList) {
        this.mUserList = peopleList;
        notifyDataSetChanged();
    }

    static class PeopleAdapterViewHolder extends RecyclerView.ViewHolder {
        ItemPeopleBinding mItemPeopleBinding;

        PeopleAdapterViewHolder(ItemPeopleBinding itemPeopleBinding) {
            super(itemPeopleBinding.itemPeople);
            this.mItemPeopleBinding = itemPeopleBinding;
        }

        void bindPeople(Users users) {
            if (mItemPeopleBinding.getPeopleViewModel() == null) {
                mItemPeopleBinding.setPeopleViewModel(new ItemPeopleViewModel(users));
            } else {
                //mItemPeopleBinding.getPeopleViewModel().setpeople(users);
            }
        }
    }
}
