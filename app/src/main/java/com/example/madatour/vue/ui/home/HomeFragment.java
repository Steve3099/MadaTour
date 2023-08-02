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
import com.example.madatour.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    RecyclerView featuredTourismRecycler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
    // Hook
        featuredTourismRecycler = getView().findViewById(R.id.featured_tourism_recycler);

        featuredTourismRecycler();
        return root;
    }

    private void featuredTourismRecycler() {
        featuredTourismRecycler.setHasFixedSize(true);
        featuredTourismRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}