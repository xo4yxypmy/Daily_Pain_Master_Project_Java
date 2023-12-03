package com.fernfog.dailyPain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        DBUsersHandler dbUsersHandler = new DBUsersHandler(this);

        if (dbUsersHandler.checkDatabaseExists(this) && dbUsersHandler.hasUsers()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        }
    }
}