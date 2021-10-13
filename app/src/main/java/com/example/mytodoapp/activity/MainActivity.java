package com.example.mytodoapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mytodoapp.R;
import com.example.mytodoapp.adapter.ListTaskAdapter;
import com.example.mytodoapp.model.Task;
import com.example.mytodoapp.utils.Session;
import com.example.mytodoapp.utils.TaskDatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvTask;
    private ArrayList<Task> list = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    TaskDatabaseHandler taskDatabaseHandler;
    private Session session;
    private TextView emailTV;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new Session(this);
        String email = session.getUser();
        emailTV = findViewById(R.id.emailTV);
        emailTV.setText(email);
        user_id = session.preferences.getInt("USER_ID", 0);
        if(!session.loggedin()){
            logout();
        }
        floatingActionButton = findViewById(R.id.fab);
        rvTask = findViewById(R.id.rc_task);
        rvTask.setHasFixedSize(true);
        taskDatabaseHandler = new TaskDatabaseHandler(getApplicationContext());
        taskDatabaseHandler.openDatabase();
        list.addAll(taskDatabaseHandler.getAllTasks(user_id));
        showRecyclerList();

        if (getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("My To Do");
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showRecyclerList() {
        rvTask.setLayoutManager(new LinearLayoutManager(this));
        ListTaskAdapter listTaskAdapter = new ListTaskAdapter(list, taskDatabaseHandler);
        rvTask.setAdapter(listTaskAdapter);
    }

    private void logout(){
        session.setLoggedin(false, user_id);
        finish();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}