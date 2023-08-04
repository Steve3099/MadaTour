package com.example.madatour.service;

import com.example.madatour.controler.CategoryAdapter;
import com.example.madatour.modele.Category;

import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
// -------------------------------------------------------------------------------------------------------------------------------------------------------------
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!
//  TSY MIASA TSONY TY AH !!!!!!


public interface IServer {
    public void loginWithEmailAndPass(String email,String pass);

    public List<Category> getAllCategorie(CategoryAdapter adapter);

}
