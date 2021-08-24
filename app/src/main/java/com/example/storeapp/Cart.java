package com.example.storeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity implements Adapter.clickMenuItemListener{


    List<Cursor> cursor;
    List<String> Descriptions;
    List<Integer> images;
    List<String> Price;
    RecyclerView CartDatalist;
    Adapter myAdapter;
    myDatabaseHelper myStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        myStore = new myDatabaseHelper(this);
        Descriptions = new ArrayList<>();
        images = new ArrayList<>();
        Price = new ArrayList<>();

        CartDatalist = findViewById(R.id.CartDataList);

        String Phone_Number = getIntent().getExtras().getString(Login.EXTRA_PHONENUMBER);      //getting phone number from Main Activity
        cursor = myStore.FetchForData(Phone_Number,myDatabaseHelper.EXTRA_CARTTABLE);   //fetching Products from Cart Table


        // assigning Retreived Data to the lists to be added into the Adapter
        int counter = 0;
        while (counter < cursor.size()) {
            if (cursor != null) {
                while (cursor.get(counter).moveToNext()) {
                    Descriptions.add(cursor.get(counter).getString(cursor.get(counter).getColumnIndexOrThrow(myDatabaseHelper.Description_Column)));
                    Price.add(cursor.get(counter).getString(cursor.get(counter).getColumnIndexOrThrow(myDatabaseHelper.Price_Column)));
                    images.add(cursor.get(counter).getInt(cursor.get(counter).getColumnIndexOrThrow(myDatabaseHelper.Image_Column)));


                }
            } else {
                Toast.makeText(getApplicationContext(), "No Data To Show", Toast.LENGTH_LONG).show();
            }
            counter++;
        }
        //Adapter For the Custom View

        myAdapter = new Adapter(this,Descriptions,images,Price,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1,RecyclerView.VERTICAL,false);
        CartDatalist.setLayoutManager(gridLayoutManager);
        CartDatalist.setAdapter(myAdapter);



    }

    @Override
    public boolean clickMenuItem(MenuItem menuItem, int position) {
        return false;
    }
}