package com.example.madatour.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.madatour.modele.Utilisateur;
import com.example.madatour.service.VolleySingleton;
import com.example.madatour.util.ApiURL;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewModelLogin extends ViewModel {

    private MutableLiveData<Utilisateur> loggedInUser = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private RequestQueue requestQueue;

    Context context;

    public ViewModelLogin(Context context) {
        this.context = context;
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }
    public LiveData<Utilisateur> getLoggedUser() {
        return loggedInUser;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public static int socketTimeout = 7000; // 7 seconds timeout
    public static int maxRetries = 3;





    public void loginUser(String email, String password) {
        loading.setValue(true);
        requestQueue = VolleySingleton.getmInstance(context).getRequestQueue();

        JSONObject postData = new JSONObject();
        try {
            postData.put("login", email);
            postData.put("mdp", password);
        } catch (JSONException  e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, ApiURL.URL_LOGIN, postData,
                response -> {
                    // Handle the successful response
                    try {
                        String message = response.getString("message");
                        JSONObject data = response.getJSONObject("data");
                        if (message.equals(ApiURL.CHECK_CODE_OK)) {
                            Utilisateur user = Utilisateur.createUserFromJsonObject(data);
                            loggedInUser.setValue(user);
                            errorMessage.setValue(null);
                        } else {
                            loggedInUser.setValue(null);
                            errorMessage.setValue(message);
                        }
                    } catch (JSONException e) {
                        loggedInUser.setValue(null);
                        errorMessage.setValue(e.getMessage());

                        errorMessage.setValue("Connexion impossible, Erreur " + e.toString());
                    }finally {
                        loading.setValue(false);
                    }
                },
                error -> {
                    // Handle the error response
                    loggedInUser.setValue(null);
                    errorMessage.setValue(error.getMessage());
                    errorMessage.setValue("Connexion impossible, Erreur " + error.toString());
                    loading.setValue(false);
                }
        );

        // Set a custom retry policy for timeout errors
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                socketTimeout, maxRetries,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        requestQueue.add(jsonObjectRequest);
    }
}