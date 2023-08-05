package com.example.madatour.vue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.madatour.R;
import com.example.madatour.controler.DetailsFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DetailActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Hook
        viewPager2 = findViewById(R.id.viewPagerDetails);
        tabLayout = findViewById(R.id.tablayoutDetails);
        viewPager2.setAdapter(new DetailsFragmentAdapter(this));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.blue_logo));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText("Description");
                                tab.setIcon(getResources().getDrawable(R.drawable.baseline_description_24));
                                break;
                            case 1:
                                tab.setText("Video");
                                tab.setIcon(getResources().getDrawable(R.drawable.baseline_video_library_24));
                                break;
                            case 2:
                                tab.setText("Image");
                                tab.setIcon(getResources().getDrawable(R.drawable.baseline_image_24));
                                break;
                        }
                    }
                });
        tabLayoutMediator.attach();
    }
}
