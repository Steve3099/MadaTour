package com.example.madatour.controler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.madatour.vue.DescriptionFragment;
import com.example.madatour.vue.ImageFragment;
import com.example.madatour.vue.VideoFragment;

import java.util.List;

public class DetailsFragmentAdapter extends FragmentStateAdapter {

    String description;
    List<String> img;
    List<String> video;
    public DetailsFragmentAdapter(@NonNull FragmentActivity fragmentActivity,String description,List<String> img,List<String> video) {
        super(fragmentActivity);
        this.description = description;
        this.img = img;
        this.video = video;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                DescriptionFragment descriptionFragment = new DescriptionFragment();
                descriptionFragment.setDescprition(description);
                return descriptionFragment;
            case 1:
                return new VideoFragment();
            default:
                return new ImageFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
