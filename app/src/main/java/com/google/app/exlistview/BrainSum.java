package com.google.app.exlistview;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;


public class BrainSum extends AppCompatActivity {

    final int GAME_DURATION = 30;
    Button[] choices;
    Button playAgain;
    TextView timerView, problemView, scoreView;
    LinearLayout boardLayout;//not required, can handle individual views
    TextView resultView;
    Random random;
    ConstraintLayout gameLayout;
    int duration, operand1, operand2, totalAnswers, correctAnswers;

    public void nextPage(View view) {

        Intent intent = new Intent(BrainSum.this, TimeTable.class);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brain_sum);
        duration = GAME_DURATION;
        random = new Random();

        operand1 = random.nextInt(100);
        operand2 = random.nextInt(100);
        gameLayout = findViewById(R.id.gameLayout);
        timerView = findViewById(R.id.timer);
        problemView = findViewById(R.id.problem);
        scoreView = findViewById(R.id.score);
        choices = new Button[]{findViewById(R.id.choice1), findViewById(R.id.choice2), findViewById(R.id.choice3), findViewById(R.id.choice4)};
        boardLayout = findViewById(R.id.board);
        resultView = findViewById(R.id.result);
        playAgain = findViewById(R.id.playAgain);
        gameLayout.setVisibility(View.INVISIBLE);
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

    public void go(View view) {

        totalAnswers = 0;
        correctAnswers = 0;
        view.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        resultView.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
        for (int i = 0; i < choices.length; i++) {
            choices[i].setVisibility(View.VISIBLE);
            choices[i].setClickable(true);
        }
        boardLayout.setVisibility(View.VISIBLE);

        problemView.setText(String.format("%d + %d", operand1, operand2));
        choices[random.nextInt(4)].setText(String.valueOf(operand1 + operand2));
        //result.setVisibility(View.VISIBLE);

        //set the timer in timer textView
        timerView.setText(String.format("%dS", duration));
        CountDownTimer countDownTimer = new CountDownTimer(duration * 1000, 1000) {
            int count = 1;

            @Override
            public void onTick(long millisUntilFinished) {

                timerView.setText(String.format("%dS", duration - count));
                count++;
            }

            @Override
            public void onFinish() {
                timerView.setText(String.format("%dS", GAME_DURATION));
                resultView.setText("Game Over!");
                resultView.setVisibility(View.VISIBLE);
                playAgain.setVisibility(View.VISIBLE);

                for (int i = 0; i < choices.length; i++)
                    choices[i].setClickable(false);

            }
        };
        countDownTimer.start();
    }

    public void checkAnswer(View view) {

        Log.i("check answer", view.getTag().toString() + "  clicked...");
        Button clicked = choices[Integer.parseInt(view.getTag().toString())];
        int curAnswer = Integer.parseInt(clicked.getText().toString());
        Log.i("answer", String.format("%d is selected", curAnswer));

        //validate the answer and update the score
        if (curAnswer == operand1 + operand2) {
            correctAnswers++;
            resultView.setText("Correct Answer");
        } else resultView.setText("Wrong Answer (");

        resultView.setVisibility(View.VISIBLE);
        totalAnswers++;
        scoreView.setText(String.format("%d/%d", correctAnswers, totalAnswers));

        //set the new answer and choices
        for (int i = 0; i < choices.length; i++) {
            choices[i].setText(String.valueOf(random.nextInt(100)));
        }

        operand1 = random.nextInt(100);
        operand2 = random.nextInt(100);
        problemView.setText(String.format("%d + %d", operand1, operand2));

        choices[random.nextInt(4)].setText(String.valueOf(operand1 + operand2));
    }
}
