package com.example.madatour.controler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madatour.R;

import java.util.ArrayList;
import java.util.List;

public class DetailImageAdapter extends RecyclerView.Adapter<DetailImageAdapter.DetailImageViewHolder> {

    private List<String> data = new ArrayList<>();



    public void setData(List<String> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DetailImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_img, parent, false);
        return new DetailImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailImageViewHolder holder, int position) {
        String onedata = data.get(position);
        String item = data.get(position);
        holder.imageView.setImageResource(Integer.valueOf(onedata));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class DetailImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        DetailImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.detail_img);
        }
    }

}

