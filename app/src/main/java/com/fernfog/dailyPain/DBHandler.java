package com.fernfog.dailyPain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fernfog.dailyPain.objects.SymptomCategory;
import com.fernfog.dailyPain.objects.Symptome;
import com.fernfog.dailyPain.objects.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public DBHandler(Context context) {
        super(context, "myDataBase", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryUsers = "CREATE TABLE users (id INT PRIMARY KEY, first_name TEXT, last_name TEXT, patronymic TEXT, birthday TEXT)";
        String querySymptomCategories = "CREATE TABLE symptomeCategories (id INT PRIMARY KEY, nameOfSymptomCategory TEXT, colorOfSymptomCategory TEXT)";
        String querySymptoms = "CREATE TABLE symptoms (id INTEGER PRIMARY KEY, painLVL FLOAT, categoryId INTEGER, nameOfCategory TEXT, startOfPain TEXT, endOfPain TEXT)";

        db.execSQL(queryUsers);
        db.execSQL(querySymptomCategories);
        db.execSQL(querySymptoms);
    }

    public void addNewUser(String first_name, String last_name, String patronymic, String birthday) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put("first_name", first_name);
            values.put("last_name", last_name);
            values.put("patronymic", patronymic);
            values.put("birthday", birthday);

            db.insert("users", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewSymptomeCategory(String nameOfSymptomCategory, String colorOfSymptomCategory) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put("nameOfSymptomCategory", nameOfSymptomCategory);
            values.put("colorOfSymptomCategory", colorOfSymptomCategory);

            db.insert("symptomeCategories", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewSymptom(float painLVL, String categoryName, String startOfPain, String endOfPain) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            int categoryId = getCategoryIdByName(categoryName, db);

            ContentValues values = new ContentValues();
            values.put("categoryId", categoryId);
            values.put("painLVL", painLVL);
            values.put("startOfPain", startOfPain);
            values.put("endOfPain", endOfPain);
            values.put("nameOfCategory", categoryName);

            db.insert("symptoms", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Symptome> getAllSymptomes() {
        List<Symptome> symptomes = new ArrayList<>();

        try (SQLiteDatabase db = this.getReadableDatabase()) {
            String query = "SELECT * FROM symptoms";
            try (Cursor cursor = db.rawQuery(query, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        float painLVL = cursor.getFloat(cursor.getColumnIndexOrThrow("painLVL"));
                        String startOfPain = cursor.getString(cursor.getColumnIndexOrThrow("startOfPain"));
                        String endOfPain = cursor.getString(cursor.getColumnIndexOrThrow("endOfPain"));
                        String nameOfCategory = cursor.getString(cursor.getColumnIndexOrThrow("nameOfCategory"));

                        Symptome category = new Symptome(startOfPain, endOfPain, nameOfCategory, painLVL);
                        symptomes.add(category);
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return symptomes;
    }

    private int getCategoryIdByName(String categoryName, SQLiteDatabase db) {
        int categoryId = -1;

        String query = "SELECT id FROM symptomeCategories WHERE nameOfSymptomCategory = ?";
        try (Cursor cursor = db.rawQuery(query, new String[]{categoryName})) {
            if (cursor != null && cursor.moveToFirst()) {
                categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoryId;
    }

    public List<SymptomCategory> getAllCategoriesWithColors() {
        List<SymptomCategory> categories = new ArrayList<>();

        try (SQLiteDatabase db = this.getReadableDatabase()) {
            String query = "SELECT * FROM symptomeCategories";
            try (Cursor cursor = db.rawQuery(query, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String categoryName = cursor.getString(cursor.getColumnIndexOrThrow("nameOfSymptomCategory"));
                        String categoryColor = cursor.getString(cursor.getColumnIndexOrThrow("colorOfSymptomCategory"));

                        SymptomCategory category = new SymptomCategory(categoryName, categoryColor);
                        categories.add(category);
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    public User getUser() {
        User user = null;

        try (SQLiteDatabase db = this.getReadableDatabase()) {
            String query = "SELECT * FROM users";
            try (Cursor cursor = db.rawQuery(query, null)) {
                if (cursor.moveToFirst()) {
                    String first_name = cursor.getString(cursor.getColumnIndexOrThrow("first_name"));
                    String last_name = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
                    String patronymic = cursor.getString(cursor.getColumnIndexOrThrow("patronymic"));
                    String birthday = cursor.getString(cursor.getColumnIndexOrThrow("birthday"));

                    user = new User(first_name, last_name, patronymic, birthday);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean hasUsers() {
        boolean hasUsers = false;

        try (SQLiteDatabase db = this.getReadableDatabase()) {
            String query = "SELECT * FROM users";
            try (Cursor cursor = db.rawQuery(query, null)) {
                hasUsers = cursor.getCount() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasUsers;
    }

    public int getSymptomId(float painLVL, String categoryName, String startOfPain, String endOfPain) {
        int symptomId = -1;

        try (SQLiteDatabase db = this.getReadableDatabase()) {
            int categoryId = getCategoryIdByName(categoryName, db);

            String query = "SELECT id FROM symptoms WHERE categoryId = ? AND painLVL = ? AND startOfPain = ? AND endOfPain = ?";
            try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId), String.valueOf(painLVL), startOfPain, endOfPain})) {
                if (cursor != null && cursor.moveToFirst()) {
                    symptomId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return symptomId;
    }

    public void deleteSymptom(int symptomId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete("symptoms", "id = ?", new String[]{String.valueOf(symptomId)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(String categoryName) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            int categoryId = getCategoryIdByName(categoryName, db);

            // Delete symptoms associated with the category
            db.delete("symptoms", "categoryId = ?", new String[]{String.valueOf(categoryId)});

            // Delete the category
            db.delete("symptomeCategories", "nameOfSymptomCategory = ?", new String[]{categoryName});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getCategoryColorByName(String categoryName) {
        String color = "000000";

        try (SQLiteDatabase db = this.getReadableDatabase()) {
            String query = "SELECT colorOfSymptomCategory FROM symptomeCategories WHERE nameOfSymptomCategory = ?";
            try (Cursor cursor = db.rawQuery(query, new String[]{categoryName})) {
                if (cursor != null && cursor.moveToFirst()) {
                    color = cursor.getString(cursor.getColumnIndexOrThrow("colorOfSymptomCategory"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return color;
    }


    public static boolean checkDatabaseExists(Context context) {
        File dbFile = context.getDatabasePath("myDataBase");
        return dbFile.exists();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS symptomeCategories");
        db.execSQL("DROP TABLE IF EXISTS symptoms");
        onCreate(db);
    }
}
