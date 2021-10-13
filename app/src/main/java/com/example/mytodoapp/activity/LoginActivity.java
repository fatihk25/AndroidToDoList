package com.example.mytodoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mytodoapp.R;
import com.example.mytodoapp.utils.Session;
import com.example.mytodoapp.utils.UserDatabaseHandler;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private UserDatabaseHandler db;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new UserDatabaseHandler(this);
        session = new Session(this);
        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        if(session.loggedin()){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
    }

    public void loginOnClick(View view) {
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();
        int userId = db.getIdUser(email.trim(),pass.trim());

        if(db.getUser(email,pass)){
            session.setLoggedin(true, userId);
            session.setUser(email);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Wrong email/password",Toast.LENGTH_SHORT).show();
        }
    }

    public void registerOnClick(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}