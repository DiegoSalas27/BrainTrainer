package com.example.android.braintrainer;

import android.os.CountDownTimer;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button startButton, button2, button3, button4, button5, btnPlayAgain;
    TextView sumTv, resultTv, pointsTv, timerTv, greatestScoreTv;
    RelativeLayout gameRelativeLayout;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    ArrayList<Integer> greaterScore = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int highest = 0;
    int numberOfQuestions = 0;
    int numberOfQuestions2 = 0;
    int tracker = 0;
    boolean gameActive = false;
    Map<Integer, Integer> map = new HashMap();

    public void playAgain(View view){

        gameActive = true;
        score = 0;
        numberOfQuestions = 0;
        timerTv.setText("30s");
        pointsTv.setText("0/0");
        resultTv.setText("");
        btnPlayAgain.setVisibility(View.INVISIBLE);
        greatestScoreTv.setVisibility(view.INVISIBLE);

        generateQuestion();

        new CountDownTimer(30100, 1000){

            @Override
            public void onTick(long l) {

                timerTv.setText(String.valueOf(l/1000) + "s");
            }

            @Override
            public void onFinish() {

                map.put(numberOfQuestions2, numberOfQuestions);
                numberOfQuestions2++;
                greaterScore.add(score);
                for(int i = 0; i < greaterScore.size(); i++){

                        if(highest <= greaterScore.get(i)){
                            highest = greaterScore.get(i);
                            tracker = i;
                        }

                }

                timerTv.setText("0s");
                greatestScoreTv.setText("Greater score: " + Integer.toString(highest) + "/" + map.get(tracker));
                resultTv.setText("Your score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
                greatestScoreTv.setVisibility(View.VISIBLE);
                btnPlayAgain.setVisibility(View.VISIBLE);
                gameActive = false;

            }
        }.start();
    }
    public void generateQuestion(){

        answers.clear();

        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        int generateOperation = rand.nextInt(4);

        switch(generateOperation)
        {
            case 2:  while(a < b)
            {
                a = rand.nextInt(21);
                b = rand.nextInt(21);
            } break;

            case 3:  while(a < b && b == 0)
            {
                a = rand.nextInt(21);
                b = rand.nextInt(21);
            } break;
            default: break;
        }

        switch (generateOperation)
        {
            case 0: sumTv.setText(Integer.toString(a) + " + " + Integer.toString(b)); break;
            case 1: sumTv.setText(Integer.toString(a) + " * " + Integer.toString(b)); break;
            case 2: sumTv.setText(Integer.toString(a) + " - " + Integer.toString(b)); break;
            case 3: sumTv.setText(Integer.toString(a) + " / " + Integer.toString(b)); break;
            default: break;
        }

        locationOfCorrectAnswer = rand.nextInt(4);
        int incorrectAnswer;

        for(int i = 0; i < 4; i++){

            if(i == locationOfCorrectAnswer){

                switch(generateOperation)
                {
                    case 0: answers.add(a + b); break;
                    case 1: answers.add(a * b); break;
                    case 2: answers.add(a - b); break;
                    case 3: answers.add(a / b); break;
                    default: break;
                }

            } else{

                switch(generateOperation)
                {
                    case 0: incorrectAnswer = rand.nextInt(41);
                        while(incorrectAnswer == a + b){
                        incorrectAnswer = rand.nextInt(41);
                    } answers.add(incorrectAnswer); break;

                    case 1: incorrectAnswer = rand.nextInt(401);
                        while(incorrectAnswer == a * b){
                        incorrectAnswer = rand.nextInt(401);
                    } answers.add(incorrectAnswer); break;

                    case 2: incorrectAnswer = rand.nextInt(21);
                        while(incorrectAnswer == a - b){
                        incorrectAnswer = rand.nextInt(21);
                    } answers.add(incorrectAnswer); break;

                    case 3: incorrectAnswer = rand.nextInt(21);
                        while(incorrectAnswer == a / b){
                        incorrectAnswer = rand.nextInt(21);
                    } answers.add(incorrectAnswer); break;
                    default: break;
                }
            }
        }
        button2.setText(Integer.toString(answers.get(0)));
        button3.setText(Integer.toString(answers.get(1)));
        button4.setText(Integer.toString(answers.get(2)));
        button5.setText(Integer.toString(answers.get(3)));

    }
    public void start(View view){

        gameActive = true;
        startButton.setVisibility(view.INVISIBLE);
        gameRelativeLayout.setVisibility(view.VISIBLE);
        playAgain(findViewById(R.id.btnPlayAgain));

    }
    public void chooseAnswer(View view){

       if(gameActive)
       {
           if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){

               score++;
               resultTv.setText("Correct!");

           } else{

               resultTv.setText("Wrong!");

           }

           numberOfQuestions++;
           pointsTv.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
           generateQuestion();
       }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        sumTv = (TextView) findViewById(R.id.sumTv);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        resultTv = (TextView) findViewById(R.id.resultTv);
        pointsTv = (TextView) findViewById(R.id.pointsTv);
        timerTv = (TextView) findViewById(R.id.timerTv);
        btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        greatestScoreTv = (TextView) findViewById(R.id.greatestScoreTv);
        gameRelativeLayout = (RelativeLayout) findViewById(R.id.gameRelativeLayout);



    }
}
