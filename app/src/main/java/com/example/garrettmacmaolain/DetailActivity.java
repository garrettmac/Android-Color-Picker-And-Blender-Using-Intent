package com.example.garrettmacmaolain;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DetailActivity extends Activity implements View.OnClickListener {
    private String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView mImageView;

    boolean useSmallPhoto = true;
    Button photoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        mImageView = (ImageView) findViewById(R.id.imageView);
        photoButton = (Button) findViewById(R.id.photoButton);
        photoButton.setOnClickListener(this);

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if(useSmallPhoto){
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }else {
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if(useSmallPhoto){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mImageView.setImageBitmap(imageBitmap);
            }else {
                File imgFile = new  File(mCurrentPhotoPath);

                if(imgFile.exists()) {

                    //Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    setPic();
                    //mImageView.setImageBitmap(myBitmap);
                }
                //  Bundle extras = data.getExtras();
                // Bitmap imageBitmap = (Bitmap) extras.get("data");
                // mImageView.setImageBitmap(imageBitmap);
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
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.photoButton:
                dispatchTakePictureIntent();
                break;
        }
    }
    private void setPic() {
        // Get the dimensions of the View

        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();
        Log.v("Target", "H: " + targetH + "\tW: " + targetW);
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoH = bmOptions.outWidth;
        int photoW = bmOptions.outHeight;
        Log.v("Photo","H: "+photoH+"\tW: "+photoW);


        // Determine how much to scale down the image
        int scaleFactor;
        scaleFactor = Math.min(photoW/targetW,photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;

        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }
}
