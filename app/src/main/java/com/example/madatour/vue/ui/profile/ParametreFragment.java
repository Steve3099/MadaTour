package com.example.madatour.vue.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.madatour.R;
import com.example.madatour.databinding.FragmentParametreBinding;
import com.example.madatour.vue.HomeActivity;


public class ParametreFragment extends Fragment {

    private FragmentParametreBinding binding;
    Button login_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ParametreViewModel dashboardViewModel =
                new ViewModelProvider(this).get(ParametreViewModel.class);

        binding = FragmentParametreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        login_btn = view.findViewById(R.id.logoutButton);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("APP_MADATOUR", view.getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(getActivity(), HomeActivity.class);

                // Start the new Activity
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}