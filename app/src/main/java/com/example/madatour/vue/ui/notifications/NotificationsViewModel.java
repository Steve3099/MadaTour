package com.example.madatour.vue.ui.notifications;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.madatour.modele.Notification;
import com.example.madatour.service.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<List<Notification>> notificationsLiveData = new MutableLiveData<List<Notification>>();
    private List<Notification> notificationList = new ArrayList<>();
    private RequestQueue requestQueue;

    private Context context;

    public LiveData<List<Notification>> getNotifications() {
        if (notificationList.isEmpty()) {
            fetchNotifications();
        }
        return notificationsLiveData;
    }

    private void fetchNotifications() {
        requestQueue = VolleySingleton.getmInstance(context).getRequestQueue(); // Replace with your Application class

        String url = "YOUR_API_URL"; // Replace with your API URL

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        for (int i = 0; i < response.length(); i++) {
                            // Parse JSON data and create Notification objects
//                                JSONObject jsonObject = response.getJSONObject(i);
//                                String title = jsonObject.getString("title");
//                                String message = jsonObject.getString("message");
//                                String time = jsonObject.getString("time");
                            Notification notification = new Notification("", "", "");
                            notificationList.add(notification);
                        }
                        notificationsLiveData.setValue(notificationList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }


                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}