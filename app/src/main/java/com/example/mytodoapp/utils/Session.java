package com.example.mytodoapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public Context context;

    public Session(Context context){
        this.context = context;
        preferences = context.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setLoggedin(boolean logggedin, int userId){
        editor.putBoolean("loggedInmode",logggedin);
        editor.putInt("USER_ID",userId);
        editor.commit();
    }

    public boolean loggedin(){
        return preferences.getBoolean("loggedInmode", false);
    }

    public void setUser(String email) {
        editor.putString("email", email);
        editor.commit();
    }

    public String getUser() {
        return  preferences.getString("email", "");
    }
}
