package com.example.scmuncey.giftcardreminder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
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
import java.util.List;

public class AddGiftCard extends AppCompatActivity {

    //variable definition
    private static final int ACTION_TAKE_PHOTO_B = 1;
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

        setBtnListenerOrDisable(
                photoButton,
                mTakePicOnClickListener,
                MediaStore.ACTION_IMAGE_CAPTURE
        );

        // in the onCreate of the Main Activity, call all of the methods needed to manage the DB
        AddData();



        // ask user for permission to take photo and use external storage
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(getString(R.string.app_name),"Permission is granted");

            } else {

                Log.v(getString(R.string.app_name), "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(getString(R.string.app_name),"Permission is granted");

        }

    }

    Button.OnClickListener mTakePicOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
                }
            };

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void setBtnListenerOrDisable(
            Button btn,
            Button.OnClickListener onClickListener,
            String intentName
    ) {
        if (isIntentAvailable(this, intentName)) {
            btn.setOnClickListener(onClickListener);
        } else {
            btn.setText("Cannot" + " " + btn.getText().toString());
            btn.setClickable(false);
        }
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

                        boolean isInserted = myDb.insertData(storeName, amount, mCurrentPhotoPath);
                        if (isInserted == true)
                            Toast.makeText(AddGiftCard.this, "Data Inserted", Toast.LENGTH_LONG).show();

                        else
                            Toast.makeText(AddGiftCard.this, "Data NOT Inserted", Toast.LENGTH_LONG).show();
                        finish();
                    }

                }
        );
    }

    public void onBackToMain (View target){
        finish();
    }


    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        switch(actionCode) {
            case ACTION_TAKE_PHOTO_B:
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        } // switch

        startActivityForResult(takePictureIntent, actionCode);
    }
    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
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
            handlePhoto();
            }
    }

    private void handlePhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
            mCurrentPhotoPath = null;
        }

    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {



		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);

		/* Associate the Bitmap to the ImageView */
        mImageView.setImageBitmap(bitmap);
        mImageView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(getString(R.string.app_name),"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
}
