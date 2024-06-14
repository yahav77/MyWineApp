package com.example.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listeners.OnItemClickListener;
import com.example.new1.R;
import com.example.models.Wine;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WineAdapter extends RecyclerView.Adapter<WineAdapter.ViewHolder> {


    private final List<Wine> wines;
    private final OnItemClickListener listener;

    public WineAdapter(List<Wine> wines, OnItemClickListener listener) {
        this.wines = wines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.wine_item, parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Wine wine = wines.get(position);
        holder.wineNameTv.setText(wine.getName());
        holder.winePriceTv.setText(wine.getPrice() + "₪");
        holder.wineMlTv.setText(wine.getMl() + " " + "מל");
        Picasso.get().load(wine.getImage()).into(holder.wineIv);
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return wines.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView wineNameTv, winePriceTv,wineMlTv;
        private ImageView wineIv, addBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.wineIv = itemView.findViewById(R.id.wineImageView);
            this.wineNameTv = itemView.findViewById(R.id.wineNameTv);
            this.winePriceTv = itemView.findViewById(R.id.winePriceTv);
            this.wineMlTv = itemView.findViewById(R.id.wineMlTv);
            this.addBtn = itemView.findViewById(R.id.addWineBtn);
        }
    }
}
