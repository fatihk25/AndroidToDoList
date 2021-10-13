package com.example.mytodoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mytodoapp.R;
import com.example.mytodoapp.utils.UserDatabaseHandler;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private UserDatabaseHandler dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new UserDatabaseHandler(this);
        etEmail = findViewById(R.id.etEmailRegister);
        etPassword = findViewById(R.id.etPasswordRegister);
    }

    private void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void registerUserOnClick(View view) {
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();
        if(email.isEmpty() && pass.isEmpty()){
            displayToast("The field is empty");
        }else{
            dbHelper.addUser(email,pass);
            displayToast("User registered");
            finish();
        }
    }

    public void loginHomeOnClick(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }
}