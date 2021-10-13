package com.example.mytodoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mytodoapp.R;
import com.example.mytodoapp.utils.TaskDatabaseHandler;
import com.google.android.material.textfield.TextInputEditText;

public class EditActivity extends AppCompatActivity {
    TextInputEditText textTitle, textHours, textDate;
    Button btnDelete, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        btnDelete = findViewById(R.id.btnDelete);
        btnSubmit = findViewById(R.id.btnSubmit);
        TaskDatabaseHandler taskDatabaseHandler = new TaskDatabaseHandler(getApplicationContext());
        taskDatabaseHandler.openDatabase();

        Bundle data = getIntent().getExtras();
        if(data != null) {
            String title = data.getString("title");
            String hours = data.getString("hours");
            String date = data.getString("date");
            textTitle = findViewById(R.id.textTask);
            textHours = findViewById(R.id.textTime);
            textDate = findViewById(R.id.textDate);

            textTitle.setText(title);
            textHours.setText(hours);
            textDate.setText(date);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDatabaseHandler.updateTask(data.getInt("id"), textTitle.getText().toString(), textHours.getText().toString(), textDate.getText().toString());
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDatabaseHandler.deleteTask(data.getInt("id"));
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}