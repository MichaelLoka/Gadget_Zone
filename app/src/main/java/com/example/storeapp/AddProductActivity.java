package com.example.storeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AddProductActivity extends AppCompatActivity {
    ImageView img;
    EditText txt;
    Button btn;
    String category;
    Bitmap bitmap;
    myDatabaseHelper MyStore;
    Spinner spinner;
    ArrayAdapter arrayAdapter ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
       img= findViewById(R.id.product_imageView);
       MyStore= new myDatabaseHelper(this);
       txt= findViewById(R.id.productName_editText);
        btn= findViewById(R.id.savebtn);

        spinner=(Spinner) findViewById(R.id.dropdownspinner);
        arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,MyStore.getCategoriesNames());
        spinner.setAdapter(arrayAdapter);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,3);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //convert from bitmap to byteArray
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
                byte[] byteArray = stream.toByteArray();
                MyStore.InsertCategory(txt.getText().toString(),byteArray);
                Intent i = new Intent(AddProductActivity.this, ManageProducts.class);
                startActivity(i);
            }
        });
    }



    @Override
      protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
           if(resultCode==RESULT_OK&& data!=null)
           {
               Uri uri = data.getData();
               img.setImageURI(uri);
                //convert from uri to Bitmap
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                   img.setImageBitmap(bitmap);


          } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
           }

    }
}