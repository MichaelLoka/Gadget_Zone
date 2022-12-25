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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ManageProducts extends AppCompatActivity {
    List<Product> productList;
    RecyclerView recyclerView;
    Button add_btn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);

        myDatabaseHelper MyStore= new myDatabaseHelper(this);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.babywipes);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] byteArray = stream.toByteArray();
        productList= MyStore.Retrive_Products();
        recyclerView= findViewById(R.id.productList);
        recyclerView.setAdapter(new ProductAdapter(MyStore));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        add_btn = (Button) findViewById(R.id.addProduct_btn);

        add_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(ManageProducts.this, AddCategoryActivity.class);
                 startActivity(i);
             }


      });

    }
}

