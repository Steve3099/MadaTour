package com.example.madatour.vue.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madatour.R;
import com.example.madatour.controler.TourismAdapter;
import com.example.madatour.databinding.FragmentHomeBinding;
import com.example.madatour.modele.Tourism;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    RecyclerView featuredTourismRecycler;
    RecyclerView.Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view =inflater.inflate(R.layout.fragment_home, container,false);
    // Hook
            featuredTourismRecycler = view.findViewById(R.id.featured_tourism_recycler);

        featuredTourismRecycler();
        return root;
    }

    private void featuredTourismRecycler() {
        featuredTourismRecycler.setHasFixedSize(true);
        featuredTourismRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        ArrayList<Tourism> listTourism = new ArrayList<>();
        listTourism.add(new Tourism("1","Monuments","Les monuments historique à Madagascar",(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("2","Monuments","Les monuments historique à Madagascar",(R.drawable.lemurs),"Monuments"));
        listTourism.add(new Tourism("3","Monuments","Les monuments historique à Madagascar",(R.drawable.lemurs),"Monuments"));
        adapter = new TourismAdapter(listTourism);
        featuredTourismRecycler.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}