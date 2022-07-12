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

public class scienceOne extends AppCompatActivity {

    private TextView science_ques, score, questionNbr, timer;
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
        setContentView(R.layout.science_one);

        Questions = new ArrayList<>();
        science_ques = findViewById(R.id.science_ques);
        score = findViewById(R.id.score1);
        questionNbr = findViewById(R.id.questionNbr1);
        timer = findViewById(R.id.timer1);

        radioGroup = findViewById(R.id.radioGroup1);
        rb1 = findViewById(R.id.rb11);
        rb2 = findViewById(R.id.rb21);
        rb3 = findViewById(R.id.rb31);
        rb4 = findViewById(R.id.rb41);
        btnNext = findViewById(R.id.btnNext1);
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
                        Toast.makeText(scienceOne.this, "Select an Answer", Toast.LENGTH_SHORT).show();
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
            science_ques.setText(currentQuestion.getQuestion());
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
                Intent win = new Intent(scienceOne.this, gameWin.class);
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
                                    Toast toast = Toast.makeText(scienceOne.this, "Times Up!", Toast.LENGTH_SHORT);
                                    toast.show();
                                    Intent over = new Intent(scienceOne.this, gameOver.class);
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
        Questions.add(new Questions("How many bones are in the human body?", "202", "204", "206", "208", 3));
        Questions.add(new Questions("What does DNA stand for?", "Deoxyribonucleic acid", "Deoxy Nucleic Acid", "Deoxyribonucleic acide", "Deoxy Nucleic Acide", 1));
        Questions.add(new Questions("At what temperature are Celcius and Fahrenheit equal?", "40", "-40", "24", "-24", 2));
        Questions.add(new Questions("What is the biggest planet in our solar system?", "Mars", "Venus", "Jupiter", "Neptune", 3));
        Questions.add(new Questions("Which Apollo moon mission was the first to carry a lunar rover?", "Apollo 12", "Apollo 13", "Apollo 14", "Apollo 15", 4));
    }

    private void sendEmail() {
        String subject = "QUIZJAM";
        String message = "I have done the Science Quiz and it is fun. You should try this app too!";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an e-mail client"));
    }
}