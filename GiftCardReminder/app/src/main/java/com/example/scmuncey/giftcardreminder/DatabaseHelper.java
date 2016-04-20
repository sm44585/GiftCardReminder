package com.example.scmuncey.giftcardreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
Authors: Josh Frankl, Spencer Muncey, and Chenchao Zang
Application: GiftCard Reminder App
Class: CS329E Elements of Mobile Computing
Date Created: February 28, 2016
Date Last Modified: May 2, 2016
*/

//Custom Java Class to store gift card records
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database information
    // Declaration of the DB name
    public static final String DATABASE_NAME = "GiftCards.db";
    // Declaration of the DB TABLE NAME
    public static final String TABLE_NAME = "giftcard_table";

    // TABLE field name declaration
    public static final String COL_1 = "_id";
    public static final String COL_2 = "STORE_NAME";
    public static final String COL_3 = "AMOUNT";
    public static final String COL_4 = "IMG_PATH";

    // this is referencing the java class that will manage the SQL DB
    public DatabaseHelper(Context context) {

        // whenever the constructor below is called, our DB will now be created
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // this is the execute sql query method that takes a string sql query and executes this query
        db.execSQL("create table " + TABLE_NAME + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, STORE_NAME TEXT, AMOUNT REAL, IMG_PATH TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // upgrade the table if version number is increased and call onCreate to create a new DB
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);

    }

    //Method that inserts store name, amount, and image path into new gift card record
    public boolean insertData(String storeName, double amount, String imagePath) {

        // Open the database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        // This class is used to store a set of values that a ContentResolver can process.
        ContentValues contentValues = new ContentValues();

        // Specify the column and the data for that column
        contentValues.put(COL_2, storeName);
        contentValues.put(COL_3, amount);
        contentValues.put(COL_4, imagePath);

        // need to give this the table name and the content values
        long result = db.insert(TABLE_NAME,null,contentValues);

        // method will return -1 if the insert did not work
        if (result == -1)
            return false;
        else
            return true;
    }

    //Method that returns all records in database as a cursor object
    public Cursor getAllData() {
        // Open the database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        // A Cursor represents the result of a query and basically points to one row of the query result.
        // This way Android can buffer the query results efficiently; as it does not have to load all data into memory.
        // the "*" means select "all"
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }

    //Method that updates store name, amount, or both for existing gift card record
    public boolean updateData(String Id, String storeName, double amount){
        // Open the database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        // This class is used to store a set of values that a ContentResolver can process.
        ContentValues contentValues = new ContentValues();

        // Specify the column and the data for that column
        contentValues.put(COL_1,Id);
        contentValues.put(COL_2,storeName);
        contentValues.put(COL_3,amount);

        // need to give this the table name and the content values
        long result = db.update(TABLE_NAME, contentValues, "_id = ?", new String[] {Id});

        // method will return -1 if the insert did not work
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean deleteData(String Id) {
        // Open the database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        // need to give this the table name and the content values
        long result = db.delete(TABLE_NAME, "_id = ?", new String[] {Id});

        // method will return -1 if the insert did not work
        if (result == -1)
            return false;
        else
            return true;
    }
}
