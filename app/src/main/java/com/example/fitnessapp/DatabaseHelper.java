package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fitnessapp.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Updated table creation statement with new fields
        String createTableStatement = "CREATE TABLE workouts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "duration TEXT, " +
                "instructions TEXT, " +
                "videoUrl TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE workouts ADD COLUMN instructions TEXT");
            db.execSQL("ALTER TABLE workouts ADD COLUMN videoUrl TEXT");
        }
    }
    public void addWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Put values into ContentValues for each column
        cv.put("name", workout.getName());
        cv.put("duration", workout.getDuration());
        cv.put("instructions", workout.getInstructions()); // New field
        cv.put("videoUrl", workout.getVideoUrl());         // New field

        // Insert the data into the workouts table
        db.insert("workouts", null, cv);
        db.close();
    }
    // Method to get all workouts, including the new fields
    public ArrayList<Workout> getAllWorkouts() {
        ArrayList<Workout> workoutList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT * FROM workouts";
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int workoutId = cursor.getInt(0);
                String workoutName = cursor.getString(1);
                String workoutDuration = cursor.getString(2);
                String workoutInstructions = cursor.getString(3);  // New field
                String workoutVideoUrl = cursor.getString(4);       // New field

                Workout newWorkout = new Workout(workoutId, workoutName, workoutDuration, workoutInstructions, workoutVideoUrl);
                workoutList.add(newWorkout);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return workoutList;
    }

    public void deleteWorkout(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("workouts", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}