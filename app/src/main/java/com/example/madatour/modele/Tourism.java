package com.example.madatour.modele;

public class Tourism {
    String id,titre,description;
    Integer categorie,image;

    public Tourism(String id, String titre, String description, Integer image, Integer categorie) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.image = image;
        this.categorie = categorie;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Integer getCategorie() {
        return categorie;
    }

    public void setCategorie(Integer categorie) {
        this.categorie = categorie;
    }
}
