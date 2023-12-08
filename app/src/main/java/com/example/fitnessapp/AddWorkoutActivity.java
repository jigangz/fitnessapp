package com.example.fitnessapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddWorkoutActivity extends AppCompatActivity {

    private EditText nameEditText, durationEditText, instructionsEditText, videoUrlEditText;
    private Button saveButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize UI components
        nameEditText = findViewById(R.id.editTextWorkoutName);
        durationEditText = findViewById(R.id.editTextWorkoutDuration);
        instructionsEditText = findViewById(R.id.editTextWorkoutInstructions);
        videoUrlEditText = findViewById(R.id.editTextWorkoutVideoUrl);

        saveButton = findViewById(R.id.buttonSaveWorkout);
        saveButton.setOnClickListener(view -> saveWorkout());
    }

    private void saveWorkout() {
        String name = nameEditText.getText().toString();
        String duration = durationEditText.getText().toString();
        String instructions = instructionsEditText.getText().toString();
        String videoUrl = videoUrlEditText.getText().toString();

        // Validation logic here

        Workout newWorkout = new Workout(0, name, duration, instructions, videoUrl);
        databaseHelper.addWorkout(newWorkout);


        // Confirmation message or logic to clear fields/reset activity
    }
}
