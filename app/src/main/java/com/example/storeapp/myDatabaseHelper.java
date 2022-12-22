package com.example.storeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class myDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASENAME = "Store.db";

    private static final String CART_TABLE = "Cart";
    public static final  String EXTRA_CARTTABLE = CART_TABLE;
    private static final String TAG = "My message";

    public myDatabaseHelper(@Nullable Context context) {
        super(context, DATABASENAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        //Categories table
        sqLiteDatabase.execSQL("CREATE TABLE Categories (category text PRIMARY KEY not null,photo Integer Not null)");

        ContentValues row1 = new ContentValues();
        row1.put("category", "Electronics");
        row1.put("photo",R.drawable.electronics_category);
        sqLiteDatabase.insert("Categories ", null, row1);

        //product table
        sqLiteDatabase.execSQL("create table products (product_id integer primary key autoincrement" +
                ",name text not null,quantity integer , price integer not null , photo integer not null,category text not null," +
                "FOREIGN KEY (category) references categories(category));");

        ContentValues row2 = new ContentValues();
        row2.put("name", "airpods");
        row2.put("quantity", 10);
        row2.put("price", 500);
        row2.put("category","Electronics");
        row2.put("photo",R.drawable.airpods);
        sqLiteDatabase.insert("products ", null, row2);

        row2.put("name", "dell_g3");
        row2.put("quantity", 20);
        row2.put("price", 5000);
        row2.put("category","Electronics");
        row2.put("photo",R.drawable.dell_g3);
        sqLiteDatabase.insert("products ", null, row2);

        row2.put("name", "iphone xs");
        row2.put("quantity", 50);
        row2.put("price", 8000);
        row2.put("category","Electronics");
        row2.put("photo",R.drawable.iphone_xs);
        sqLiteDatabase.insert("products ", null, row2);


        //Cart table
        sqLiteDatabase.execSQL("create table cart (User_phoneNum integer not null," +
                "product_id integer Not null,quantity integer not null," +
                "FOREIGN KEY (product_id) references products(product_id)," +
                "PRIMARY KEY (product_id,user_id));");


        //transaction table
        sqLiteDatabase.execSQL("CREATE TABLE Transactions (trans_id Interger primary key autoincrement," +
                "product_id integer not null, user_id integer not null," +
                "quantity integer not null," +
                "date Date not null," +
                "FOREIGN KEY (product_id) references products(product_id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS products");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CART_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS categories");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Transactions");
        onCreate(sqLiteDatabase);
    }

    public void InsertTOCart(String PhoneNumber, int ID_Product, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("User_phoneNum",PhoneNumber);
        row.put("product_id",ID_Product);
        row.put("quantity",quantity)

        // insert into Table that we choose whether be Wish list Table or Cart Table
        db.insert("CART",null,row);
        db.close();
    }

    public void InsertProduct(Product product,int ID_Product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();

        row.put("name",product.getName());
        row.put("quantity",product.getQuantity());
        row.put("price",product.getPrice());
        row.put("category",product.getCategory());
        row.put("photo",product.getImange_id());

        db.insert("products",null,row);
        db.close();
    }

    public void InsertCategory(String Categorey_name,int image_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("category",Categorey_name);
        row.put("photo",image_id);

        db.insert("Categories",null,row);
        db.close();
    }

    public List<Cursor> FetchForData(String PhoneUser, String Table){
        SQLiteDatabase db = this.getReadableDatabase();
        String []UserPhone = {PhoneUser};
        String selection =  "product_id" + " = ?";

        //fetching ProductsIDs from the Table variable where phoneUser
        Cursor cursor_idPRODUCT = db.rawQuery("SELECT product_id FROM cart WHERE  LIKE ?",UserPhone);


        String[] productsID = new String[cursor_idPRODUCT.getCount()]; //setting a size for the productsIDs Array

        //assigning the cursor of ProductsIDs to the ProductsIDs Array
        cursor_idPRODUCT.moveToFirst();
        for (int counter = 0; counter < cursor_idPRODUCT.getCount();counter++){
            productsID[counter] = cursor_idPRODUCT.getString(
                    cursor_idPRODUCT.getColumnIndexOrThrow(IDPRODUCT_Column));
            cursor_idPRODUCT.moveToNext();
        }


        //making a list of cursor for each retrived product
        List<Cursor> cursors = new ArrayList<Cursor>();

        //fetching the Products where
        for (int counter = 0; counter < cursor_idPRODUCT.getCount();counter++){
            cursors.add(db.query(PRODUCTS_TABLE, null, selection, new String[]{productsID[counter]}, null, null, null));
        }

        return cursors;
    }
}
