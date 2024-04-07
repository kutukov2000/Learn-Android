package com.example.firstapp.category;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;


public class CategoryCardViewHolder extends RecyclerView.ViewHolder {
    private TextView categoryName;
    private TextView categoryDescription;
    public CategoryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryName = itemView.findViewById(com.example.firstapp.R.id.categoryName);
        categoryDescription = itemView.findViewById(R.id.categoryDescription);
    }

    public TextView getCategoryName() {
        return categoryName;
    }

    public TextView getCategoryDescription() {
        return categoryDescription;
    }
}
