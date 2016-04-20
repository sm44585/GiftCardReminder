package com.example.scmuncey.giftcardreminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.Toast;

/*
Authors: Josh Frankl, Spencer Muncey, and Chenchao Zang
Application: GiftCard Reminder App
Class: CS329E Elements of Mobile Computing
Date Created: February 28, 2016
Date Last Modified: May 2, 2016
*/

//Splash screen for loading the application
public class Splash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this, MainMenu.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
        //toast displayed to user alerting of wait time
        Toast.makeText(this, "Please wait while we get ready", Toast.LENGTH_LONG).show();
    }
}
