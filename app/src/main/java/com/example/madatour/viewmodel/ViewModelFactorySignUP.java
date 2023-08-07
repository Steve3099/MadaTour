package com.example.madatour.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactorySignUP implements ViewModelProvider.Factory {

private final Context context;

public ViewModelFactorySignUP(Context context) {
        this.context = context;
        }

@NonNull
@Override
public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == ViewModelInscription.class) {
        return (T) new ViewModelInscription(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
        }

