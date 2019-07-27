package com.google.app.exlistview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadBitmap extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... urls) {

        URL uRL = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            uRL = new URL(urls[0]);

            connection = (HttpURLConnection) uRL.openConnection();
            inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
