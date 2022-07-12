package com.example.quizjam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class gameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
    }

    public void startagain(View view) {
        Intent start = new Intent(gameOver.this, startActivity.class);
        startActivity(start);
    }
}