package com.dolly.re_cam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;

public class CammyLoginActivity extends AppCompatActivity {



    private EditText Email, Password;
    private Button Registration, Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cammy_login);

        Email    = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);

        Registration = (Button) findViewById(R.id.registration);
        Login        = (Button) findViewById(R.id.login);

    }
}
