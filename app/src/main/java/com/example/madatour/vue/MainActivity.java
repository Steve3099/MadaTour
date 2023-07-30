package com.example.madatour.vue;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import com.example.madatour.R;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Animation topanim;
    ImageView image;

    public static int SPLASH_SCREEN_DURATION = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topanim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        image = findViewById(R.id.imageView2);
        image.setAnimation(topanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent  intent = new Intent(MainActivity.this, HomeActivity.class);

                Pair[] pairs= new Pair[1];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,options.toBundle());

            }
        },SPLASH_SCREEN_DURATION);

    }



}