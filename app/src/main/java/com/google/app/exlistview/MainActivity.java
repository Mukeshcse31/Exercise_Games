package com.google.app.exlistview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> names;
    ArrayList<String> images;
    ImageView celebImage;
    DownloadBitmap downloadBitmap;
    Bitmap curImage;
    Button button[];
    Random random;
    String rightAnswer;
    int nextQuestionNo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();
        button = new Button[]{findViewById(R.id.button1), findViewById(R.id.button2), findViewById(R.id.button3), findViewById(R.id.button4)};

        celebImage = findViewById(R.id.celebImage);
        DownloadContent downloadContent = new DownloadContent();
        DownloadContent downloadImage = new DownloadContent();

        downloadBitmap = new DownloadBitmap();

        try {
            names = downloadContent.execute("http://www.posh24.se/kandisar", "names").get();
            images = downloadImage.execute("http://www.posh24.se/kandisar", "bitmap").get();

            Log.i("Names", names.get(4));
            Log.i("URL", images.get(5));

        } catch (Exception e) {
            e.printStackTrace();
        }

        //for(int i = 0;i < 5; i ++)
        nextQuestion(nextQuestionNo);
    }

protected void nextQuestion(int no){

    //GEt Bitmap

    Log.i("correct Images",images.get(no));
    Log.i("correct Names",names.get(no));
    rightAnswer = names.get(no);
    try {
        downloadBitmap = new DownloadBitmap();
        curImage = downloadBitmap.execute(images.get(no)).get();
    } catch (Exception e) {
        e.printStackTrace();
    }

    // set Image and answers
    celebImage.setImageBitmap(curImage);

    for (int i = 0; i < 4; i++) {
        button[i].setText(getRandomName());
    }

    button[random.nextInt(4)].setText(rightAnswer);
}


    protected String getRandomName() {


        return names.get(random.nextInt(names.size()));

    }

    public void checkName(View view) {
        int buttonNo = Integer.parseInt(view.getTag().toString());
        Log.i("Button", String.format("%d selected",buttonNo));
String temp = "";

        if(rightAnswer.equals(button[buttonNo].getText().toString())) {
            temp = "Correct!";
        } else {
            temp = "Wrong (";
        }

        Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();
        nextQuestion(++nextQuestionNo);
    }
}
