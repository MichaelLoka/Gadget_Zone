package com.example.storeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditCategoryActivity extends AppCompatActivity {
    ImageView img;
    EditText txt;
    Button btn;
    String category;
    Bitmap bitmap;
    myDatabaseHelper MyStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        img= findViewById(R.id.product_imageView);
        MyStore= new myDatabaseHelper(this);

        byte[]imgbyteArray = getIntent().getByteArrayExtra("img");
        //convert from byteArray to Bitmap
         bitmap = BitmapFactory.decodeByteArray(imgbyteArray, 0,imgbyteArray.length);
        img.setImageBitmap(bitmap);

        txt= findViewById(R.id.categoryLabel_editText);
        btn= findViewById(R.id.savebtn);
        category=getIntent().getStringExtra("categoryName");
        txt.setText(category);


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
                MyStore.EditCategory(category,txt.getText().toString(),byteArray);
                Intent i = new Intent(EditCategoryActivity.this, ManageCategories.class);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            //convert from Bitmap to ByteArray To Store in DB
        }
    }
}