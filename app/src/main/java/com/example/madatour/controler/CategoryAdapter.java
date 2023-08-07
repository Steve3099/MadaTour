package com.example.madatour.controler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madatour.R;
import com.example.madatour.modele.Category;
import com.example.madatour.modele.Tourism;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{


    ArrayList<Category> listCategory;

    public void setData(List<Category> newData) {
        listCategory.clear();
        listCategory.addAll(newData);
        notifyDataSetChanged();
    }

    public CategoryAdapter(ArrayList<Category> listCategory) {
        this.listCategory = listCategory;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_categorie,parent,false);
        CategoryAdapter.CategoryViewHolder categoryViewHolder = new CategoryAdapter.CategoryViewHolder(view);
       return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = listCategory.get(position);
        holder.image.setImageResource(Integer.valueOf(category.getImage()).intValue());
        holder.title.setText(category.getTitre());
    }



    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            //Hooks
            image = itemView.findViewById(R.id.categ_image);
            title = itemView.findViewById(R.id.categ_name);

        }
    }
}
