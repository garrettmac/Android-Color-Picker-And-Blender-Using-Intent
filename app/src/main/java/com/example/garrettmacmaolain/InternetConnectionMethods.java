package com.example.garrettmacmaolain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by jesper on 26/11/14.
 */
public class InternetConnectionMethods {

    public static void getImageFromWeb(final String url, final ImageSetListener imageSetListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap downloadBitmap = downloadBitmap(url);
                    //user.setProfilePicture(downloadBitmap);
                    imageSetListener.onImageSet(downloadBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }).start();
    }


    private static Bitmap downloadBitmap(String url) throws IOException {
        HttpUriRequest request = new HttpGet(url.toString());
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(request);

        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            HttpEntity entity = response.getEntity();
            byte[] bytes = EntityUtils.toByteArray(entity);

            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
                    bytes.length);
            return bitmap;
        } else {
            throw new IOException("Download failed, HTTP response code "
                    + statusCode + " - " + statusLine.getReasonPhrase());
        }
    }


    public interface ImageSetListener {
        public void onImageSet(Bitmap image);
    }
}