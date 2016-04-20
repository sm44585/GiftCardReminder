package com.example.scmuncey.giftcardreminder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

/*
Authors: Josh Frankl, Spencer Muncey, and Chenchao Zang
Application: GiftCard Reminder App
Class: CS329E Elements of Mobile Computing
Date Created: February 28, 2016
Date Last Modified: May 2, 2016
*/


//Activity that view all gift cards in database
//Uses ListViews, adaptors, cursors, and SQLite database
public class ViewGiftcard extends AppCompatActivity {

    // this will call the constructor of the DatabaseHelper class which will create the DB
    DatabaseHelper myDb = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_giftcard);

        //Call to populateListView method when acitivty is first created
        populateListView();
    }

    //Method that populates the ListView with data
    //From the SQLite database
    public void populateListView() {
        Cursor cursor = myDb.getAllData();
        String[] fromFieldNames = new String[] {
                DatabaseHelper.COL_1,
                DatabaseHelper.COL_2,
                DatabaseHelper.COL_3,
                DatabaseHelper.COL_4
        };
        int[] toViewIDs = new int[] {
                R.id.textViewGCiD,
                R.id.textViewStore,
                R.id.textViewAmount,
                R.id.imageViewGC
        };
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.giftcard_layout, cursor, fromFieldNames, toViewIDs, 0);
        ListView myList = (ListView) findViewById(R.id.listViewGiftcards);
        myList.setAdapter(myCursorAdapter);
        myCursorAdapter.notifyDataSetChanged();
    }

    //Method to handle update Gift Card button click
    //Starts intent directed at UpdateGiftCard activity
    public void updateGC (View target){
        //create new intent to call UpdateGiftCard activity
        Intent i = new Intent(getApplicationContext(), UpdateGiftCard.class);
        //Get Gift card ID, Store Name and Amount from textViews
        TextView gcIdEditText = (TextView) findViewById(R.id.textViewGCiD);
        TextView storeEditText = (TextView) findViewById(R.id.textViewStore);
        TextView amountEditText = (TextView) findViewById(R.id.textViewAmount);
        String  gc_id = gcIdEditText.getText().toString();
        String  store = storeEditText.getText().toString();
        String  amount = amountEditText.getText().toString();

        //Create the bundle
        Bundle bundle = new Bundle();
        //Add your data from getFactualResults method to bundle
        bundle.putString("id", gc_id);
        bundle.putString("STORE_NAME", store);
        bundle.putString("AMOUNT", amount);
        //Add the bundle to the intent and start the intent

        i.putExtras(bundle);
        startActivity(i);
    }

    //Method to handle delete Gift Card Button click
    //Starts intent directed at DeleteGiftCard Acitivty
    public void deleteGC (View target){
        Intent i = new Intent(getApplicationContext(), DeleteGiftCard.class);
        //Get Gift card ID, Store Name and Amount from textViews
        TextView gcIdEditText = (TextView) findViewById(R.id.textViewGCiD);
        TextView storeEditText = (TextView) findViewById(R.id.textViewStore);
        TextView amountEditText = (TextView) findViewById(R.id.textViewAmount);
        String  gc_id = gcIdEditText.getText().toString();
        String  store = storeEditText.getText().toString();
        String  amount = amountEditText.getText().toString();

        //Create the bundle
        Bundle bundle = new Bundle();
        //Add your data from getFactualResults method to bundle
        bundle.putString("id", gc_id);
        bundle.putString("STORE_NAME", store);
        bundle.putString("AMOUNT", amount);
        //Add the bundle to the intent and start the intent

        i.putExtras(bundle);


        startActivity(i);

    }

    //Method to handle back to main menu button
    public void onBackToMain (View target) {finish();}
}
