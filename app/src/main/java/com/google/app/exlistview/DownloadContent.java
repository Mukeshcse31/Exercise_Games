package com.google.app.exlistview;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class NameImage {
    ArrayList<String> name, image;

    NameImage(ArrayList name, ArrayList image) {
        this.name = name;
        this.image = image;

    }

    public ArrayList getName() {
        return name;
    }

    public ArrayList getImage() {
        return image;
    }
}

public class DownloadContent extends AsyncTask<String, Void, ArrayList>{

    ArrayList<String> nameImage = new ArrayList<>();

    @Override
    protected ArrayList doInBackground(String... urls) {

        URL uRL = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        InputStreamReader reader = null;
        String result = "";
        try {
            uRL = new URL(urls[0]);

            connection = (HttpURLConnection) uRL.openConnection();
            inputStream = connection.getInputStream();
            reader = new InputStreamReader(inputStream);
            int temp = -1;
            temp = reader.read();

            while (temp > -1) {

                result += (char) temp;
                temp = reader.read();
            }

//            NameImage nameImage = null;
//            ArrayList<String> names = new ArrayList<String>();
//            ArrayList<String> images = new ArrayList<String>();

            Pattern pattern = Pattern.compile("<img src=\"(.*?)\"\\Walt=\"(.*?)\"");
            //Pattern pattern = Pattern.compile("Ki(.*)hian");
            Matcher m = pattern.matcher(result);

            Log.i("Names", "Before pattern match");
//            if (m.find())
                while (m.find()) {
                    if(urls[1].equals("names")) {
                        nameImage.add(m.group(2));
Log.i("Names", m.group(2));
                    }
                    else {
                        nameImage.add(m.group(1));
                        Log.i("Images", m.group(1));
                    }

//                    images.add(m.group(1));
//                    names.add(m.group(2));
                }
//            else {
//                Log.i("Names", "No Match");
//            }
            //nameImage = new NameImage(names, images);

            return nameImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
