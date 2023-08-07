package com.example.madatour.controler;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.madatour.R;
import com.example.madatour.modele.Category;
import com.example.madatour.modele.Tourism;
import com.example.madatour.recycler.RecyclerViewInterface;

import java.util.ArrayList;
import java.util.List;

public class TourismAdapter extends RecyclerView.Adapter<TourismAdapter.TourismViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    ArrayList<Tourism> listTourism;
    private Context context;

    public void setData(List<Tourism> newData) {
        listTourism.clear();
        listTourism.addAll(newData);
        notifyDataSetChanged();
    }


    public TourismAdapter(RecyclerViewInterface recyclerViewInterface,  ArrayList<Tourism> listTourism) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.listTourism = listTourism;
    }
    public TourismAdapter(RecyclerViewInterface recyclerViewInterface,  ArrayList<Tourism> listTourism,Context context) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.listTourism = listTourism;
        this.context = context;
    }

    @NonNull
    @Override
    public TourismViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_tourism_card_design,parent,false);
        TourismViewHolder tourismViewHolder = new TourismViewHolder(view,recyclerViewInterface);
        return tourismViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TourismViewHolder holder, int position) {

        Tourism tourism = listTourism.get(position);

        holder.title.setText(tourism.getTitre());
        holder.desc.setText(tourism.getMindesc());
//        holder.image.setImageResource(Integer.parseInt(Integer.valueOf(tourism.getImage()).toString()));
        Glide.with(context).load(tourism.getImage()).into(holder.image);
    }




    @Override
    public int getItemCount() {
        return listTourism.size();
    }

    public static class TourismViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title,desc;
        public TourismViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            //Hooks
            image = itemView.findViewById(R.id.featured_tourism_img);
            title = itemView.findViewById(R.id.featured_tourism_title);
            desc = itemView.findViewById(R.id.featured_tourism_desc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }


}
