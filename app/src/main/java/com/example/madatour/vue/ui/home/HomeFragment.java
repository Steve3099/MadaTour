package com.example.madatour.vue.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import com.example.madatour.modele.Utilisateur;
import com.example.madatour.recycler.RecyclerViewInterface;
import com.example.madatour.service.Server;
import com.example.madatour.service.VolleySingleton;
import com.example.madatour.util.ApiURL;
import com.example.madatour.util.CategImgCheck;
import com.example.madatour.util.SpacesItemDecoration;
import com.example.madatour.viewmodel.ViewModelDashboard;
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
    ViewModelDashboard homeViewModel;

    TextView username;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         homeViewModel =
                new ViewModelProvider(this).get(ViewModelDashboard.class);

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
        username = view.findViewById(R.id.user_name);

        Utilisateur loggedUser = homeViewModel.getUserFromPreferences(view.getContext());
        if(loggedUser !=null){
            username.setText(loggedUser.getNom());
        }else{
            username.setText("Bonjour");
        }

        // Hook for recycler view
        featuredTourismRecycler = view.findViewById(R.id.featured_tourism_recycler);
        featuredCategoryRecycler = view.findViewById(R.id.featured_category);
        SearchView searchView = view.findViewById(R.id.searchview);
        RecyclerViewInterface interfaceview =this ;

        // Category recycler
        featuredCategoryRecycler.setHasFixedSize(true);
        featuredCategoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        adaptercategory = new CategoryAdapter((ArrayList<Category>) listCategory);
        featuredCategoryRecycler.setAdapter(adaptercategory);

        // Tourism recycler
        featuredTourismRecycler.setHasFixedSize(true);
        featuredTourismRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        featuredTourismRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new TourismAdapter(this, (ArrayList<Tourism>) listTourism,context);
        featuredTourismRecycler.setAdapter(adapter);

        SpacesItemDecoration decoration = new SpacesItemDecoration(12);
        featuredTourismRecycler.addItemDecoration(decoration);

        featuredCategoryRecyclerV1();
//        ------------------------
        featuredTourismRecyclerV1();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Trigger search when submit button is pressed
                homeViewModel.searchTourism( query,requestQueue, interfaceview);
                homeViewModel.  getTourismListLiveData().observe(getViewLifecycleOwner(), data -> {
                    homeViewModel.getErrorTourism().observe(getViewLifecycleOwner(),errorCategory ->{
                        if(data !=null){
                            ((TourismAdapter)adapter).setData(data);
                        }else{
                            Toast.makeText(getActivity(),"Erreur getting Category " +errorCategory, Toast.LENGTH_SHORT).show();
                        }
                    });
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Trigger search as text changes
                homeViewModel.searchTourism(newText,requestQueue, interfaceview);
                homeViewModel.  getTourismListLiveData().observe(getViewLifecycleOwner(), data -> {
                    homeViewModel.getErrorTourism().observe(getViewLifecycleOwner(),errorCategory ->{
                        if(data !=null){
                            ((TourismAdapter)adapter).setData(data);
                        }else{
                            Toast.makeText(getActivity(),"Erreur getting Category " +errorCategory, Toast.LENGTH_SHORT).show();
                        }
                    });
                });
                return true;
            }
        });


// ------------------------

    }




    private void featuredCategoryRecyclerV1(){
        homeViewModel.fetchCategories(requestQueue);
        homeViewModel.  getCategoryListLiveData().observe(getViewLifecycleOwner(), data -> {
            homeViewModel.getErrorCategory().observe(getViewLifecycleOwner(),errorCategory ->{
                if(data !=null){
                    ((CategoryAdapter)adaptercategory).setData(data);
                }else{
                    Toast.makeText(getActivity(),"Erreur getting Category " +errorCategory, Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
    private void featuredTourismRecyclerV1(){
      homeViewModel.fetchTourism(requestQueue,this);
        homeViewModel.  getTourismListLiveData().observe(getViewLifecycleOwner(), data -> {
            homeViewModel.getErrorTourism().observe(getViewLifecycleOwner(),errorCategory ->{
                if(data !=null){
                    ((TourismAdapter)adapter).setData(data);
                }else{
                    Toast.makeText(getActivity(),"Erreur getting Category " +errorCategory, Toast.LENGTH_SHORT).show();
                }
            });
        });
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