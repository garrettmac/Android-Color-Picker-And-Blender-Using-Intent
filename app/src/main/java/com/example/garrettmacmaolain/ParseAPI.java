package com.example.garrettmacmaolain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jesper on 26/11/14.
 */
public class ParseAPI {

    //TODO Build Parse Methods

    public static void getAllCloths(){

    }
    public static void getAllUserClothsFromUser(){

    }
    public static void getUserInfo(){

    }
    public static void updateUserInfo(){

    }
    public static void getAllClothsFromCategory(){

    }
    public static void sendUserInfoToParse(ParseUser parseUser, User user){
        Log.v("FB",""+user.toString());
        parseUser.put(ParseStrings.USER_FIRST_NAME, user.getFirstName());
        parseUser.put(ParseStrings.USER_EMAIL,user.getEmail());
        parseUser.put(ParseStrings.USER_FACEBOOK_ID,user.getFacebookId());
        parseUser.put(ParseStrings.USER_HOMETOWN,user.getHomeTown());
        parseUser.put(ParseStrings.USER_PICTURE,bitmapToParseFile(user.getProfilePicture()));

        parseUser.saveInBackground();
    }
    private static ParseFile bitmapToParseFile(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapData = stream.toByteArray();
        ParseFile parseFile = new ParseFile("profilePic.jpg",bitmapData);
        try {
            if(stream !=null){
                stream.close();}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseFile;
    }
    public static void getImageFromFile(ParseFile parseFile, final Product product, final Product.ProductImageSetListener listener){

        parseFile.getDataInBackground(new GetDataCallback() {
            public void done(final byte[] data, ParseException e) {

                if (e == null) {
                    //TODO
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPurgeable = true;

                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                            Bitmap b2;
                            int newHeight = (Integer)(bitmap.getHeight() * 500) / bitmap.getWidth();

                            b2 = bitmap.createScaledBitmap(bitmap, 500, newHeight, true);

                            bitmap.recycle();
                            bitmap = b2;
                            product.setImage(Bitmap.createBitmap(bitmap));
                            listener.onProductImageSet(product);
                        }}
                    ).start();
                } else {
                    // something went wrong
                }
            }
        });
    }
    

}
