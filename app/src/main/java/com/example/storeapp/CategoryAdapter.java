package com.example.storeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storeapp.Model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<Category> categories;
    private Context mContext;

    private final CategoryAdapter.AdapterOnClickHandler ClickHandler;

    public interface AdapterOnClickHandler {
        void onCategoryClicked(int position);
    }


    public CategoryAdapter(Context mContext,ArrayList<Category> categories,CategoryAdapter.AdapterOnClickHandler clicklistener) {
        this.mContext = mContext;
        this.categories = categories;
        this.ClickHandler = clicklistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Category category_item = categories.get(position);
        holder.CategoryImage.setImageDrawable(this.mContext.getResources().getDrawable(category_item.getPhoto_id()));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView CategoryImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoryImage = itemView.findViewById(R.id.category_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ClickHandler.onCategoryClicked(getAdapterPosition());
        }
    }

}
