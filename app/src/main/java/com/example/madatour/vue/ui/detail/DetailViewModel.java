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

    private String youtubeVideoId;

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

//    Default width x 225 should be 300px x 225
    public void fechtVideoURlFromApi(){
//        youtubeVideoId = "<iframe width=\"300\" height=\"225\" src=\"https://www.youtube.com/embed/th9WMMJuOFU?&autoplay=1\" title=\"YouTube video player\" frameborder=\"0\" allow=\"autoplay;\" allowfullscreen></iframe>";
        youtubeVideoId = "<iframe width=\"300\" height=\"225\" src=\"https://www.youtube.com/embed/6Xnz2fwf9Yk\" title=\"Madagascar - 4k\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
    }
    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }
}
