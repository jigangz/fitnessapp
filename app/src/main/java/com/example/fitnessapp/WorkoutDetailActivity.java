package com.example.fitnessapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class WorkoutDetailActivity extends AppCompatActivity {

    private ImageView workoutImageView;
    private TextView workoutNameTextView;
    private TextView workoutDurationTextView;
    private TextView workoutInstructionsTextView;
    private VideoView workoutVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);

        // Initialize views
        workoutImageView = findViewById(R.id.workout_image);
        workoutNameTextView = findViewById(R.id.workout_name);
        workoutDurationTextView = findViewById(R.id.workout_duration);
        workoutInstructionsTextView = findViewById(R.id.workout_instructions);
        workoutVideoView = findViewById(R.id.videoView);

        // Extract data from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String workoutName = extras.getString("EXTRA_WORKOUT_NAME");
            String workoutDuration = extras.getString("EXTRA_WORKOUT_DURATION");
            String workoutInstructions = extras.getString("EXTRA_WORKOUT_INSTRUCTIONS");
            String workoutVideoUrl = extras.getString("EXTRA_WORKOUT_VIDEO_URL");

            // Set data to views
            workoutNameTextView.setText(workoutName);
            workoutDurationTextView.setText(workoutDuration);
            workoutInstructionsTextView.setText(workoutInstructions);

            // Setup the VideoView for workout video
            setupVideoView(workoutVideoUrl);


        }
    }

    private void setupVideoView(String videoUrl) {
        if (videoUrl != null && !videoUrl.isEmpty()) {
            Uri videoUri = Uri.parse(videoUrl);
            workoutVideoView.setVideoURI(videoUri);

            MediaController mediaController = new MediaController(this);
            workoutVideoView.setMediaController(mediaController);
            mediaController.setAnchorView(workoutVideoView);

            // Start the video
            workoutVideoView.start();
        }
    }
}


