package com.example.madatour.vue.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.madatour.R;
import com.example.madatour.controler.CategoryAdapter;
import com.example.madatour.controler.TourismAdapter;

import com.example.madatour.databinding.FragmentHomeBinding;
import com.example.madatour.modele.Category;
import com.example.madatour.modele.Tourism;
import com.example.madatour.util.SpacesItemDecoration;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    RecyclerView featuredTourismRecycler;

    RecyclerView featuredCategoryRecycler;
    RecyclerView.Adapter adapter;
    RecyclerView.Adapter adaptercategory;
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
        featuredTourismRecycler = view.findViewById(R.id.featured_tourism_recycler);
        featuredCategoryRecycler = view.findViewById(R.id.featured_category);
        featuredTourismRecycler();
        featuredCategoryRecycler();
    }

    private void featuredTourismRecycler() {
        featuredTourismRecycler.setHasFixedSize(true);
        featuredTourismRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        featuredTourismRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        ArrayList<Tourism> listTourism = new ArrayList<>();
        listTourism.add(new Tourism("1","Monuments","Les monuments historique à Madagascar",(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("2","Monuments","Les monuments historique à Madagascar",(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",(R.drawable.lemurs),"Monuments"));
        adapter = new TourismAdapter(listTourism);
        featuredTourismRecycler.setAdapter(adapter);
//        Add space in first list
        SpacesItemDecoration decoration = new SpacesItemDecoration(20);
        featuredTourismRecycler.addItemDecoration(decoration);
    }
    private void featuredCategoryRecycler() {
        featuredCategoryRecycler.setHasFixedSize(true);
        featuredCategoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        ArrayList<Category> listCategory = new ArrayList<>();
        listCategory.add(new Category("1","Monuments",String.valueOf(R.drawable.rova)));
        listCategory.add(new Category("1","Monuments",String.valueOf(R.drawable.rova)));
        listCategory.add(new Category("1","Monuments",String.valueOf(R.drawable.rova)));

        adaptercategory = new CategoryAdapter(listCategory);
        featuredCategoryRecycler.setAdapter(adaptercategory);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}