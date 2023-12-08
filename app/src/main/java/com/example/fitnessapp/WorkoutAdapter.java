package com.example.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private ArrayList<Workout> workoutList;
    private Context context;
    private DatabaseHelper databaseHelper;

    public WorkoutAdapter(Context context, ArrayList<Workout> workoutList) {
        this.context = context;
        this.workoutList = workoutList;
        databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkoutViewHolder holder, int position) {
        Workout currentWorkout = workoutList.get(position);
        holder.workoutNameTextView.setText(currentWorkout.getName());
        holder.workoutDurationTextView.setText(currentWorkout.getDuration());

        holder.watchVideoButton.setOnClickListener(view -> {
            String videoUrl = currentWorkout.getVideoUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
            context.startActivity(intent);
        });
        holder.deleteButton.setOnClickListener(view -> {
            deleteWorkout(currentWorkout.getId(), position);
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }
    private void deleteWorkout(int workoutId, int position) {
        databaseHelper.deleteWorkout(workoutId);
        workoutList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, workoutList.size());
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutNameTextView;
        public TextView workoutDurationTextView;
        public Button watchVideoButton;
        public Button deleteButton;

        public WorkoutViewHolder(View itemView) {
            super(itemView);
            workoutNameTextView = itemView.findViewById(R.id.workout_name);
            workoutDurationTextView = itemView.findViewById(R.id.workout_duration);
            watchVideoButton = itemView.findViewById(R.id.watch_video_button);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
