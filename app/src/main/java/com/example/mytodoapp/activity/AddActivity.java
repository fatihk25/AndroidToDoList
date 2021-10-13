package com.example.mytodoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mytodoapp.R;
import com.example.mytodoapp.model.Task;
import com.example.mytodoapp.utils.Session;
import com.example.mytodoapp.utils.TaskDatabaseHandler;
import com.google.android.material.textfield.TextInputEditText;

public class AddActivity extends AppCompatActivity {
    Button btnAdd;
    TextInputEditText textTask, textDate, textHours;
    int user_id;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Task task = new Task();
        setContentView(R.layout.activity_add);
        btnAdd = findViewById(R.id.addButton);
        textTask = findViewById(R.id.textTask);
        textDate = findViewById(R.id.textDate);
        textHours = findViewById(R.id.textHours);
        TaskDatabaseHandler taskDatabaseHandler = new TaskDatabaseHandler(getApplicationContext());
        taskDatabaseHandler.openDatabase();
        session = new Session(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id = session.preferences.getInt("USER_ID", 0);
                task.setTask(textTask.getText().toString());
                task.setHours(textHours.getText().toString());
                task.setDate(textDate.getText().toString());
                task.setStatus(0);
                task.setUser_id(user_id);
                taskDatabaseHandler.insertTask(task);
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}