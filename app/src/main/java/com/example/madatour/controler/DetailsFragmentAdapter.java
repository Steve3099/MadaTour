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

public class DetailsFragmentAdapter extends FragmentStateAdapter {


    public DetailsFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new  DescriptionFragment();
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
