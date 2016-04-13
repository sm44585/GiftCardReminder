package com.example.scmuncey.giftcardreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    //variable definition
    private Button viewButton;
    private Button addButton;
    private Button deleteButton;
    private Button storesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //initialize the buttons
        viewButton = (Button) findViewById(R.id.viewButton);
        addButton = (Button) findViewById(R.id.addButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        storesButton = (Button) findViewById(R.id.storesButton);
    }
    public void addGiftCard (View target){
        Intent i = new Intent(getApplicationContext(), AddGiftCard.class);
        startActivity(i);
    }
    public void storesNearYou (View target){
        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(i);
    }
}
