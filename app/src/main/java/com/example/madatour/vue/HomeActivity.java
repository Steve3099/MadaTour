package com.example.madatour.vue;

import androidx.appcompat.app.AppCompatActivity;
import com.example.madatour.R;
import com.google.android.material.textfield.TextInputLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    Button callSignup;
    ImageView image;
    TextView logoText,sloganText;
    TextInputLayout mail,password;

    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        // Hook
        callSignup = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        mail = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);


        callSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,SignUpActivity.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(logoText,"logo_text");
                pairs[2] = new Pair<View,String>(sloganText,"logo_desc");
                pairs[3] = new Pair<View,String>(mail,"mail_trans");
                pairs[4] = new Pair<View,String>(password,"password_trans");
                pairs[5] = new Pair<View,String>(login_btn,"button_trans");
                pairs[6] = new Pair<View,String>(callSignup,"login_sing_trans");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this,pairs);

                startActivity(intent,options.toBundle());
            }
        });
    }
}