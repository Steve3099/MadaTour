package com.example.madatour.modele;

public class Category {

    String id,titre,image;

    public Category(String id, String titre, String image) {
        this.id = id;
        this.titre = titre;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
