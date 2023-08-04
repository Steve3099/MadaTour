package com.example.madatour.util;

import com.example.madatour.R;

public  class CategImgCheck {

    public static int returnImageCateg(String titre){
        int retour = R.drawable.rova;

       if(titre.equalsIgnoreCase("Monument")){
           retour = R.drawable.rova;
       }
       else if (titre.equalsIgnoreCase("Site touristique")) {
           retour = R.drawable.sitetouristique;

       }  else if (titre.equalsIgnoreCase("Faune")) {
           retour = R.drawable.faune;

       }  else if (titre.equalsIgnoreCase("Flore")) {
           retour = R.drawable.flore;


       }  else if (titre.equalsIgnoreCase("Plage")) {
           retour = R.drawable.plage;


       }
       return retour;
    }
}
