package com.example.madatour.vue.ui.detail;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.madatour.R;
import com.example.madatour.modele.Tourism;
import com.example.madatour.util.ApiURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailViewModel  extends ViewModel {
    private MutableLiveData<List<String>> apiResponse = new MutableLiveData<>();
    private MutableLiveData<List<String>> apiImage = new MutableLiveData<>();
    private MutableLiveData<List<String>> apiVideo = new MutableLiveData<>();
    private MutableLiveData<String> errorFecthcing = new MutableLiveData<>();



    public LiveData<List<String>> getApiVideo() {
        return apiVideo;
    }

    public LiveData<List<String>> getApiImage() {
        return apiImage;
    } public LiveData<String> getErrorResponse() {
        return errorFecthcing;
    }
    public static int socketTimeout = 7000; // 7 seconds timeout
    public static int maxRetries = 3;

    private String youtubeVideoId;

    private String idTourisme;

    public DetailViewModel(String idTourisme) {
        this.idTourisme = idTourisme;
    }

    public LiveData<List<String>> getApiResponse() {
        return apiResponse;
    }

    public void fetchDataFromApi() {
        Log.d("_id",idTourisme);
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

    public void fetchDataGlobal(RequestQueue requestQueue){
        List<String> listImage =new ArrayList<>();
        List<String> listVideo = new ArrayList<>();

        JSONObject postData = new JSONObject();
        try {
            postData.put("tourimseID", idTourisme);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, ApiURL.URL_TOURISM_BY_ID, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response
                        Log.d("TAFIDITRA", "Couou");
                        try {
                            String message = response.getString("message");
                            JSONArray data = response.getJSONArray("fichier");
                            if (message.equals(ApiURL.CHECK_CODE_OK)) {
                                Log.d("DATA_LENGTH",String.valueOf(data.length()));
                                if (data.length() > 0) {
                                    for (int i = 0; i < data.length(); i++) {
                                        String imgURL = data.getJSONObject(i).getString("lien");
                                        Log.d("Mitsofoka ao @ api",imgURL);
                                        String type = data.getJSONObject(i).getString("type");
                                        if(type.equals("image")){
                                            listImage.add(imgURL);
                                        } else if (type.equals("video")) {
                                            listVideo.add(imgURL);
                                        }
                                    }
                                    apiImage.setValue(listImage);
                                    apiVideo.setValue(listVideo);
                                    errorFecthcing.setValue(null);
                                } else {
                                    apiImage.setValue(null);
                                    apiVideo.setValue(null);
                                    errorFecthcing.setValue("Pas d'activités touristiques en vue");
                                }
                            } else {
                                apiImage.setValue(null);
                                apiVideo.setValue(null);
                                errorFecthcing.setValue("Tourisme API checkcode NOT OK");
                            }
                        } catch (JSONException e) {
                            apiImage.setValue(null);
                            apiVideo.setValue(null);
                            errorFecthcing.setValue("Tourisme API JSON exception" + e.getMessage());
                            Log.d("Tonga fa json exception", "Connexion impossible" + e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        apiImage.setValue(null);
                        apiVideo.setValue(null);
                        errorFecthcing.setValue("Tourisme API JSON exception" + error.getMessage());
                        Log.d("API response tourism error", "API response error " + error.toString());
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tourimseID", idTourisme);
                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(socketTimeout, maxRetries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
    }

