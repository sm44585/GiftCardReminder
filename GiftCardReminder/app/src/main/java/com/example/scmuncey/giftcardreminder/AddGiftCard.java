package com.example.scmuncey.giftcardreminder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.Date;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class AddGiftCard extends AppCompatActivity {

    //variable definition
    private TextView storeTextView;
    private EditText storeEditText;
    private TextView amountTextView;
    private EditText amountEditText;
    private Button backButton;
    private Button photoButton;
    private Button submitButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageView;
    // define TAG constant for Log messages
    private static final String TAG = "AddGiftCard";

    // create an instance of the DatabaseHelper class here
    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gift_card);

        //initialize variables and connect to interface
        storeTextView = (TextView) findViewById(R.id.storeTextView);
        storeEditText = (EditText) findViewById(R.id.storeEditText);
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        amountEditText = (EditText) findViewById(R.id.amountEditText);
        backButton = (Button) findViewById(R.id.backButton);
        photoButton = (Button) findViewById(R.id.photoButton);
        submitButton = (Button) findViewById(R.id.submitButton);
        mImageView = (ImageView) findViewById(R.id.mImageView);

        // this will call the constructor of the DatabaseHelper class which will create the DB
        myDb = new DatabaseHelper(this);

        // in the onCreate of the Main Activity, call all of the methods needed to manage the DB
        AddData();

    }

    // this section listens for the "Submit" button event and inserts into the DB
    // must convert to String in order to input into the DB
    public void AddData() {
        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.i(TAG, "submitButton clicked");
                        String storeName = storeEditText.getText().toString();
                        double amount = Double.parseDouble(amountEditText.getText().toString());

                        boolean isInserted = myDb.insertData(storeName, amount);
                        if (isInserted == true)
                            Toast.makeText(AddGiftCard.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(AddGiftCard.this, "Data NOT Inserted", Toast.LENGTH_LONG).show();
                    }

                }
        );
    }

    public void onBackToMain (View target){
        finish();
    }

    public void takePhoto (View target){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i(TAG, "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                mImageView.setImageBitmap(mImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
