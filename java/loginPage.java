package com.example.quizjam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class loginPage extends AppCompatActivity {
    Button loginBtn;
    EditText uname;
    EditText pswd;
    SharedPreferences sharedPreferences;
    public static String pref = "sPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        uname = findViewById(R.id.et_username);
        pswd = findViewById(R.id.et_password);
        loginBtn = findViewById(R.id.loginbtn);
    }

    public void getLogin(View view) {
        String username = uname.getText().toString();
        String password = pswd.getText().toString();
        String s = "";
        pref = "sPref";
        sharedPreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usernameS", username);
        editor.putString("passwordS", password);
        editor.commit();
        try {
            URL url = new URL("https://jam2021.000webhostapp.com/JAM/login.php?username="+username+"&password="+password);
            URLConnection ucon = url.openConnection();
            InputStream in = ucon.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);
            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                s=s+current;
                data = isw.read();
            }
            if(s.equals("Login Successfully!")) {
                Intent startApp = new Intent(loginPage.this, startActivity.class);
                startActivity(startApp);

            }
            else {
                Toast.makeText(this, (s), Toast.LENGTH_SHORT).show();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
