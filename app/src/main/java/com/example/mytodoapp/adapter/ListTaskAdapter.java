package com.example.mytodoapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodoapp.activity.EditActivity;
import com.example.mytodoapp.R;
import com.example.mytodoapp.model.Task;
import com.example.mytodoapp.utils.TaskDatabaseHandler;

import java.util.ArrayList;

public class ListTaskAdapter extends RecyclerView.Adapter<ListTaskAdapter.ListViewHolder> {
    Task task;
    ArrayList<Task> listTask;
    TaskDatabaseHandler db;

    public ListTaskAdapter(ArrayList<Task> Task, TaskDatabaseHandler taskDatabaseHandler) {
        this.db = taskDatabaseHandler;
        this.listTask = Task;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list, parent,
                false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Task task = listTask.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditActivity.class);
                intent.putExtra("id", task.getId());
                intent.putExtra("title", task.getTask());
                intent.putExtra("hours", task.getHours());
                intent.putExtra("date", task.getDate());
                v.getContext().startActivity(intent);
                v.getContext().stopService(intent);
            }
        });
        holder.tv_title.setText(task.getTask());
        holder.tv_hours.setText(task.getHours());
        holder.tv_date.setText(task.getDate());
        holder.cb_done.setChecked(task.getStatus() == 1);
        holder.cb_done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatus(task.getId(), 1);
                } else {
                    db.updateStatus(task.getId(), 0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTask.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{

        TextView tv_title;
        TextView tv_hours;
        TextView tv_date;
        CheckBox cb_done;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_hours = itemView.findViewById(R.id.tv_hour);
            tv_date = itemView.findViewById(R.id.tv_date);
            cb_done = itemView.findViewById(R.id.cb_done);
        }
    }
}