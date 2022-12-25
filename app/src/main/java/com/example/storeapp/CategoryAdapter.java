package com.example.storeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryCard> {
     List<Category> categoryList;
     myDatabaseHelper myStore ;
    public CategoryAdapter(myDatabaseHelper myStore ) {
        this.myStore= myStore;
        this.categoryList = myStore.Retrive_categories();
    }

    @NonNull
    @Override
    public CategoryCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context==currentScreen
        //ViewHolder==CategoryCard
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorycard,parent,false);
        return new CategoryAdapter.CategoryCard(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCard holder, int position) {
        //items of recyclerview created in veiwholder is put(viewed) here on onBindviewholder
        Category cat = categoryList.get(position) ;
        Bitmap bitmap = BitmapFactory.decodeByteArray(cat.getCategoryImage(), 0,cat.getCategoryImage().length);
        holder.img.setImageBitmap(bitmap);
        holder.txt.setText(cat.getCategoryName());
        holder.Deletefloatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myStore.DeleteCategory(cat.getCategoryName());
            }
        });

        holder.Editfloatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),EditCategoryActivity.class);

                i.putExtra("categoryName",cat.getCategoryName());
                i.putExtra("img", cat.getCategoryImage());
                view.getContext().startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryCard extends RecyclerView.ViewHolder {
        ImageView img ;
        TextView txt;
        FloatingActionButton Editfloatbtn;
        FloatingActionButton Deletefloatbtn;
        public CategoryCard(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.product_imageView);
            txt=itemView.findViewById(R.id.productLabel_textView);
            Editfloatbtn=itemView.findViewById(R.id.editbtn_floatingButton);
            Deletefloatbtn=itemView.findViewById(R.id.deletebtn_floatingButton);

        }
    }
}
