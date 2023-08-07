package com.example.madatour.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.madatour.modele.Utilisateur;
import com.example.madatour.service.VolleySingleton;
import com.example.madatour.util.ApiURL;
import com.example.madatour.vue.DashboardActivity;
import com.example.madatour.vue.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewModelInscription extends ViewModel {

    private MutableLiveData<String> apiResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    Context context;

   public static int socketTimeout = 7000; // 7 seconds timeout
    public static int maxRetries = 3;
    public LiveData<String> getApiResponse() {
        return apiResponse;
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    public void signupUserAPi(String nom,String prenom,String email,String mdp) {
        loading.setValue(true); // Set loading to true before making the API call

        String url =ApiURL.URL_INSCRIPTION;

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nom", nom);
            jsonBody.put("prenom", prenom);
            jsonBody.put("email", email);
            jsonBody.put("dtn", "30-01-1999");
            jsonBody.put("login", nom);
            jsonBody.put("mdp", mdp);
            // Add other parameters to the JSON body
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,jsonBody,
                response -> {
                    // Handle the successful response
                    try {
                        String message =  response.getString("message");
                        if(message.equals(ApiURL.CHECK_CODE_OK)){
                            apiResponse.setValue(null);
                            loading.setValue(false);
                        }
                    } catch (JSONException e) {
                        apiResponse.setValue(e.toString());
                        throw new RuntimeException(e);
                    }finally {
                        loading.setValue(false);
                    }

                    loading.setValue(false);
                },
                error -> {
                    // Handle API error
                    apiResponse.setValue(error.toString());
                    loading.setValue(false); // Set loading to false after API call completes
                }){
            @Override
            public RetryPolicy getRetryPolicy() {
                return new DefaultRetryPolicy(socketTimeout, maxRetries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            }
        };

        VolleySingleton.getmInstance(context).getRequestQueue().add(request);

    }
}

