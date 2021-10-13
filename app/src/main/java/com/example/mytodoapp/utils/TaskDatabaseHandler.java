package com.example.mytodoapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mytodoapp.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 6;
    private static final String NAME = "todoDB";
    private static final String TODO_TABLE = "todo";
    private static final String TABLE_USER = "user";
    private static final String ID = "id";
    private static final String USER_ID = "user_id";
    private static final String TASK = "task";
    private static final String DATE = "date";
    private static final String HOURS = "hours";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TASK + " TEXT, " +
            DATE + " TEXT, " +
            HOURS + " TEXT, " + STATUS + " INTEGER, "+
            USER_ID + " INTEGER, FOREIGN KEY" +
            " (" + USER_ID + " )" + "REFERENCES " +
            TABLE_USER + " (" + ID + "));";

    private SQLiteDatabase db;

    public TaskDatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertTask(Task task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(DATE, task.getDate());
        cv.put(HOURS, task.getHours());
        cv.put(STATUS, 0);
        cv.put(USER_ID, task.getUser_id());
        db.insert(TODO_TABLE, null, cv);
    }

    public List<Task> getAllTasks(int user_id){
        List<Task> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        String selectQuery = "SELECT * FROM " + TODO_TABLE + " WHERE " + USER_ID + " = " +
                user_id;
        try{
            cur = db.rawQuery(selectQuery,null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        Task task = new Task();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setTask(cur.getString(cur.getColumnIndex(TASK)));
                        task.setDate(cur.getString(cur.getColumnIndex(DATE)));
                        task.setHours(cur.getString(cur.getColumnIndex(HOURS)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        task.setUser_id(cur.getInt(user_id));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String task, String date, String hours) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        cv.put(DATE, date);
        cv.put(HOURS, hours);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TODO_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }
}