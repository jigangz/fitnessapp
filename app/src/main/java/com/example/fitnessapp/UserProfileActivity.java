package com.example.fitnessapp;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText ageEditText;
    private EditText fitnessGoalsEditText;
    private Button saveButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);

        nameEditText = findViewById(R.id.name_input);
        ageEditText = findViewById(R.id.age_input);
        fitnessGoalsEditText = findViewById(R.id.goals_input);
        saveButton = findViewById(R.id.save_button);

        loadUserProfile();

        saveButton.setOnClickListener(view -> saveUserProfile());
    }

    private void loadUserProfile() {
        // Load user profile data from Shared Preferences
        String name = sharedPreferences.getString("Name", "");
        int age = sharedPreferences.getInt("Age", 0); // Default age is 0 if not set
        String goals = sharedPreferences.getString("FitnessGoals", "");

        nameEditText.setText(name);
        ageEditText.setText(age != 0 ? String.valueOf(age) : "");
        fitnessGoalsEditText.setText(goals);
    }

    private void saveUserProfile() {
        // Save user profile data to Shared Preferences
        String name = nameEditText.getText().toString();
        int age = 0;
        try {
            age = Integer.parseInt(ageEditText.getText().toString());
        } catch (NumberFormatException e) {
            ageEditText.setError("Invalid age");
            return;
        }
        String goals = fitnessGoalsEditText.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", name);
        editor.putInt("Age", age);
        editor.putString("FitnessGoals", goals);
        editor.apply();
    }
}
