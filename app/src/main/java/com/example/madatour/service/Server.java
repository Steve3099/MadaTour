package com.example.madatour.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.madatour.modele.Utilisateur;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class Server implements IServer{

    public static  String BASE_URL = "https://428f-197-149-43-122.ngrok.io";
    public static  String URL_LOGIN = BASE_URL +"/utilisateur/login";
    Context context;
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;
    private  Utilisateur loogedUtilisateur;
    public static final String TAG = "Web services";
    public static final String CHECK_CODE_OK = "mety";
//
//    public static String getBaseURL(){
//        BASE_URL =  context.getString(R.string.local_API);
//        return BASE_URL;
//    }
//
    public Server(Context context) {
        this.context = context;
    }

    @Override
    public void loginWithEmailAndPass(String email, String pass) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("login", email);
            postData.put("mdp", pass);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        requestQueue = Volley.newRequestQueue(context);
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,URL_LOGIN, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response
                        Log.d("TAFIDITRA","Couou");
                        try {
                            String message = response.getString("message");
                            String token = response.getJSONObject("token").getString("insertedId");
                            JSONObject data = response.getJSONObject("data");
                            if(message.equals(CHECK_CODE_OK)){
                                loogedUtilisateur = Utilisateur.createUserFromJsonObject(data);
                                ((IWebService)context).getResponse(loogedUtilisateur,token,null);
                            }else{
                                loogedUtilisateur = null;
                                ((IWebService)context).getResponse(loogedUtilisateur,null,message);
                            }

                        } catch (JSONException e) {
                            loogedUtilisateur = null;
                            ((IWebService)context).getResponse(loogedUtilisateur,null,"Connexion impossible, Erreur "+e.toString());
//                            Log.d(TAG,"Connexion impossible");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response
                        loogedUtilisateur = null;
                        ((IWebService)context).getResponse(loogedUtilisateur,null,"Connexion impossible, Erreur "+error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
