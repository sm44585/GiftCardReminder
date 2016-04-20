package com.example.scmuncey.giftcardreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
Authors: Josh Frankl, Spencer Muncey, and Chenchao Zang
Application: GiftCard Reminder App
Class: CS329E Elements of Mobile Computing
Date Created: February 28, 2016
Date Last Modified: May 2, 2016
*/

//Activity that deletes a gift card from ListView and Database
public class DeleteGiftCard extends AppCompatActivity {

    //variable definition
    private TextView idTextView;
    private TextView storeTextView;
    private TextView amountTextView;
    private Button deleteButton;

    // create an instance of the DatabaseHelper class here
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_gift_card);

        //initialize variables and connect to interface
        storeTextView = (TextView) findViewById(R.id.stTextView);
        amountTextView = (TextView) findViewById(R.id.amntTextView);
        idTextView = (TextView) findViewById(R.id.IDtextView);
        deleteButton = (Button) findViewById(R.id.buttonDelete);

        // in the onCreate of the DeleteGiftCard Activity,
        // call the necessary methods to manage the DB in this activity
        deleteGiftCard();

        //Extract relevant information from bundle
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        String store = bundle.getString("STORE_NAME");
        String amount = bundle.getString("AMOUNT");

        //Populate the TextViews
        idTextView.setText(id);
        storeTextView.setText(store);
        amountTextView.setText(amount);

        // this will call the constructor of the DatabaseHelper class which creates the DB
        myDb = new DatabaseHelper(this);
    }

    //button to go back to view gift card screen
    public void onBackToView (View target){
        finish();
    }

    //interact with database to delete desired gift card record
    public void deleteGiftCard() {
        deleteButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        //Get gift card record id from TextView
                        String id = idTextView.getText().toString();

                        //Perform delete data method
                        boolean isInserted = myDb.deleteData(id);

                        //Check if data was successfully deleted
                        if (isInserted == true)
                            Toast.makeText(DeleteGiftCard.this, "Data Deleted", Toast.LENGTH_LONG).show();

                        else
                            Toast.makeText(DeleteGiftCard.this, "Data NOT Deleted", Toast.LENGTH_LONG).show();

                        //Start new intent back to Main Menu so that ListView is refreshed
                        Intent i = new Intent(getApplicationContext(), MainMenu.class);
                        startActivity(i);
                    }

                }
        );
    }
}
