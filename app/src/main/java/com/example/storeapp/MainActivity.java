package com.example.storeapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.clickMenuItemListener
{
    Button wishlist_btn;
    Button cart_btn;
    List<String> Names;
    List<Integer> images;
    List<String> Price;
    List<Product> Products;
    List<Integer> Avaliabilty;
    RecyclerView datalist;
    Adapter myadapter;
    myDatabaseHelper MyStore = new myDatabaseHelper(this);
    String Phone_Number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Phone_Number = getIntent().getExtras().getString(Login.EXTRA_PHONENUMBER);       //getting phone number from Main Activity


        // Get The Icon Beside The Title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);

        datalist = findViewById(R.id.datalist);


        cart_btn = findViewById(R.id.cartBtn);


        myadapter = new Adapter(this, Names,images,Price,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        datalist.setLayoutManager(gridLayoutManager);
        datalist.setAdapter(myadapter);


        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Cart.class);
                i.putExtra(Login.EXTRA_PHONENUMBER,Phone_Number);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean clickMenuItem(MenuItem menuItem, int position) {
        switch (menuItem.getItemId()) {
            case R.id.cartBtn:
                Toast.makeText(getApplicationContext(),"Item Added to cart " + position,Toast.LENGTH_SHORT).show();
                MyStore.InsertTOCart(Phone_Number,position,50);
                return true;
            default:
                return false;
        }
    }
}