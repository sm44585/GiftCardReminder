package com.example.scmuncey.giftcardreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
Authors: Josh Frankl, Spencer Muncey, and Chenchao Zang
Application: GiftCard Reminder App
Class: CS329E Elements of Mobile Computing
Date Created: February 28, 2016
Date Last Modified: May 2, 2016
*/


//Main Menu Activity with three buttons and Ad space
public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }
    //method is called when viewButton is pressed
    //calls an intent to the ViewGiftCard Activity
    public void viewGiftCard (View Target){
        Intent i = new Intent(getApplicationContext(), ViewGiftcard.class);
        startActivity(i);
    }
    //method is called when addButton is pressed
    //calls an intent to the AddGiftCard Activity
    public void addGiftCard (View target){
        Intent i = new Intent(getApplicationContext(), AddGiftCard.class);
        startActivity(i);
    }
    //method is called when storesButton is pressed
    //calls an intent to the MapsActivity Activity
    public void storesNearYou (View target){
        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(i);
    }
}
