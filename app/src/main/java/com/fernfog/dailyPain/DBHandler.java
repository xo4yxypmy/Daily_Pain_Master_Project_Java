package com.fernfog.dailyPain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fernfog.dailyPain.objects.SymptomCategory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public DBHandler(Context context) {
        super(context, "myDataBase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryUsers = "CREATE TABLE users (id INT PRIMARY KEY, first_name TEXT, last_name TEXT, patronymic TEXT, birthday TEXT)";
        String querySymptomCategories = "CREATE TABLE symptomeCategories (id INT PRIMARY KEY, nameOfSymptomCategory TEXT, colorOfSymptomCategory TEXT)";
        String querySymptoms = "CREATE TABLE symptoms (id INTEGER PRIMARY KEY, nameOfSymptom TEXT, painLVL INTEGER, categoryId INTEGER)";


        db.execSQL(queryUsers);
        db.execSQL(querySymptomCategories);
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

    public void addNewSymptomeCategory(String nameOfSymptomCategory, String colorOfSymptomCategory) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nameOfSymptomCategory", nameOfSymptomCategory);
        values.put("colorOfSymptomCategory", colorOfSymptomCategory);

        db.insert("symptomeCategories", null, values);

        db.close();
    }

    public void addNewSymptom(String nameOfSymptom, int painLVL, String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();

        int categoryId = getCategoryIdByName(categoryName);

        ContentValues values = new ContentValues();
        values.put("nameOfSymptom", nameOfSymptom);
        values.put("categoryId", categoryId);
        values.put("painLVL", painLVL);

        db.insert("symptoms", null, values);

        db.close();
    }

    private int getCategoryIdByName(String categoryName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int categoryId = -1;

        String query = "SELECT id FROM symptomCategories WHERE nameOfSymptomCategory = ?";
        Cursor cursor = db.rawQuery(query, new String[]{categoryName});

        if (cursor != null && cursor.moveToFirst()) {
            categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            cursor.close();
        }

        db.close();
        return categoryId;
    }

    public List<SymptomCategory> getAllCategoriesWithColors() {
        List<SymptomCategory> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM symptomeCategories";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String categoryName = cursor.getString(cursor.getColumnIndexOrThrow("nameOfSymptomCategory"));
                String categoryColor = cursor.getString(cursor.getColumnIndexOrThrow("colorOfSymptomCategory"));

                SymptomCategory category = new SymptomCategory(categoryName, categoryColor);
                categories.add(category);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return categories;
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

    public boolean hasUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users";
        Cursor cursor = db.rawQuery(query, null);
        boolean hasUsers = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return hasUsers;
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
