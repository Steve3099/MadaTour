package com.example.madatour.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.madatour.controler.CategoryAdapter;
import com.example.madatour.controler.TourismAdapter;
import com.example.madatour.modele.Category;
import com.example.madatour.modele.Tourism;
import com.example.madatour.modele.Utilisateur;
import com.example.madatour.recycler.RecyclerViewInterface;
import com.example.madatour.util.ApiURL;
import com.example.madatour.util.CategImgCheck;
import com.example.madatour.util.SpacesItemDecoration;
import com.example.madatour.vue.DashboardActivity;
import com.example.madatour.vue.MainActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewModelDashboard extends ViewModel {

    private MutableLiveData<List<Category>> categoryListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Tourism>> tourismListLiveData = new MutableLiveData<>();

    private MutableLiveData<String> errorCategory = new MutableLiveData<>();
    private MutableLiveData<String> errorTourism = new MutableLiveData<>();


    public static int socketTimeout = 7000; // 7 seconds timeout
    public static int maxRetries = 3;

    public MutableLiveData<String> getErrorTourism() {
        return errorTourism;
    }

    public MutableLiveData<String> getErrorCategory() {
        return errorCategory;
    }

    public LiveData<List<Category>> getCategoryListLiveData() {
        return categoryListLiveData;
    }

    public LiveData<List<Tourism>> getTourismListLiveData() {
        return tourismListLiveData;
    }


    public void fetchCategories(RequestQueue requestQueue) {
        List<Category> listCategory = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, ApiURL.URL_CATEGORY, null  ,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response
                        Log.d("TAFIDITRA","Couou");
                        try {
                            String message = response.getString("message");
                            JSONArray data = response.getJSONArray("categories");
                            if(message.equals(ApiURL.CHECK_CODE_OK)){
                                if(data.length() > 0){
                                    for(int i = 0; i<data.length();i++){

                                        Category c = Category.createCategoryFromJsonObject(data.getJSONObject(i));

                                        c.setImage(Integer.toString( CategImgCheck.returnImageCateg(c.getTitre())));
                                        listCategory.add(c);
                                    }
                                    categoryListLiveData.setValue(listCategory);
                                    errorCategory.setValue(null);
                                }

                            }else{
                                categoryListLiveData.setValue(null);
                                errorCategory.setValue("Category Error on message response");
                            }


                        } catch (JSONException e) {
                           categoryListLiveData.setValue(null);
                            Log.d("Tonga fa json exception","Connexion impossible" +e.toString());
                            errorCategory.setValue("Error category "+e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        categoryListLiveData.setValue(null);
                        errorCategory.setValue("Category error api"+error.toString());
                        Log.d("API response error","API response error " +error.toString());
                    }
                }
        ){
            @Override
            public RetryPolicy getRetryPolicy() {
                return new DefaultRetryPolicy(socketTimeout, maxRetries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void fetchTourism(RequestQueue requestQueue,RecyclerViewInterface interfaceview) {
        List<Tourism> listTourism = new ArrayList<>();
        JsonObjectRequest  jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, ApiURL.URL_TOURISM_ALL, null  ,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response
                        Log.d("TAFIDITRA","Couou");
                        try {
                            String message = response.getString("message");
                            JSONArray data = response.getJSONArray("tourismes");
                            if(message.equals(ApiURL.CHECK_CODE_OK)){
                                if(data.length() > 0){
                                    for(int i = 0; i<data.length();i++){

                                        Tourism t = Tourism.createTourismFromJsonObject(data.getJSONObject(i));

                                        listTourism.add(t);
                                    }
                                    tourismListLiveData.setValue(listTourism);
                                    errorTourism.setValue(null);
                                }else{
                                    tourismListLiveData.setValue(null);
                                    errorTourism.setValue("Pas d'activités touristique en vue");
                                }
                            }else{

                               tourismListLiveData.setValue(null);
                                errorTourism.setValue("Tourisme API checkcode NOT OK");
                            }


                        } catch (JSONException e) {
//                            ((IWebServiceCateg)context).getResponseCateg(null,"Connexion impossible" +e.toString(),context);
                            tourismListLiveData.setValue(null);
                            errorTourism.setValue("Tourisme API JSON excpetion"+e.getMessage());
                            Log.d("Tonga fa json exception","Connexion impossible" +e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tourismListLiveData.setValue(null);
                        errorTourism.setValue("Tourisme API JSON excpetion"+error.getMessage());
                        Log.d("API response tourism error","API response error " +error.toString());
                    }
                }
        ){
            @Override
            public RetryPolicy getRetryPolicy() {
                return new DefaultRetryPolicy(socketTimeout, maxRetries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public Utilisateur getUserFromPreferences(Context context){
        Utilisateur utilisateur = null;
        Gson gson = new Gson();
       SharedPreferences sharedPrefs = context.getSharedPreferences("APP_MADATOUR",context.MODE_PRIVATE);
        if(sharedPrefs != null){
            if( sharedPrefs.contains("USER_DETAILS")){
                String savedJson =  sharedPrefs.getString("USER_DETAILS","");
                 utilisateur = gson.fromJson(savedJson, Utilisateur.class);
            }
        }

        return utilisateur;
    }

    public void searchTourism(String texte, RequestQueue requestQueue, RecyclerViewInterface interfaceview) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("texte", texte);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        List<Tourism> listTourism = new ArrayList<>();
        String bindData= null;
        try {
            bindData = "?texte="+ URLEncoder.encode(texte, "UTF-8");
            Log.d("API data ", bindData);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, ApiURL.URL_TOURISM_SEARCH+bindData, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response
                        Log.d("TAFIDITRA", "Couou");
                        try {
                            String message = response.getString("message");
                            JSONArray data = response.getJSONArray("tourismes");
                            if (message.equals(ApiURL.CHECK_CODE_OK)) {
                                if (data.length() > 0) {
                                    for (int i = 0; i < data.length(); i++) {
                                        Tourism t = Tourism.createTourismFromJsonObject(data.getJSONObject(i));
                                        listTourism.add(t);
                                    }
                                    Log.d("API search ", String.valueOf(listTourism.size()));
                                    tourismListLiveData.setValue(listTourism);
                                    errorTourism.setValue(null);
                                } else {
                                    tourismListLiveData.setValue(null);
                                    errorTourism.setValue("Pas d'activités touristiques en vue");
                                }
                            } else {
                                tourismListLiveData.setValue(null);
                                errorTourism.setValue("Tourisme API checkcode NOT OK");
                            }
                        } catch (JSONException e) {
                            tourismListLiveData.setValue(null);
                            errorTourism.setValue("Tourisme API JSON exception" + e.getMessage());
                            Log.d("Tonga fa json exception", "Connexion impossible" + e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tourismListLiveData.setValue(null);
                        errorTourism.setValue("Tourisme API JSON exception" + error.getMessage());
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
                params.put("username", texte);
                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(socketTimeout, maxRetries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

}