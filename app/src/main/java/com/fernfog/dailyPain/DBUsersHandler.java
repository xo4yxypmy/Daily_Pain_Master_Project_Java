package com.fernfog.dailyPain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class DBUsersHandler extends SQLiteOpenHelper {

    public DBUsersHandler(Context context) {
        super(context, "users", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE users (id INT PRIMARY KEY, first_name TEXT, last_name TEXT, patronymic TEXT, birthday TEXT)";

        db.execSQL(query);
    }

    public void addNewUser(String first_name, String last_name, String patronymic, String birthday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("first_name", first_name);
        values.put("last_name", last_name);
        values.put("patronymic", patronymic);
        values.put("birthday", birthday);

        db.insert("users", null, values);

        db.close();
    }

    public boolean hasUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users";
        Cursor cursor = db.rawQuery(query, null);
        boolean hasUsers = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return hasUsers;
    }

    public void listUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String userName = cursor.getString(cursor.getColumnIndexOrThrow("first_name"));

                Log.e("USER ID", "userId: " + userId);
                Log.e("USER NAME", "userName: " + userName);

            } while (cursor.moveToNext());

            cursor.close();
        }


        cursor.close();
        db.close();
    }

    public static boolean checkDatabaseExists(Context context) {
        File dbFile = context.getDatabasePath("users");
        return dbFile.exists();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }



}
