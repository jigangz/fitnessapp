package com.example.fitnessapp;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 100;


    private static final int ADD_WORKOUT_REQUEST = 1; // Request code for adding a workout
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ArrayList<Workout> workoutList;
    private WorkoutAdapter workoutAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workoutList = new ArrayList<>(); // Initialize the workout list
        databaseHelper = new DatabaseHelper(this); // Initialize database helper

        initializeWorkouts(); // Initialize workouts in the database
        loadWorkouts(); // Load workouts from the database

        setupRecyclerView();
        setupFab();
        setupCameraButton();

        Button buttonSetReminder = findViewById(R.id.buttonSetReminder);
        buttonSetReminder.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SetReminderActivity.class);
            startActivity(intent);
        });  Button heartRateButton = findViewById(R.id.button_view_heart_rate);
        heartRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HeartRateActivity.class);
                startActivity(intent);
            }
        });
        Button userProfileButton = findViewById(R.id.buttonUserProfile);
        userProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
            startActivity(intent);
        });
    }

    private void initializeWorkouts() {
        // Check if the database is empty, then add initial workouts
        if(databaseHelper.getAllWorkouts().isEmpty()) {
            ArrayList<Workout> workouts = new ArrayList<>();
            workouts.add(new Workout(1, "Yoga for Beginners", "10 mins", "Follow along with this beginner-friendly yoga sequence.", "https://www.youtube.com/watch?v=j7rKKpwdXNE&ab_channel=YogaWithAdriene"));
            workouts.add(new Workout(2, "HIIT Cardio", "15 mins", "High-intensity interval training for burning calories.", "https://www.youtube.com/watch?v=VWj8ZxCxrYk&ab_channel=MadFit"));

            for (Workout workout : workouts) {
                databaseHelper.addWorkout(workout);
            }
        }
    }

    private void loadWorkouts() {
        workoutList.clear(); // Clear existing data

        try {
            List<Workout> workouts = databaseHelper.getAllWorkouts(); // Fetch workouts
            workoutList.addAll(workouts);
        } catch (Exception e) {
            // Handle exception, e.g., log it or show an error message
            Log.e("MainActivity", "Error loading workouts", e);
        }

        if (workoutAdapter != null) {
            workoutAdapter.notifyDataSetChanged(); // Update adapter with new data
        }
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        workoutAdapter = new WorkoutAdapter(this,workoutList);
        recyclerView.setAdapter(workoutAdapter);
    }

    private void setupFab() {
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddWorkoutActivity.class);
            startActivityForResult(intent, ADD_WORKOUT_REQUEST);
        });
    }
    private void setupCameraButton() {
        Button cameraButton = findViewById(R.id.buttonOpenCamera);
        cameraButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                // Permission has already been granted, open the camera
                openCamera();
            }
        });
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "Camera app is not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted, open the camera
                openCamera();
            } else {
                // Permission was denied
                Toast.makeText(this, "Camera permission is required to use the camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_WORKOUT_REQUEST && resultCode == RESULT_OK) {
            loadWorkouts(); // Reload the workouts from the database
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Handle the camera result here
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

        }
    }


}
