package com.example.quizapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapplication.Models.CategoryModel;
import com.example.quizapplication.R;
import com.example.quizapplication.databinding.ItemCategoryBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapters extends RecyclerView.Adapter<CategoryAdapters.viewHolder> {
    Context context;
    ArrayList<CategoryModel>list;

    public CategoryAdapters(Context context, ArrayList<CategoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.item_category,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
    CategoryModel model=list.get(position);
    holder.binding.categoryName.setText(model.getCategoryName());
    Picasso.get()
            .load(model.getCategoryImage())
            .placeholder(R.drawable.logo)
            .into(holder.binding.categoryImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding= ItemCategoryBinding.bind(itemView);
        }
    }
}
