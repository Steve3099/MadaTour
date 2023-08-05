package com.example.madatour.modele;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

public class Tourism implements Serializable {
    String id,titre,description,categorie,mindesc;
    String image;

    public Tourism(String id, String titre, String description, String image, String categorie) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.image = image;
        this.categorie = categorie;
        this.mindesc = getShortenedWordsAsString(description);
    }

    public static Tourism createTourismFromJsonObject(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("_id");
            String categorie = jsonObject.getString("categorie");
            String titre = jsonObject.getString("titre");
            String description = jsonObject.getString("description");
//            String image = jsonObject.getString("image");

            return new Tourism( id,  titre,  description, "",  categorie);

        } catch (JSONException e) {
            return null;
        }
    }
    public static String getShortenedWordsAsString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return ""; // Return an empty string for null or empty input
        }

        // Split the input string into words
        String[] words = input.split("\\s+");

        // Limit the number of words to 10 or less
        int numWords = Math.min(10, words.length);

        if (numWords < 10) {
            // If the input has fewer than 10 words, return the whole input
            return input;
        }

        // Join the first 'numWords' words into a single string
        String shortenedWords = String.join(" ", Arrays.copyOfRange(words, 0, numWords));

        return shortenedWords;
    }

    public String getMindesc() {
        return mindesc;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
