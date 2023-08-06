package com.example.madatour.vue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.madatour.R;
import com.example.madatour.controler.DetailImageAdapter;
import com.example.madatour.vue.ui.detail.DetailViewModel;

import java.util.List;


public class ImageFragment extends Fragment {

    List<String> img;

    private DetailViewModel viewModel;

    private DetailImageAdapter detailsFragmentAdapter;

    private RecyclerView recyclerViewImage;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        viewModel.fetchDataFromApi();
//
;

        return inflater.inflate(R.layout.fragment_image, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ////        ---------------------------------
        recyclerViewImage =view.findViewById(R.id.details_image_list);
        detailsFragmentAdapter = new DetailImageAdapter();
//
        recyclerViewImage.setHasFixedSize(true);
        recyclerViewImage.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerViewImage.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewImage.setAdapter(detailsFragmentAdapter);
        viewModel.getApiResponse().observe(getViewLifecycleOwner(), data -> {
            detailsFragmentAdapter.setData(data); // Update adapter data when API response changes
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }
}