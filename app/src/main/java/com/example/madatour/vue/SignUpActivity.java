package com.example.madatour.vue;

import androidx.appcompat.app.AppCompatActivity;
import com.example.madatour.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {


    Button callLogin;
    TextInputLayout regNom,regPrenom,regEmail,regPassword,regConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide navigation bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_sign_up);

        // Hook
        callLogin = findViewById(R.id.call_login);
        regNom = findViewById(R.id.fullname);
        regPrenom = findViewById(R.id.username);
        regEmail = findViewById(R.id.mail);
        regPassword = findViewById(R.id.password);
        regConfirmPassword = findViewById(R.id.confirm_password);
        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
                startActivity(intent);
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

    }
}