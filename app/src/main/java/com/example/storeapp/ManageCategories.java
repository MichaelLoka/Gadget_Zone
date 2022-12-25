package com.example.storeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ManageCategories extends AppCompatActivity {
    List<Category> categoryList;
    RecyclerView recyclerView;
    Button add_btn;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_categories);
        myDatabaseHelper MyStore= new myDatabaseHelper(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        //MyStore.clearTable();
        byte[] byteArray = stream.toByteArray();
        MyStore.InsertCategory("Baby",byteArray);
        categoryList= MyStore.Retrive_categories();
        recyclerView= findViewById(R.id.categoryList);
        recyclerView.setAdapter(new CategoryAdapter(MyStore));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        add_btn = (Button) findViewById(R.id.addCategory_btn);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManageCategories.this, AddCategoryActivity.class);
                startActivity(i);

            }
        });

    }

}