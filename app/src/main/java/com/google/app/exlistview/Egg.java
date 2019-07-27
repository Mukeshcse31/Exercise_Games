package com.google.app.exlistview;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class Egg extends AppCompatActivity {
    SeekBar seekBar;
    TextView timer;
    int currentProgress;
    CountDownTimer countDownTimer;

    public void nextPage(View view) {

        Intent intent = new Intent(Egg.this, MainActivity.class);
        startActivity(intent);

    }

    public void control(View view) {


        String tag = view.getTag().toString();
        Log.i("Action", tag);
        if (tag.equals("0")) {
           setControl("play");

        } else {
            setControl("pause");
        }


    }

    public void setControl(String temp){

        Button button = (Button) findViewById(R.id.play);
        if (temp.equals("play")) {
            button.setText("Pause");
            button.setTag("1");
            seekBar.setEnabled(false);
            setTimer();

        } else {
            button.setText("Play");
            button.setTag("0");
            seekBar.setEnabled(true);
            stopTimer();
        }
    }
    public void stopTimer() {
        countDownTimer.cancel();
    }


    public void setTimer() {

        countDownTimer = new CountDownTimer(currentProgress * 1000, 1000) {

            int currentTime = currentProgress;

            @Override
            public void onTick(long millisUntilFinished) {

                timer.setText(String.format("%02d : %02d", currentTime / 60, currentTime % 60));
                currentTime--;
            }

            @Override
            public void onFinish() {

                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                mediaPlayer.start();
                timer.setText(String.format("%02d : %02d", 0, 0));
                setControl("pause");

            }
        };
        countDownTimer.start();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.egg);

        seekBar = findViewById(R.id.seekBar4);
        seekBar.setMax(300);
        seekBar.setProgress(100);

        timer = (TextView) findViewById(R.id.timer);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.d("Progress Changed", String.valueOf(progress));
                timer.setText(String.format("%02d : %02d", progress / 60, progress % 60));
                currentProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /*
        //Timers method 1
        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("countdown", String.valueOf(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Log.d("countdown", "countdown finished !!!");
            }
        };
        countDownTimer.start();

        //Timers method 2
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("Runnable", "1 sec has passed...");
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
        */
    }
}
