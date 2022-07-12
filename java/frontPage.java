package com.example.quizjam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class frontPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);
    }

    public void math(View view) {
        Intent strMath = new Intent(frontPage.this, mathOne.class);
        startActivity(strMath);
    }

    public void science(View view) {
        Intent strScience = new Intent(frontPage.this, scienceOne.class);
        startActivity(strScience);
    }

    public void english(View view) {
        Intent strEnglish = new Intent(frontPage.this, englishOne.class);
        startActivity(strEnglish);
    }

    public void history(View view) {
        Intent strHistory = new Intent(frontPage.this, historyOne.class);
        startActivity(strHistory);
    }

    public void geography(View view) {
        Intent strGeo = new Intent(frontPage.this, geographyOne.class);
        startActivity(strGeo);
    }

    public void getVideo(View view) {
        Intent strVid1= new Intent(frontPage.this, getVid1.class);
        startActivity(strVid1);
    }
}
