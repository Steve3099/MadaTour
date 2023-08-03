package com.example.madatour.vue;

import androidx.appcompat.app.AppCompatActivity;
import com.example.madatour.R;
import com.example.madatour.modele.Utilisateur;
import com.example.madatour.service.IWebService;
import com.example.madatour.service.Server;
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
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements IWebService {

    Button callSignup;
    ImageView image;
    TextView logoText,sloganText;
    TextInputLayout mail,password;

    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide navigation bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
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
        Server server = new Server(this);
        server.loginWithEmailAndPass(email,passwordval);


    }

    @Override
    public void getResponse(Object responseObject,String errorMessage) {
//        System.out.println("getResponse");
        if(responseObject != null){
            Toast.makeText(this,"Impossible de se connecter, erreur "+errorMessage,Toast.LENGTH_LONG).show();
            return;
        }if(responseObject instanceof Utilisateur){

            Intent  intent = new Intent(HomeActivity.this, DashboardActivity.class);
            startActivity(intent);
        }
    }
}