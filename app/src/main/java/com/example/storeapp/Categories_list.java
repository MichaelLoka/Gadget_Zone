package com.example.storeapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.storeapp.Model.Category;

import java.util.ArrayList;

public class Categories_list extends AppCompatActivity implements CategoryAdapter.AdapterOnClickHandler {

    myDatabaseHelper MyStore;

    @Override
    public void onCategoryClicked(int position) {
        Intent i = new Intent(this,MainActivity.class);
        i.putExtra("Category_selected",Categories.get(position).getCat_Name());
        startActivity(i);
    }

    ArrayList<Category> Categories;
    RecyclerView category_list;
    CategoryAdapter adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);

        MyStore = new myDatabaseHelper(this);
        Categories = new ArrayList<Category>();
        category_list = findViewById(R.id.category_list);

        // Get The Icon Beside The Title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);

        Cursor Cat_cursor = MyStore.Retrieve_All_Category();

        while (!Cat_cursor.isAfterLast()) {
            Categories.add(new Category(Cat_cursor.getString(0),
                    Cat_cursor.getInt(1)));

            Cat_cursor.moveToNext();
        }
        adpater = new CategoryAdapter(this,Categories,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1,RecyclerView.VERTICAL,false);
        category_list.setLayoutManager(gridLayoutManager);
        category_list.setAdapter(adpater);


    }
}