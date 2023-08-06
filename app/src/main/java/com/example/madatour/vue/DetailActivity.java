package com.example.madatour.vue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madatour.R;
import com.example.madatour.controler.DetailImageAdapter;
import com.example.madatour.controler.DetailsFragmentAdapter;
import com.example.madatour.modele.Tourism;
import com.example.madatour.vue.ui.detail.DetailViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    TabLayout tabLayout;

    ImageView backButton,poster_image;

    TextView detailtitle;
    Tourism tourim;

    List<String> fetchedImage;
    List<String> fetchedVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Hook
        fetchedImage = new ArrayList<>();
        fetchedVideo = new ArrayList<>();

        //----------------------------------

        dataFromDashboardActiviy();
        initializeHook();
        tablayoutRedirect();
        backButton();
        poster_image.setImageResource(Integer.valueOf(tourim.getImage()));
        detailtitle.setText(tourim.getTitre());
    }

    public void initializeHook(){
        viewPager2 = findViewById(R.id.viewPagerDetails);
        tabLayout = findViewById(R.id.tablayoutDetails);
        detailtitle = findViewById(R.id.detail_title);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.blue_logo));
        viewPager2.setAdapter(new DetailsFragmentAdapter(this,tourim.getDescription(),fetchedImage,fetchedVideo));
        backButton = findViewById(R.id.imageViewBackButton);
        poster_image = findViewById(R.id.poster_image);
    }

    public void tablayoutRedirect(){

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
                                tab.setText("Image");
                                tab.setIcon(getResources().getDrawable(R.drawable.baseline_image_24));
                                break;
                            case 2:
                                tab.setText("Video");
                                tab.setIcon(getResources().getDrawable(R.drawable.baseline_video_library_24));
                                break;
                        }
                    }
                });
        tabLayoutMediator.attach();
    }
    public void backButton(){
        backButton.setOnClickListener(view -> {
          finish();
        });
    }

    public void dataFromDashboardActiviy(){
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("tourism_id");
        String titre = bundle.getString("tourism_title");
        String image = bundle.getString("tourism_img");
        String description = bundle.getString("tourism_desc");
        String categorie = bundle.getString("tourism_categ");
        tourim = new Tourism( id,  titre,  description,  image,  categorie);


    }


}
