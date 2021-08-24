package com.example.storeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

public class myDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASENAME = "Store.db";
    private static final String PRODUCTS_TABLE = "Products";
    private static final String IDPRODUCT_Column = "PRODUCT_ID";
    public static final String Description_Column = "Description";
    public static final String Price_Column = "Price";
    public static final String Image_Column = "Image_ID";
    private static final String WISHLIST_TABLE = "Wishlist";
    private static final String PhoneUser_Column = "Phone_User";
    private static final String CART_TABLE = "Cart";

    public static final  String EXTRA_WISHLISTTABLE = WISHLIST_TABLE;
    public static final  String EXTRA_CARTTABLE = CART_TABLE;
    private static final String TAG = "My message";

    public myDatabaseHelper(@Nullable Context context) {
        super(context, DATABASENAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ PRODUCTS_TABLE +" (" + //  Products table
                IDPRODUCT_Column +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Description_Column + " TEXT NOT NULL, " +
                Price_Column + " FLOAT NOT NULL, " +
                Image_Column +" INTEGER NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE "+ WISHLIST_TABLE +" (" +  //  add to wish list table
                IDPRODUCT_Column +" INTEGER , " +
                PhoneUser_Column + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + IDPRODUCT_Column +") REFERENCES " + PRODUCTS_TABLE + "("+IDPRODUCT_Column+")," +
                "PRIMARY KEY ("+ IDPRODUCT_Column +" , "+PhoneUser_Column +"));");

        sqLiteDatabase.execSQL("CREATE TABLE "+ CART_TABLE +" (" +  //  add to wish list table
                IDPRODUCT_Column +" INTEGER   , " +
                PhoneUser_Column + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + IDPRODUCT_Column +") REFERENCES " + PRODUCTS_TABLE + "("+IDPRODUCT_Column+")," +
                "PRIMARY KEY ("+ IDPRODUCT_Column +" , "+PhoneUser_Column +"));");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ PRODUCTS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ WISHLIST_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CART_TABLE);

        onCreate(sqLiteDatabase);
    }

    public void InsertTO(String PhoneNumber,int ID_Product,String Table){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(IDPRODUCT_Column,ID_Product);
        row.put(PhoneUser_Column,PhoneNumber);

        // insert into Table that we choose whether be Wish list Table or Cart Table
        db.insert(Table,null,row);
        db.close();
    }
    public void InsertProduct(Product product,int ID_Product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row .put(IDPRODUCT_Column,ID_Product);
        row .put(Description_Column,product.getDescription());
        row .put(Price_Column,product.getPrice());
        row .put(Image_Column,product.getImange_id());

        db.insert(PRODUCTS_TABLE,null,row);
        db.close();
    }

    public List<Cursor> FetchForData(String PhoneUser, String Table){
        SQLiteDatabase db = this.getReadableDatabase();
        String []UserPhone = {PhoneUser};
        String selection =  IDPRODUCT_Column + " = ?";

        //fetching ProductsIDs from the Table variable where phoneUser
        Cursor cursor_idPRODUCT = db.rawQuery("SELECT "+IDPRODUCT_Column+" FROM "+Table+" WHERE "+PhoneUser_Column+" LIKE ?",UserPhone);


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
        db.close();

        return cursors;
    }
}
