package com.example.quizjam;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class signupPage extends AppCompatActivity {
    Button signupBtn;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.signup_page);
        signupBtn = findViewById(R.id.signupbtn);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
    }

    public void getRegister(View view) {
        String a = username.getText().toString();
        String b = password.getText().toString();
        String s = "";
        try {
            URL url = new URL("https://jam2021.000webhostapp.com/JAM/register.php?a="+a+"&b="+b);
            URLConnection ucon = url.openConnection();
            InputStream in = ucon.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);
            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                s=s+current;
                data = isw.read();
            }
            if(s.equals("You're Registered!")) {
                Intent login = new Intent(signupPage.this, loginPage.class);
                startActivity(login);

            }
            else {
                signupBtn.setText(s);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void LoginPage(View view) {
        Intent login = new Intent(signupPage.this, loginPage.class);
        startActivity(login);
    }
}