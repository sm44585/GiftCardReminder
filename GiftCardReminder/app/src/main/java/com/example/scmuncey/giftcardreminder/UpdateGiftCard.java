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

//Acitivty that updates a specific gift card record
public class UpdateGiftCard extends AppCompatActivity {

    //variable definition
    private TextView idTextView;
    private EditText storeEditText;
    private EditText amountEditText;
    private Button updateButton;

    // create an instance of the DatabaseHelper class here
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_gift_card);

        //initialize variables and connect to interface
        storeEditText = (EditText) findViewById(R.id.strEditText);
        amountEditText = (EditText) findViewById(R.id.amtEditText);
        idTextView = (TextView) findViewById(R.id.IDtextView);
        updateButton = (Button) findViewById(R.id.btnDelete);

        // in the onCreate of the AddGiftCard Activity, call all of the methods needed to manage the DB
        updateGiftCard();

        //Extract relevant information from bundle
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        String store = bundle.getString("STORE_NAME");
        String amount = bundle.getString("AMOUNT");

        //Populate the TextViews
        idTextView.setText(id);
        storeEditText.setText(store);
        amountEditText.setText(amount);

        // this will call the constructor of the DatabaseHelper class which creates the DB
        myDb = new DatabaseHelper(this);
    }

    //button to go back to view gift card screen
    public void onBackToView (View target){
        finish();
    }

    //interact with database to update desired gift card record
    public void updateGiftCard() {
        updateButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        //retrieve information from UI
                        String id = idTextView.getText().toString();
                        String storeName = storeEditText.getText().toString();
                        double amount = Double.parseDouble(amountEditText.getText().toString());

                        //Perform update data operation on records
                        boolean isInserted = myDb.updateData(id, storeName, amount);

                        //Check if update was successful
                        if (isInserted == true)
                            Toast.makeText(UpdateGiftCard.this, "Data Updated", Toast.LENGTH_LONG).show();

                        else
                            Toast.makeText(UpdateGiftCard.this, "Data NOT Updated", Toast.LENGTH_LONG).show();
                        //Start new intent back to main menu to ensure ListView updated
                        Intent i = new Intent(getApplicationContext(), MainMenu.class);
                        startActivity(i);
                    }

                }
        );
    }
}
