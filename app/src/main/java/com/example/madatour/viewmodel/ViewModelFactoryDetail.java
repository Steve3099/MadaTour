package com.example.madatour.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.madatour.vue.ui.detail.DetailViewModel;

public class ViewModelFactoryDetail implements ViewModelProvider.Factory {

    private final String idString;

    public ViewModelFactoryDetail(String idString) {
        this.idString = idString;
    }

    @NonNull
    @Override
    public <T extends ViewModel > T create(@NonNull Class<T> modelClass) {
        if (modelClass == DetailViewModel.class) {
            return (T) new DetailViewModel(idString);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
