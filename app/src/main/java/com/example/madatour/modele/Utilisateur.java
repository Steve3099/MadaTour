package com.example.madatour.modele;

import org.json.JSONException;
import org.json.JSONObject;

public class Utilisateur {

    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String login;
    private String mdp;

    public Utilisateur(String nom, String prenom, String email, String login, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.login = login;
        this.mdp = mdp;
    }
    public static Utilisateur createUserFromJsonObject(JSONObject jsonObject){
        try {
            String id = jsonObject.getString("_id");
            String nom = jsonObject.getString("nom");
            String prenom = jsonObject.getString("prenom");
            String email = jsonObject.getString("email");
            String login = "";
            String mdp ="";
            return new Utilisateur( nom,  prenom,  email,  login,  mdp);

        }catch (JSONException e){
            return null;
        }
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
