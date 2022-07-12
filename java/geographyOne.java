package com.example.quizjam;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class geographyOne extends AppCompatActivity {

    private TextView geo_ques, score, questionNbr, timer;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button btnNext;
    private Button btnShr;

    int totalQuestions;
    int qCounter = 0;
    int scores;

    ColorStateList dfRbColor;
    boolean answered;

    private Questions currentQuestion;

    private List<Questions> Questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geography_one);

        Questions = new ArrayList<>();
        geo_ques = findViewById(R.id.geo_ques);
        score = findViewById(R.id.score);
        questionNbr = findViewById(R.id.questionNbr);
        timer = findViewById(R.id.timer);

        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        btnNext = findViewById(R.id.btnNext);
        btnShr = findViewById(R.id.btnShr);

        dfRbColor = rb1.getTextColors();

        addQuestions();
        totalQuestions = Questions.size();
        showNextQuestion();

        btnShr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answered == false) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        checkAnswer();
                    }else{
                        Toast.makeText(geographyOne.this, "Select an Answer", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showNextQuestion();
                }
            }
        });

    }

    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected) + 1;
        if(answerNo == currentQuestion.getCorrectAnsNo()){
            i += 20;
            scores+=10;
            score.setText("Score : "+scores);
        }
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);
        switch (currentQuestion.getCorrectAnsNo()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                break;
        }

        if(answerNo != currentQuestion.getCorrectAnsNo()){
            i -= 30;
            scores-=5;
            score.setText("Score : "+scores);
        }

        if(qCounter < totalQuestions){
            btnNext.setText("Next");
        }else{
            btnNext.setText("Finish");
        }

    }

    private void showNextQuestion() {

        radioGroup.clearCheck();
        rb1.setTextColor(dfRbColor);
        rb2.setTextColor(dfRbColor);
        rb3.setTextColor(dfRbColor);
        rb4.setTextColor(dfRbColor);

        if(qCounter < totalQuestions){
            currentQuestion = Questions.get(qCounter);
            geo_ques.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            qCounter++;
            btnNext.setText("Submit");
            quizTimer();
            questionNbr.setText("Question : " +qCounter+ "/" +totalQuestions);
            answered = false;

        }else{
            finish();
            if (i > 0) {
                Intent win = new Intent(geographyOne.this, gameWin.class);
                startActivity(win);
            }
        }
    }

    private int i = 60;
    public void quizTimer() {
        boolean t = true;
        Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            public void run() {
                while (t) {
                    try {
                        i--;
                        Thread.sleep(2000);
                        if (i <= 0 ) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(geographyOne.this, "Times Up!", Toast.LENGTH_SHORT);
                                    toast.show();
                                    Intent over = new Intent(geographyOne.this, gameOver.class);
                                    startActivity(over);
                                }
                            });
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable(){
                        public void run() {
                            timer.setText(i + " sec");
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).interrupt();
    }

    private void addQuestions() {
        Questions.add(new Questions("Which country has the largest population in the world? ", "China", "America", "Indonesia", "India", 1));
        Questions.add(new Questions("What is the capital of Mexico?", "San Francisco", "Mexico City", "New York", "Seoul", 2));
        Questions.add(new Questions("Where is the Eiffel Tower located?", "Jakarta, Indonesia", "Tokyo, Japan", "Paris, France", "New York, USA", 3));
        Questions.add(new Questions("What is the name of the smallest country in the world?", "Russia", "Nepal", "The Vatican City", "Indonesia", 3));
        Questions.add(new Questions("What is the official currency of the United Kingdom?", "Won", "Dollar", "Rupiah", "Pound sterling", 4));
    }

    private void sendEmail() {
        String subject = "QUIZJAM";
        String message = "I have done the Geography Quiz and it is fun. You should try this app too!";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an e-mail client"));
    }
}
