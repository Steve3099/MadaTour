package com.example.madatour.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.madatour.controler.CategoryAdapter;
import com.example.madatour.modele.Category;
import com.example.madatour.modele.Utilisateur;
import com.example.madatour.util.CategImgCheck;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!

public class Server implements IServer{

    public static  String BASE_URL = "https://2fed-197-149-18-224.ngrok.io";
    public static  String URL_LOGIN = BASE_URL +"/utilisateur/login";
    public static  String URL_CATEGORY = BASE_URL +"/categorie/all";
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
        requestQueue = Volley.newRequestQueue(context);
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

//        requestQueue = Volley.newRequestQueue(context);
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,URL_LOGIN, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response
                        try {
                            Log.d("TAFIDITRA","Couou");

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

    @Override
    public List<Category> getAllCategorie(CategoryAdapter adapter) {
        List<Category> listcateg = new ArrayList<>();
//        requestQueue = Volley.newRequestQueue(context);
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,URL_CATEGORY, null  ,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response
                        Log.d("TAFIDITRA","Couou");
                        try {
                            String message = response.getString("message");
                            JSONArray data = response.getJSONArray("categories");
                            if(message.equals(CHECK_CODE_OK)){
                                if(data.length() > 0){
                                    for(int i = 0; i<data.length();i++){

                                        Category c = Category.createCategoryFromJsonObject(data.getJSONObject(i));
                                        c.setImage(Integer.toString( CategImgCheck.returnImageCateg(c.getImage())));
                                        listcateg.add(c);
                                    }
                                }
//
                                ((IWebServiceCateg)context).getResponseCateg(listcateg,null,context);
                            }else{

                                ((IWebServiceCateg)context).getResponseCateg(null,"Error in json response",context);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            ((IWebServiceCateg)context).getResponseCateg(null,"Connexion impossible" +e.toString(),context);
                            Log.d("Tonga fa json exception","Connexion impossible" +e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response
                        ((IWebServiceCateg)context).getResponseCateg(null,"Connexion impossible" +error.toString(),context);
                        Log.d("API response error","API response error " +error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
        return listcateg;
    }
}
