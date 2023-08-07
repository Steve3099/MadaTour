package com.example.madatour.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.madatour.R;
import com.example.madatour.viewmodel.ViewModelFactorySignUP;
import com.example.madatour.viewmodel.ViewModelInscription;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {


    Button callLogin;
    TextInputLayout regNom,regPrenom,regEmail,regPassword,regConfirmPassword;

    private ProgressBar loader;

    ViewModelInscription viewModel;

    boolean isSubscribed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide navigation barr
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_sign_up);

         viewModel = new ViewModelProvider(this,new ViewModelFactorySignUP(this)).get(ViewModelInscription.class);
        // Hook
        callLogin = findViewById(R.id.call_login);
        regNom = findViewById(R.id.fullname);
        regPrenom = findViewById(R.id.username);
        regEmail = findViewById(R.id.mail);
        regPassword = findViewById(R.id.password);
        regConfirmPassword = findViewById(R.id.confirm_password);
        loader = findViewById(R.id.loader);

        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
                startActivity(intent);

//
            }
        });
    }

    private Boolean validateNom(){
        String val = regNom.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if(val.isEmpty()){
            regNom.setError("Le champ ne peut pas être vide");
            return false;
        }else if (val.length() >=25) {
            regNom.setError("Veuillez ne pas depasser 25 caractères");
            return false;
        }else if (!val.matches(noWhiteSpace)) {
            regNom.setError("Veuillez ne pas ajouter d'epsaces");
            return false;
        }
        else{
            regNom.setError(null);
            regNom.setErrorEnabled(false);
            return true;
        }
    }private Boolean validatePrenom(){
        String val = regPrenom.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if(val.isEmpty()){
            regPrenom.setError("Le champ ne peut pas être vide");
            return false;
        }else if (val.length() >=25) {
            regPrenom.setError("Veuillez ne pas depasser 25 caractères");
            return false;
        }else if (!val.matches(noWhiteSpace)) {
            regPrenom.setError("Veuillez ne pas ajouter d'epsaces");
            return false;
        }
        else{
            regPrenom.setError(null);
            regPrenom.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            regEmail.setError("Le champ ne peut pas être vide");
            return false;
        }
        else if (!val.matches(emailPattern)){
            regEmail.setError("Email invalide");
            return false;
        }
        else{
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        String confirmpass = regConfirmPassword.getEditText().getText().toString();
        if(val.isEmpty() || val.length() <=5){
            regPassword.setError("Le champ ne peut pas être vide,ajoutez + 5 caractères");
            return false;
        }if(confirmpass.isEmpty() || val.length() <=5 ){
        regConfirmPassword.setError("Le champ ne peut pas être vide,ajoutez + 5 caractères");
            return false;
        }
        if(val.equals(confirmpass)) {
            regPassword.setError(null);
            regConfirmPassword.setError(null);
            regPassword.setErrorEnabled(false);
            regConfirmPassword.setErrorEnabled(false);
            return true;
        }else{
            regConfirmPassword.setError("Veuillez bien confirmer le mot de passe");
            return false;
        }
    }
    public void registerUser(View view){

        if(!validateNom() | !validatePrenom() | !validateEmail() | !validatePassword()) {
            return;
        }
        String nom = regNom.getEditText().getText().toString();
        String prenom = regPrenom.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();
        String confirmpass = regConfirmPassword.getEditText().getText().toString();

        viewModel.isLoading().observe(this, isLoading -> {
            if (isLoading) {
                loader.setVisibility(View.VISIBLE); // Show the loader
            } else {
                loader.setVisibility(View.GONE); // Hide the loader
            }
        });
        viewModel.signupUserAPi(nom,prenom,email,password);

        viewModel.getApiResponse().observe(this, response -> {
            if (response == null) {
                isSubscribed = true;
                Toast.makeText(this,"Inscription réussie  ",Toast.LENGTH_LONG).show();
                new Handler().postDelayed(() -> {
                    // Create an Intent to start the target activity
                    Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
                    startActivity(intent);

                    // Finish the current activity to prevent going back to it
                    finish();
                }, 1000); // 2000 milliseconds (1 second) delay

            } else {
                Toast.makeText(this,"Impossible de se d'effectuer l'operation, je réessaye  "+response,Toast.LENGTH_LONG).show();
            }
        });

    }
}