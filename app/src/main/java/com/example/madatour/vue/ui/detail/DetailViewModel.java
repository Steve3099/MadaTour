package com.example.madatour.vue.ui.detail;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.madatour.R;

import java.util.ArrayList;
import java.util.List;

public class DetailViewModel  extends ViewModel {
    private MutableLiveData<List<String>> apiResponse = new MutableLiveData<>();

    public LiveData<List<String>> getApiResponse() {
        return apiResponse;
    }

    public void fetchDataFromApi() {
        // Simulate API call and update apiResponse
        List<String> responseData = new ArrayList<>();
        // Replace this with actual API call and data parsing
        responseData.add(String.valueOf(R.drawable.rova));
        responseData.add(String.valueOf(R.drawable.rova));
        responseData.add(String.valueOf(R.drawable.rova));
        responseData.add(String.valueOf(R.drawable.rova));
        responseData.add(String.valueOf(R.drawable.rova));

        apiResponse.setValue(responseData);
    }
}
