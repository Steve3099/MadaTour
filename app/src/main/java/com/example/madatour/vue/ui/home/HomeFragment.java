package com.example.madatour.vue.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.madatour.R;
import com.example.madatour.controler.CategoryAdapter;
import com.example.madatour.controler.TourismAdapter;

import com.example.madatour.databinding.FragmentHomeBinding;
import com.example.madatour.modele.Category;
import com.example.madatour.modele.Tourism;
import com.example.madatour.recycler.RecyclerViewInterface;
import com.example.madatour.service.Server;
import com.example.madatour.service.VolleySingleton;
import com.example.madatour.util.ApiURL;
import com.example.madatour.util.CategImgCheck;
import com.example.madatour.util.SpacesItemDecoration;
import com.example.madatour.vue.DetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RecyclerViewInterface {

    private FragmentHomeBinding binding;

    RecyclerView featuredTourismRecycler;

    RecyclerView featuredCategoryRecycler;
    RecyclerView.Adapter adapter;
    RecyclerView.Adapter adaptercategory;

    private Context context;
    List<Category> listCategory;
    List<Tourism> listTourism;
    private RequestQueue requestQueue;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        View view =inflater.inflate(R.layout.fragment_home, container,false);
    // Hook


//        featuredTourismRecycler();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        requestQueue = VolleySingleton.getmInstance(view.getContext()).getRequestQueue();
        listCategory = new ArrayList<>();
        listTourism = new ArrayList<>();

        // Hook for recycler view
        featuredTourismRecycler = view.findViewById(R.id.featured_tourism_recycler);
        featuredCategoryRecycler = view.findViewById(R.id.featured_category);


        // Category recycler
        featuredCategoryRecycler.setHasFixedSize(true);
        featuredCategoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));


        // Tourism recycler
        featuredTourismRecycler.setHasFixedSize(true);
        featuredTourismRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        featuredTourismRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        featuredCategoryRecyclerV1();

        featuredTourismRecyclerV1();

    }

    private void featuredTourismRecycler() {
        featuredTourismRecycler.setHasFixedSize(true);
        featuredTourismRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        featuredTourismRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        listTourism.add(new Tourism("1","Monuments","Les monuments historique à Madagascar",String.valueOf(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("2","Monuments","Les monuments historique à Madagascar",String.valueOf(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",String.valueOf(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",String.valueOf(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",String.valueOf(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",String.valueOf(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",String.valueOf(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",String.valueOf(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",String.valueOf(R.drawable.lemurs),"Monuments"));
        adapter = new TourismAdapter(this, (ArrayList<Tourism>) listTourism);
        featuredTourismRecycler.setAdapter(adapter);
//        Add space in first list
        SpacesItemDecoration decoration = new SpacesItemDecoration(20);
        featuredTourismRecycler.addItemDecoration(decoration);
    }


     /*
      Not work
      */
    private void featuredCategoryRecycler(Context ctx) {
        featuredCategoryRecycler.setHasFixedSize(true);
        featuredCategoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        adaptercategory = new CategoryAdapter((ArrayList<Category>) listCategory);
        Log.d("LISTCATEG_LENGTH","list length"+listCategory.size());
        listCategory = (ArrayList<Category>) new Server(ctx).getAllCategorie((CategoryAdapter) adaptercategory);
        featuredCategoryRecycler.setAdapter(adaptercategory);

//        listCategory.add(new Category("1","Monuments",String.valueOf(R.drawable.rova)));
//        listCategory.add(new Category("1","Monuments",String.valueOf(R.drawable.rova)));
//        listCategory.add(new Category("1","Monuments",String.valueOf(R.drawable.rova)));

    }

    private void featuredCategoryRecyclerV1(){
        JsonObjectRequest  jsonObjectRequest = new JsonObjectRequest(
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
                                }
//
//                                ((IWebServiceCateg)context).getResponseCateg(listCategory,null,context);
                            }else{

//                                ((IWebServiceCateg)context).getResponseCateg(null,"Error in json response",context);
                            }
                            adaptercategory = new CategoryAdapter((ArrayList<Category>) listCategory);
                            featuredCategoryRecycler.setAdapter(adaptercategory);

                        } catch (JSONException e) {
//                            ((IWebServiceCateg)context).getResponseCateg(null,"Connexion impossible" +e.toString(),context);
                            Log.d("Tonga fa json exception","Connexion impossible" +e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response
//                        ((IWebServiceCateg)context).getResponseCateg(null,"Connexion impossible" +error.toString(),context);
                        Toast.makeText(getActivity(),"Erreur getting categori " +error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("API response error","API response error " +error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    private void featuredTourismRecyclerV1(){
        RecyclerViewInterface interfaceview = this;
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
                                }
//
//                                ((IWebServiceCateg)context).getResponseCateg(listCategory,null,context);
                            }else{

//                                ((IWebServiceCateg)context).getResponseCateg(null,"Error in json response",context);
                            }
                            adapter = new TourismAdapter(interfaceview, (ArrayList<Tourism>) listTourism,context);
                            featuredTourismRecycler.setAdapter(adapter);
//        Add space in first list
                            SpacesItemDecoration decoration = new SpacesItemDecoration(20);
                            featuredTourismRecycler.addItemDecoration(decoration);

                        } catch (JSONException e) {
//                            ((IWebServiceCateg)context).getResponseCateg(null,"Connexion impossible" +e.toString(),context);
                            Log.d("Tonga fa json exception","Connexion impossible" +e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response
//                        ((IWebServiceCateg)context).getResponseCateg(null,"Connexion impossible" +error.toString(),context);
                        Toast.makeText(getActivity(),"Erreur getting tourism " +error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("API response tourism error","API response error " +error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(context, DetailActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("tourism_id",listTourism.get(position).getId());
        bundle.putString("tourism_title",listTourism.get(position).getTitre());
        bundle.putString("tourism_img",listTourism.get(position).getImage());
        bundle.putString("tourism_desc",listTourism.get(position).getDescription());
        bundle.putString("tourism_categ",listTourism.get(position).getCategorie());

        intent.putExtras(bundle);
        context.startActivity(intent);
//        Fragment frag = new DetailFragment();
//
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.nav_host_fragment_activity_dashboard, frag);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        ft.addToBackStack(null);
//        ft.commit();
    }


}