package com.example.madatour.vue.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.madatour.databinding.FragmentParametreBinding;


public class ParametreFragment extends Fragment {

    private FragmentParametreBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ParametreViewModel dashboardViewModel =
                new ViewModelProvider(this).get(ParametreViewModel.class);

        binding = FragmentParametreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}