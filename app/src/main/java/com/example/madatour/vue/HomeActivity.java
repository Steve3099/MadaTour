package com.example.madatour.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.madatour.R;
import com.example.madatour.modele.Utilisateur;
import com.example.madatour.service.IWebService;
import com.example.madatour.service.Server;
import com.example.madatour.service.VolleySingleton;
import com.example.madatour.util.ApiURL;
import com.example.madatour.viewmodel.ViewModelFactoryLogin;
import com.example.madatour.viewmodel.ViewModelLogin;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity  {

    Button callSignup;
    ImageView image;
    TextView logoText,sloganText;
    TextInputLayout mail,password;
    Utilisateur loogedUtilisateur;

    RequestQueue requestQueue;

    Button login_btn;

    private ViewModelLogin viewModel;

    private ProgressBar loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide navigation bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_home);
        viewModel = new ViewModelProvider(this, new ViewModelFactoryLogin(this)).get(ViewModelLogin.class);


        // Hook
        callSignup = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        mail = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        loader = findViewById(R.id.loader_login);


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
    private Boolean validateEmail(){
        String val = mail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            mail.setError("Le champ ne peut pas être vide");
            return false;
        }
        else if (!val.matches(emailPattern)){
            mail.setError("Email invalide");
            return false;
        }
        else{
            mail.setError(null);
            mail.setErrorEnabled(false);
            return true;
        }
    }private Boolean validatePassword(){
        String val = password.getEditText().getText().toString();

        if(val.isEmpty() ){
            password.setError("Le champ ne peut pas être vide,ajoutez + 5 caractères");
            return false;
        }
        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void login(View view){
        if(!validateEmail() | !validatePassword() ){
            return;
        }
        String email = mail.getEditText().getText().toString();
        String passwordval = password.getEditText().getText().toString();
        fectcUserFromBackend(email,passwordval);



    }
    private void fectcUserFromBackend(String email,String pass){

        viewModel.isLoading().observe(this, isLoading -> {
            if (isLoading) {
                loader.setVisibility(View.VISIBLE); // Show the loader
            } else {
                loader.setVisibility(View.GONE); // Hide the loader
            }
        });
        viewModel.loginUser(email,pass);

        viewModel.getLoggedUser().observe(this, response -> {

            viewModel.getErrorMessage().observe(this,errormessage ->{
                getResponse(response,"FEVE678979",errormessage);
            });


        });
    }


    public void getResponse(Object responseObject,Object token,String errorMessage) {
//        System.out.println("getResponse");
        if(errorMessage != null){
            Toast.makeText(this,"Impossible de se connecter, erreur "+errorMessage,Toast.LENGTH_LONG).show();
            return;
        }if(responseObject instanceof Utilisateur){
            SharedPreferences preferences = this.getSharedPreferences("APP_MADATOUR",this.MODE_PRIVATE);
            preferences.edit().putString("TOKEN",(String)token).apply();

            Gson gson = new Gson();
            String json = gson.toJson((Utilisateur)responseObject);
            preferences.edit().putString("USER_DETAILS",json).apply();
            Intent  intent = new Intent(HomeActivity.this, DashboardActivity.class);
            startActivity(intent);
        }
    }
}