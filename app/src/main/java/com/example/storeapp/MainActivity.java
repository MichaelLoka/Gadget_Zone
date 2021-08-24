package com.example.storeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.clickMenuItemListener
{
    Button wishlist_btn;
    Button cart_btn;
    List<String> Descriptions;
    List<Integer> images;
    List<String> Price;
    List<Product> Products;
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


        wishlist_btn = findViewById(R.id.wishBtn);
        cart_btn = findViewById(R.id.cartBtn);
        Descriptions = new ArrayList<>();
        images = new ArrayList<>();
        Price = new ArrayList<>();
        Products = new ArrayList<>();


        Descriptions.add("IPhone XS");
        Descriptions.add("Samsung A50");
        Descriptions.add("Laptop Dell G3");
        Descriptions.add("Laptop Dell G5");
        Descriptions.add("Apple Airpods");
        Descriptions.add("PS4 Controller");
        Descriptions.add("Lenovo Tablet P11 Pro");
        Descriptions.add("Lenovo Ideapad Gaming 3");
        Descriptions.add("Intel Core i7 7700K");
        Descriptions.add("Geforce GTX 1660Ti");

        images.add(R.drawable.iphone_xs);
        images.add(R.drawable.samsung_a50);
        images.add(R.drawable.dell_g3);
        images.add(R.drawable.dell_g5);
        images.add(R.drawable.airpods);
        images.add(R.drawable.ps4_controller);
        images.add(R.drawable.lenovo_p11_pro);
        images.add(R.drawable.ideapad_g3);
        images.add(R.drawable.i7);
        images.add(R.drawable.gtx);

        Price.add("$11000");
        Price.add("$5500");
        Price.add("$13000");
        Price.add("$17000");
        Price.add("3500");
        Price.add("$900");
        Price.add("$6000");
        Price.add("$17000");
        Price.add("$7500");
        Price.add("6600");

        for (int i = 0 ; i < Descriptions.size();i++){
            Products.add(new Product(Descriptions.get(i),Price.get(i),images.get(i)));
            MyStore.InsertProduct(Products.get(i),i);
        }


        myadapter = new Adapter(this,Descriptions,images,Price,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        datalist.setLayoutManager(gridLayoutManager);
        datalist.setAdapter(myadapter);


        wishlist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,WishList.class);
                i.putExtra(Login.EXTRA_PHONENUMBER,Phone_Number);
                startActivity(i);
            }
        });

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
                MyStore.InsertTO(Phone_Number,position,myDatabaseHelper.EXTRA_CARTTABLE);
                return true;
            case R.id.wishBtn:
                Toast.makeText(getApplicationContext(),"Item Added to WishList " + position,Toast.LENGTH_SHORT).show();
                MyStore.InsertTO(Phone_Number,position,myDatabaseHelper.EXTRA_WISHLISTTABLE);
                return true;
            default:
                return false;
        }
    }
}