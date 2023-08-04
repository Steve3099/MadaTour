package com.example.madatour.modele;

import org.json.JSONException;
import org.json.JSONObject;

public class Category {

    String id,titre,image;

    public Category(String id, String titre, String image) {
        this.id = id;
        this.titre = titre;
        this.image = image;
    }
    public static Category createCategoryFromJsonObject(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("_id");
            String titre = jsonObject.getString("label");
            String image = jsonObject.getString("icon");

            return new Category(id, titre, image);

        } catch (JSONException e) {
            return null;
        }
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
