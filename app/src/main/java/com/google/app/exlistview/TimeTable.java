package com.google.app.exlistview;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class TimeTable extends AppCompatActivity {
    SeekBar seekBar;
    List<String> table = new ArrayList<>();
    ListView tableList;

    public void fillTable(int tableNo) {

        table = new ArrayList<>();
        for (int i = 1; i < 21; i++)
            table.add(String.format("%d * %d =  %d", i, tableNo, i * tableNo));

        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, table);
        tableList.setAdapter(aa);

    }

    public void prevPage(View view){

        Intent intent = new Intent(TimeTable.this, MainActivity.class);
        finish();
        startActivity(intent);

    }

    public void nextPage(View view){

        Intent intent = new Intent(TimeTable.this, Egg.class);
        finish();
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_table);

        seekBar = findViewById(R.id.seekBar4);
        seekBar.setMax(20);
        seekBar.setProgress(10);

        tableList = findViewById(R.id.table);
        fillTable(10);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.d("Progress Changed", String.valueOf(progress));
                fillTable(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        tableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("specific Click", table.get(position));
                Toast.makeText(getApplicationContext(), table.get(position), Toast.LENGTH_LONG).show();

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
