package com.example.quizjam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class gameWin extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamewin);

    }

    public void startagain2(View view) {
        Intent start = new Intent(gameWin.this, startActivity.class);
        startActivity(start);
    }

}