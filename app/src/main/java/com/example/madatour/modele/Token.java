package com.example.madatour.modele;

public class Token {
    private Integer id;
    private Integer utilisateur;
    private String dateValidité;

    public Token(Integer utilisateur, String dateValidité) {
        this.utilisateur = utilisateur;
        this.dateValidité = dateValidité;
    }

    public Integer getUtilisateur() {
        return utilisateur;
    }

    public String getDateValidité() {
        return dateValidité;
    }
}
